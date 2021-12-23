package br.com.boavista.apitubo.models;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash("DocumentoCache")
public class DocumentoCache {

    @Id
    @Indexed
    private String documento;

    @TimeToLive (unit = TimeUnit.HOURS)
    private long expira;


}

