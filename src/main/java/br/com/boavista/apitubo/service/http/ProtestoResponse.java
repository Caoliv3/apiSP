package br.com.boavista.apitubo.service.http;

public interface ProtestoResponse {
    String getSuccessResponse();
    String getErrorResponse();
    int getHttpStatusCode();
}
