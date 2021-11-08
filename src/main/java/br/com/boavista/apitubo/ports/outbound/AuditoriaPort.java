package br.com.boavista.apitubo.ports.outbound;

import br.com.boavista.apitubo.models.Auditoria;
import java.sql.SQLException;

public interface AuditoriaPort {
    void salvar(Auditoria auditoria) throws SQLException;
}
