package br.com.boavista.apitubo.adapters.outbound;

import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ConsultaSimplificada;
import br.com.boavista.apitubo.www.ServerRemessaLocator;
import br.com.boavista.apitubo.www.ServerRemessaPortType;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

@Slf4j
public class ConsultaSimplificadaSoapAdapter implements ConsultaSimplificada {
	@Override
	public ConsultaSimplificadaResponse consultar(ConsultaSimplificadaFiltro filtro) {
//		log.info("consultar simples - inicio {}", LocalDateTime.now());
		ConsultaSimplificadaResponse respostaConsultaSimplificada = new ConsultaSimplificadaResponse();
		ConsultaSOAP consulta;
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			respostaConsultaSimplificada = this.fillFrom(respostaConsultaSimplificada, new
					ErrorResponse(500, "Erro ao obter IP host.", e.getMessage(), filtro.getDocumento(), filtro.getTipoDocumento()));
		}

		if (respostaConsultaSimplificada.isSuccess()) {
			try {
				ServerRemessaLocator locator = new ServerRemessaLocator();
				ServerRemessaPortType remessa = locator.getServerRemessaPort();
				consulta = ConsultaSOAP.Xml2Object(remessa.consulta(filtro.getTipoDocumento(), filtro.getDocumento(), ip));
				respostaConsultaSimplificada = this.fillFrom(respostaConsultaSimplificada, consulta, filtro);
			} catch (RemoteException | ServiceException e) {
				respostaConsultaSimplificada = this.fillFrom(respostaConsultaSimplificada, new
						ErrorResponse(500, "Erro ao chamar serviço remoto.", e.getMessage(),filtro.getDocumento(), filtro.getTipoDocumento()));
			} catch (JAXBException | XMLStreamException | FactoryConfigurationError e) {
				e.printStackTrace();
				respostaConsultaSimplificada = this.fillFrom(respostaConsultaSimplificada, new
						ErrorResponse(500, "Erro ao converter dados recebidos.", e.getMessage(), filtro.getDocumento(), filtro.getTipoDocumento()));
			}
		}
//		log.info("consultar simples - fim {}", LocalDateTime.now());
		return respostaConsultaSimplificada;
	}

	public ConsultaSimplificadaResponse fillFrom(ConsultaSimplificadaResponse response, ConsultaSOAP consulta, ConsultaSimplificadaFiltro filtro){
		ConsultaSimplificadaResponse retorno = response;
		retorno.setRetorno(consulta.retorno);
		retorno.setDataConsulta(consulta.data_consulta);
		retorno.setSituacao(consulta.situacao);
		retorno.setValorTotalProtestos(consulta.valor_protestados_total);
		if (consulta.retorno.equals("false")){
			retorno.setCodigoRetono("612");
			retorno.setError(validacaoRetornoErroInstituto(consulta, filtro));
		}else {
			retorno.setCodigoRetono("200");
			for (CartorioSOAP car : consulta.conteudo) {
				retorno.getProtestos().add(Protesto.builder()
						.tipoDocumento(filtro.getTipoDocumento())
						.documento(filtro.getDocumento())
						.idCartorioBoavista(car.id_cartorio_boavista)
						.quantidadeProtestos(car.protestos)
						.valorProtestado(car.valor_protestado)
						.build());
				log.info("Simplificada -> Doc: {} {} -C Cartorio: {} ", filtro.getTipoDocumento(), filtro.getDocumento(), car.toString());
			}
		}
		return retorno;
	}

	public ConsultaSimplificadaResponse fillFrom(ConsultaSimplificadaResponse response, ErrorResponse error){
		ConsultaSimplificadaResponse retorno = response;
		retorno.setError(error);
		return retorno;
	}

	public ErrorResponse validacaoRetornoErroInstituto( ConsultaSOAP consulta, ConsultaSimplificadaFiltro filtro){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setDescricao(consulta.erro_descricao);
		errorResponse.setDocumento(filtro.getDocumento());
		errorResponse.setTipoDocumento(filtro.getTipoDocumento());
		if(consulta.erro_descricao.equals("NENHUM REGISTRO ENCONTRADO") ) {
			errorResponse.setCodigo(612);
		} else {
			errorResponse.setCodigo(613);
		}
		return errorResponse;
	}
}
