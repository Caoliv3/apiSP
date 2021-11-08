//package br.com.boavista.apitubo.adapters.outbound;
//
//import br.com.boavista.apitubo.models.*;
//import br.com.boavista.apitubo.ports.ConsultaFactory;
//import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
//
//import java.util.List;
//
//public class ConsultaTestAdapter implements ConsultaPort {
//    private ConsultaTestFactory consultaTestFactory;
//    private String documento;
//    private String tipoDocumento;
//
//    @Override
//    public void setConsultaFactory(ConsultaFactory consultaFactory) {
//        this.consultaTestFactory = (ConsultaTestFactory) consultaFactory;
//    }
//
//    @Override
//    public ConsultaSimplificadaResponse fazerConsultaSimplifcada(String documento, String tipoDocumento) {
//        this.documento = documento;
//        this.tipoDocumento = tipoDocumento;
//        return this.consultaTestFactory.getConsultaSimplificada().consultar(new ConsultaSimplificadaFiltro(documento, tipoDocumento));
//    }
//
//    @Override
//    public ConsultaDetalhadaResponse fazerConsultaDetalhada(List<ResumoProtestos> protestos) {
//
//        ResumoProtestos protesto = protestos.get(0);
//        ConsultaDetalhadaResponse response = new ConsultaDetalhadaResponse();
//        response.setTitulos(this.consultaTestFactory.getConsultaDetalhada().consultar(ConsultaDetalhadaFiltro.buildFromProtesto(protesto)));
//        return response;
//    }
//}
