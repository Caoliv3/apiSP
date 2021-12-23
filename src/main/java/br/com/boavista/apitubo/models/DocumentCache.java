package br.com.boavista.apitubo.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentCache implements Serializable {

    private String document;

    public static DocumentCache create(String document) {
        var destiny = new DocumentCache();
        destiny.setDocument(document);
        return destiny;
    }
}

