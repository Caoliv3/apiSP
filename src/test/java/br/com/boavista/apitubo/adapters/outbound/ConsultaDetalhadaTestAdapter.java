//package br.com.boavista.apitubo.adapters.outbound;
//
//import br.com.boavista.apitubo.models.*;
//import br.com.boavista.apitubo.ports.outbound.ConsultaDetalhada;
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ConsultaDetalhadaTestAdapter implements ConsultaDetalhada {
//
//    @Override
//    public List<Titulo> consultar(ConsultaDetalhadaFiltro filtro) {
//
//        List<Titulo> resposta = new ArrayList<Titulo>();
//        Gson gson = new Gson();
//        JsonReader buffer = null;
//        try {
//            buffer = new JsonReader(new FileReader("./massa_test/" + filtro.getDocumento() +"_detalhada.json"));
//        } catch (FileNotFoundException e) {
//            System.out.printf("Erro na leitura do arquivo Json" + e.getMessage());
//        }
//        RestJson restJson = gson.fromJson(buffer, RestJson.class);
//        try {
//            buffer.close();
//        } catch (IOException e) {
//            System.out.printf("Erro na close do arquivo Json" + e.getMessage());
//        }
//
//        List<RestSP> restSP = restJson.getData();
//        List<RestProtesto> restProtestos = new ArrayList<>();
//        String idCartorio;
//
//        for (RestSP sp: restSP) {
//            restProtestos = sp.getProtestos();
//            idCartorio = sp.getCodigo();
//            int i =0;
//            for (RestProtesto protesto : restProtestos) {
//                restProtestos = sp.getProtestos();
//                resposta.add( Titulo.builder()
//                        .documento_devedor(restProtestos.get(i).getCpfCnpj())
//                        .data_protesto(restProtestos.get(i).getDataProtesto())
//                        .valor_protestado(restProtestos.get(i).getValor().replace(",", "."))
//                        .data_vencimento(restProtestos.get(i).getDataVencimento())
//                        .cod_cartorio(idCartorio)
//                        .especie(restProtestos.get(i).getEspecie())
//                        .build());
//                i++;
//            }
//        }
//
//        return resposta;
//    }
//}
