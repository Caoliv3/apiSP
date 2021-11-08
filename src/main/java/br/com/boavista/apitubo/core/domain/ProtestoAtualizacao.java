package br.com.boavista.apitubo.core.domain;

public interface ProtestoAtualizacao {
      void setQuantidadeProtesto(Integer quantidadeProtesto);
      void setDataBase(String data);
      boolean atualizarProtestos();
}