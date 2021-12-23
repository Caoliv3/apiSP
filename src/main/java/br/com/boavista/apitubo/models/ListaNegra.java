package br.com.boavista.apitubo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash("ListaNegra")
public class ListaNegra {

    @Id
    @Indexed
    private String documento;
}

