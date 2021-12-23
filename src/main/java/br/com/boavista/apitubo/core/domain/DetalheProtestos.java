package br.com.boavista.apitubo.core.domain;

import br.com.boavista.apitubo.models.*;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
public class DetalheProtestos {

    List<Titulo> recuperadaBase = new ArrayList<>();
    List<Protesto> simplificada = new ArrayList<>();
    List<ResumoProtestos> baixar = new ArrayList<>();
    List<Titulo> incluir = new ArrayList<>();
    List<ResumoProtestos> detalhada = new ArrayList<>();
    List<Protesto> cartorioBase = new ArrayList<>();
    private int qtdeProtestosPorDocumento = 0;
    private int qtdeCartorioPorDocumento = 0;


    public void atualizarProstetos(String documento, String tipoDocumento) {

//        log.info("[API-SPY] Carregamento dados base BVS vs Instituto - inicio {}", LocalDateTime.now());
        cartorioBase = getQuantidadeProtestoCartorioBase(documento, tipoDocumento);
        for (Protesto base : cartorioBase) {
            Protesto protestosBase = simplificada.stream()
                    .filter(protestosCartorio -> base.getIdCartorioBoavista().equals(protestosCartorio.getIdCartorioBoavista()))
                    .findAny()
                    .orElse(null);

            String valorProtetosInstituto = protestosBase == null ? "0" :String.format("%.2f", new BigDecimal(protestosBase.getValorProtestado()));
            String valorProtetosBVS = String.format("%.2f", new BigDecimal(base.getValorProtestado()));
            if (protestosBase == null) {
                baixar.add(new ResumoProtestos(base.getTipoDocumento(), base.getDocumento(), base.getIdCartorioBoavista(), base.getQuantidadeProtestos(), base.getValorProtestado()));
            } else if (!protestosBase.getQuantidadeProtestos().equals(base.getQuantidadeProtestos()) ||
                    !valorProtetosInstituto.equals(valorProtetosBVS)) {
                detalhada.add(new ResumoProtestos(base.getTipoDocumento(), base.getDocumento(), protestosBase.getIdCartorioBoavista(), protestosBase.getQuantidadeProtestos(), protestosBase.getValorProtestado()));
                baixar.add(new ResumoProtestos(base.getTipoDocumento(), base.getDocumento(), base.getIdCartorioBoavista(), base.getQuantidadeProtestos(), base.getValorProtestado()));
            }
        }
        for (Protesto simpli : simplificada) {
            Protesto protestosBase = cartorioBase.stream()
                    .filter(protestosCartorio -> simpli.getIdCartorioBoavista().equals(protestosCartorio.getIdCartorioBoavista()))
                    .findAny()
                    .orElse(null);
            if (protestosBase == null) {
                detalhada.add(new ResumoProtestos(tipoDocumento, documento, simpli.getIdCartorioBoavista(), simpli.getQuantidadeProtestos(), simpli.getValorProtestado()));
            }
        }
//        log.info("[API-SPY] Carregamento dados base BVS vs Instituto - fim {}", LocalDateTime.now());
    }

    public List<Protesto> getQuantidadeProtestoCartorioBase(String documento, String tipoDocumento ) {
        String id_cartorio = "";
        int qtdProtestosCartorio = 0;
        BigDecimal values = BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        List<Protesto> protestosCartorio = new ArrayList<>();

        for (Titulo repair : recuperadaBase) {
            if (!repair.getCod_cartorio().equals(id_cartorio)) {
                if (qtdProtestosCartorio > 0) {
                    protestosCartorio.add(Protesto.builder()
                            .tipoDocumento(tipoDocumento)
                            .documento(documento)
                            .idCartorioBoavista(id_cartorio)
                            .quantidadeProtestos(String.valueOf(qtdProtestosCartorio))
                            .valorProtestado(String.valueOf(sum))
                            .build());
                }
                values = BigDecimal.ZERO;
                sum = BigDecimal.ZERO;
                qtdProtestosCartorio = 0;
                id_cartorio = repair.getCod_cartorio();
            }
            qtdProtestosCartorio++;
            sum = sum.add(values.add(new BigDecimal(repair.getValor_protestado())));
        }
        if (qtdProtestosCartorio > 0) {
            protestosCartorio.add(Protesto.builder()
                    .tipoDocumento(tipoDocumento)
                    .documento(documento)
                    .idCartorioBoavista(id_cartorio)
                    .quantidadeProtestos(String.valueOf(qtdProtestosCartorio))
                    .valorProtestado(String.valueOf(sum))
                    .build());
        }
        return protestosCartorio;
    }

    public String getJson(String documento, String tipoDocumento) {

        List<RestSP> listProtestos = getParametroJson(getTitulosAtualizados());

        RestJson json = new RestJson();
        json.setCode(200);
        json.setCodeMessage("A consulta foi realizada com sucesso e retornou um resultado.");
        json.setDocumento(documento);
        json.setTipoDocumento(tipoDocumento);
        json.setQtdeCartorios(String.valueOf(this.getQtdeCartorioPorDocumento()));
        json.setQtdeProtestos(String.valueOf(this.getQtdeProtestosPorDocumento()));
        json.setData(listProtestos);

        return new Gson().toJson(json);
    }

    public String getJsonSimplificada(ConsultaSimplificadaResponse consultaSimplificada) {

        return new Gson().toJson(consultaSimplificada);
    }

    public String getJsonAuditoria() {

        AuditoriaJson auditoriaJson = new AuditoriaJson();

        if (recuperadaBase.size() > 0) {
            auditoriaJson.setBaseProtesto(recuperadaBase);
        }
        if (baixar.size() > 0) {
            auditoriaJson.setBaixaProtesto(baixar);
        }
        if (incluir.size() > 0) {
            auditoriaJson.setIncluirProtesto(incluir);
        }
        return new Gson().toJson(auditoriaJson);
    }

    private List<Titulo> getTitulosAtualizados() {

        List<Titulo> titulos = new ArrayList<>();

        if (this.incluir.size() > 0) {
            titulos.addAll(this.incluir);
        }

        for (Titulo base : this.recuperadaBase) {
            ResumoProtestos protestos = this.baixar.stream()
                    .filter(protestoCartorio -> base.getCod_cartorio().equals(protestoCartorio.getIdCartorioBoavista()))
                    .findAny()
                    .orElse(null);
            if (protestos == null) {
                titulos.add(Titulo.builder()
                        .documento_devedor(base.getDocumento_devedor())
                        .tipo_documento_devedor(base.getTipo_documento_devedor())
                        .cod_cartorio(base.getCod_cartorio())
                        .data_inclusao(base.getData_inclusao())
                        .data_protesto(base.getData_protesto())
                        .data_vencimento(base.getData_vencimento())
                        .valor_protestado(base.getValor_protestado())
                        .uf_devedor(base.getUf_devedor())
                        .especie(base.getEspecie())
                        .build());
            }
        }
        return titulos;
    }

    private List<RestSP> getParametroJson(List<Titulo> protestos) {

        List<RestSP> sps = new ArrayList<>();
        String codigoCartorio = "";
        int qtdadeProtestoPorCartorio = 0;
        int qtdCartorioTotal = 0;
        int qtdProtestoTotal = 0;

        RestSP respSP = null;
        for (Titulo titulo : protestos) {
            if (!codigoCartorio.equals(titulo.getCod_cartorio())) {
                if (qtdadeProtestoPorCartorio > 0 && respSP != null) {
                    respSP.setQuantidade(qtdadeProtestoPorCartorio);
                    sps.add(respSP);
                }
                codigoCartorio = titulo.getCod_cartorio();
                respSP = new RestSP();
                respSP.setCodigo(codigoCartorio);
                respSP.setAtualizacaoData(titulo.getData_inclusao());
                respSP.setProtestos(new ArrayList<RestProtesto>());
                qtdadeProtestoPorCartorio = 0;
                qtdCartorioTotal++;
            }
            RestProtesto protesto = new RestProtesto();
            protesto.setCpfCnpj(titulo.getDocumento_devedor());
            protesto.setDataProtesto(titulo.getData_protesto());
            protesto.setDataVencimento(titulo.getData_vencimento());
            protesto.setValor(titulo.getValor_protestado().replace(".", ",").trim());
            protesto.setUf_cartorio(titulo.getUf_devedor());
            protesto.setEspecie(titulo.getEspecie());

            respSP.getProtestos().add(protesto);
            qtdadeProtestoPorCartorio++;
            qtdProtestoTotal++;
        }

        if (!(respSP == null)) {
            respSP.setQuantidade(qtdadeProtestoPorCartorio);
            sps.add(respSP);
        }
        this.setQtdeCartorioPorDocumento(qtdCartorioTotal);
        this.setQtdeProtestosPorDocumento(qtdProtestoTotal);
        return sps;
    }

    public String getErrorJson(ErrorResponse error) {

        RestJson json = new RestJson();
        json.setCode(error.getCodigo());
        json.setCodeMessage(error.getDescricao());
        json.setDocumento(error.getDocumento());
        json.setTipoDocumento(error.getTipoDocumento());
        json.setQtdeCartorios(null);
        json.setQtdeProtestos(null);
        json.setData(null);

        return new Gson().toJson(json);
    }

    public String getId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public void baixarProtesto(String documento, String tipoDocumento) {
        cartorioBase = getQuantidadeProtestoCartorioBase(documento, tipoDocumento);
        for (Protesto base : cartorioBase) {
            baixar.add(new ResumoProtestos(base.getTipoDocumento(), base.getDocumento(), base.getIdCartorioBoavista(), base.getQuantidadeProtestos(), base.getValorProtestado()));
        }
    }
}


