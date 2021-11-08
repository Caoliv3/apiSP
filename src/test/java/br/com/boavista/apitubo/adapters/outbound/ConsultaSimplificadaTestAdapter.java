//package br.com.boavista.apitubo.adapters.outbound;
//
//
//import br.com.boavista.apitubo.models.ConsultaSimplificadaFiltro;
//import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;
//import br.com.boavista.apitubo.ports.outbound.ConsultaSimplificada;
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class ConsultaSimplificadaTestAdapter implements ConsultaSimplificada {
//
//    @Override
//    public ConsultaSimplificadaResponse consultar(ConsultaSimplificadaFiltro filtro) {
//
//        ConsultaSimplificadaResponse resposta = new ConsultaSimplificadaResponse();
//        Gson gson = new Gson();
//        JsonReader buffer = null;
//        try {
//            buffer = new JsonReader(new FileReader("./massa_test/" + filtro.getDocumento() +"_simplificada.json"));
//        } catch (FileNotFoundException e) {
//            System.out.printf("Erro na leitura do arquivo Json" + e.getMessage());
//        }
//        resposta = gson.fromJson(buffer, ConsultaSimplificadaResponse.class);
//        try {
//            buffer.close();
//        } catch (IOException e) {
//            System.out.printf("Erro na close do arquivo Json" + e.getMessage());
//        }
//        return resposta;
//    }
//}
//
//
