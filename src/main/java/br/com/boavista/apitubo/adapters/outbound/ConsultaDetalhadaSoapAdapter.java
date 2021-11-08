package br.com.boavista.apitubo.adapters.outbound;

import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ConsultaDetalhada;
import br.com.boavista.apitubo.www.ServerRemessaLocator;
import br.com.boavista.apitubo.www.ServerRemessaPortType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConsultaDetalhadaSoapAdapter implements ConsultaDetalhada {
	public CenprotSOAP cenprotSOAP;
	private ObjectMapper mapper = new ObjectMapper();
	@Setter private List<ResumoProtestos> listaResumoProtestos;
	private String erro_metodo = null;
	private String erro_metodo_descricao = null;
	
	@Override
	public List<Titulo> consultar(ConsultaDetalhadaFiltro filtro) {
		List<Titulo> resposta = new ArrayList<Titulo>();
		String ip = null;
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	

		ServerRemessaLocator locator = new ServerRemessaLocator();
		ServerRemessaPortType remessa = null;
		
		try {
			remessa = locator.getServerRemessaPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			try {
				cenprotSOAP = CenprotSOAP.Xml2Object(remessa.consultaDetalhesBiro(filtro.getTipoDocumento(), filtro.getDocumento(), "", "", ip, "boavista;" + filtro.getNumeroCartorio()));
			} catch (JAXBException | XMLStreamException | FactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			for (ConsultaDetalhadaSOAP consultaDetalhes : cenprotSOAP.consultaDetalhes) {
				for (TitulosSOAP titulos : consultaDetalhes.titulos) {
					resposta.add(Titulo.builder()
							.cod_cartorio(filtro.getNumeroCartorio())
							.tipo_documento_devedor(filtro.getTipoDocumento())
							.documento_devedor(filtro.getDocumento())
							.nome_devedor(titulos.nome_devedor)
							.endereco_devedor(titulos.endereco_devedor)
							.cep_devedor(titulos.cep_devedor)
							.bairro_devedor(titulos.bairro_devedor)
							.cidade_devedor(titulos.cidade_devedor)
							.uf_devedor(titulos.uf_devedor)
							.livro_protesto(titulos.livro_protesto)
							.folha_protesto(titulos.folha_protesto)
							.protocolo(titulos.protocolo)
							.data_protocolo(titulos.data_protocolo)
							.especie(titulos.especie)
							.numero_titulo(titulos.numero_titulo)
							.data_emissao(titulos.data_emissao)
							.data_vencimento(titulos.data_vencimento)
							.data_protesto(titulos.data_protesto)
							.valor_original(titulos.valor_original)
							.valor_protestado(titulos.valor_protestado)
							.erro_metodo(consultaDetalhes.erro_metodo)
							.erro_metodo_descricao(consultaDetalhes.erro_metodo_descricao)
							.build());

					log.info("Detalhada -> Doc: {} {} - Carotorio: {}" ,filtro.getTipoDocumento(), filtro.getDocumento() ,  titulos);
				}
			}
			return resposta;
		}

}