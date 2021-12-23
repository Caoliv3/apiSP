package br.com.boavista.apitubo.adapters.outbound.repository;

import br.com.boavista.apitubo.core.domain.DataCorrente;
import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.ResumoProtestos;
import br.com.boavista.apitubo.models.Titulo;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ProtestoRepository implements ProtestoPort {

    private final DataSource dataSource;
//    private PreparedStatement sql = null;
    private static String SQL_INSERT_SIMPLIFICADA = "insert into simplificada ( id_auditoria, data_inclusao, hora_inclusao, json) values(?,?,?,?)";
    private static String SQL_INSERT_AUDITORIA = "insert into auditoria (tipo_documento, documento, fonte_consulta, quantidade_consulta, data_inclusao, hora_inclusao, json," +
            " canal, produto, codigo_cliente, inicio_consulta, fim_consulta) " +
            " values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String SQL_INSERT_DETALHADA = "insert into detalhada (id_auditoria, data_inclusao, hora_inclusao, json)  values(?,?,?,?)";
    private static String SQL_SELECT_PROTESTOS = "select data_protesto, data_vencimento,valor_protestado,uf_devedor, especie , id_cartorio, tipo_documento, documento, data_inclusao" +
            " from protesto " +
            "  where documento =  ?" +
            "  and data_inativacao is null" +
            "  order by id_cartorio ";
    private static String SQL_SELECT_ULTIMA_ATUALIZACAO = "select max(data_inclusao) as data_inclusao" +
            " from auditoria " +
            " where documento =  ?";
    private static String SQL_INSERT_PROTESTO = "insert into protesto(id_auditoria, id_cartorio," +
            " documento, tipo_documento, data_inclusao, hora_inclusao, data_inativacao, hora_inativacao, nome_devedor," +
            " endereco_devedor, cep_devedor, bairro_devedor, cidade_devedor, uf_devedor, livro_protesto, folha_protesto, protocolo," +
            " data_protocolo, especie, numero_titulo, data_emissao, data_vencimento, data_protesto, valor_original," +
            " valor_protestado)" +
            " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String SQL_SELECT_JSON_DETALHADA = "select json from detalhada " +
            " where documento = ? " +
            "   and data_inclusao = ? ";
    private static String SQL_UPDATE_BAIXAR_PROTESTOS = "update protesto set data_inativacao = ? , hora_inativacao = ? " +
            " where documento = ? " +
            "   and id_cartorio in (%S) " +
            "   and  data_inativacao is null ";
    private static String SQL_SELECT_QTDADE_CONSULTA = "select sum(quantidade_consulta) as quantidade_consulta from auditoria " +
            " where data_inclusao = ? " +
            "   and fonte_consulta = 3";
    private static String SQL_SELECT_QTDADE_PROTESTOS = "select count(*) as quantidades from protesto " +
            " where documento = ? " +
            "   and tipo_documento = ? " +
            "   and data_inativacao is null";
    private static String SQL_SELECT_ID = " select nextval('job_id_seq') UUID";

    private DataCorrente data = new DataCorrente();
    private Auditoria auditoria;

    public ProtestoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Titulo> recuperarDetalheProtestos(String documento) {
//        log.info("[API-SPY] recuperarDetalheProtestos - inicio {}", LocalDateTime.now());

        List<Titulo> listTitulo = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sql =  null;
        try {
            conn = this.dataSource.getConnection();

            sql = conn.prepareStatement(SQL_SELECT_PROTESTOS);
            sql.setString(1, documento);
            ResultSet rs = sql.executeQuery();
            while (rs.next()) {
                Titulo titulo = new Titulo();
                titulo.setData_protesto(rs.getString("data_protesto"));
                titulo.setData_vencimento(rs.getString("data_vencimento"));
                titulo.setValor_protestado(rs.getString("valor_protestado"));
                titulo.setUf_devedor(rs.getString("uf_devedor"));
                titulo.setEspecie(rs.getString("especie"));
                titulo.setCod_cartorio(rs.getString("id_cartorio"));
                titulo.setTipo_documento_devedor(String.valueOf(rs.getInt("tipo_documento")));
                titulo.setDocumento_devedor(rs.getString("documento"));
                titulo.setData_inclusao(rs.getString("data_inclusao"));
                listTitulo.add(titulo);
                log.info("Titulo base {} ", titulo.toString());
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar protesto da base Protestos {} ", e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        log.info("[API-SPY] recuperarDetalheProtestos - fim {}", LocalDateTime.now());
        return listTitulo;
    }

    @Override
    public String recuperarJsonConsultaDetalhada(String documento) {

        String retorno = "";
        String dataInclusao = getDataAtualizacao(documento);
        Connection conn = null;
        PreparedStatement sql = null;
        try {
            conn = this.dataSource.getConnection();

            sql = conn.prepareStatement(SQL_SELECT_JSON_DETALHADA);
            sql.setString(1, documento);
            sql.setString(2, dataInclusao);

            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                retorno = rs.getString("json");
            }

        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar json detalha {} ", e.getMessage());
        } finally {
            try {
                conn.close();
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    @Override
    public ProtestoPort with(Auditoria auditoria) {
        this.auditoria = auditoria;
        return this;
    }

    @Override
    public ProtestoPort salvar() {
        this.salvar(null);
        return this;
    }

    @Override
    public int getQtdadeConsultas() {

        int retorno = 0;
        Connection conn = null;
        PreparedStatement sql =  null;

        try {
            conn = this.dataSource.getConnection();

            sql = conn.prepareStatement(SQL_SELECT_QTDADE_CONSULTA);
            sql.setString(1, data.getDataCorrente());
            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                if (rs.getInt("quantidade_consulta") != 0) {
                    retorno = rs.getInt("quantidade_consulta");
                }
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar quantidade consulta {}", e.getMessage());

        } finally {
            try {
                conn.close();
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    @Override
    public int getQtdadeProtestoDocumento(String documento, String tipoDocumento) {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement sql =  null;

        try {
            conn = this.dataSource.getConnection();

            sql = conn.prepareStatement(SQL_SELECT_QTDADE_PROTESTOS);
            sql.setString(1, documento);
            sql.setInt(2, Integer.valueOf(tipoDocumento));
            ResultSet resultSet = sql.executeQuery();
            if (resultSet.next()) {
                retorno = resultSet.getInt("quantidades");
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar quantidade de protestos do documento {}", e.getMessage());
        } finally {
            try {
                conn.close();
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    @Override
    public String getDataAtualizacao(String documento) {
        String data = null;
        Connection conn = null;
        PreparedStatement sql =  null;

        try {
            conn = this.dataSource.getConnection();

            sql = conn.prepareStatement(SQL_SELECT_ULTIMA_ATUALIZACAO);
            sql.setString(1, documento);
            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                if (rs.getString("data_inclusao") != null) {
                    data = rs.getString("data_inclusao");
                }
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar uma data de Atualização {}", e.getMessage());
        } finally {
            try {
                conn.close();
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public ProtestoPort salvar(DetalheProtestos detalheProtestos) {
        log.info("[API-SPY] salvar - inicio {} ", LocalDateTime.now());
        Connection conn = null;
        PreparedStatement sql =  null;

        try {
            conn = this.dataSource.getConnection();
            conn.setAutoCommit(false);
            Long idAuditoria = salvarAuditoria(auditoria, conn);
            if (detalheProtestos != null) {
                salvarDetalheProtesto(detalheProtestos, auditoria, conn, idAuditoria);
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.info(" SQLSERROr - Inclusao  de Protestos: {}", e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.info("[API-SPY] salvar - fim {}", LocalDateTime.now());
        return this;
    }

    private void salvarDetalheProtesto(DetalheProtestos protestos, Auditoria auditoria, Connection conn, Long idAuditoria) {
//        log.info("[API-SPY] salvarDetalheProtesto - inicio {}", LocalDateTime.now());
        if (protestos.getBaixar().size() > 0) {
            List<ResumoProtestos> resumoProtestos = protestos.getBaixar();
            baixarProtestos(resumoProtestos, conn);
        }
        if (protestos.getIncluir().size() > 0) {
            List<Titulo> titulos = protestos.getIncluir();
            incluirProtesto(titulos, auditoria, conn, idAuditoria);
        }
//        log.info("[API-SPY] salvarDetalheProtesto - fim {}", LocalDateTime.now());
    }

    private void baixarProtestos(List<ResumoProtestos> resumoProtestos, Connection conn) {
//        log.info("[API-SPY] baixarProtestos - inicio {}", LocalDateTime.now());
        List<String> idCartorios = new ArrayList<>();
        String documento = "";
        PreparedStatement sql =  null;

        int paramCount = 0;
        for (ResumoProtestos res : resumoProtestos) {
            idCartorios.add(res.idCartorioBoavista);
            documento = res.documento;
            paramCount++;
        }

        try {
            sql = conn.prepareStatement(parametros(paramCount));
            sql.setString(1, data.getDataCorrente());
            sql.setString(2, data.getHoraCorrente());
            sql.setString(3, documento);
            paramCount = 4;
            for (String id : idCartorios) {
                sql.setString(paramCount, id);
                paramCount++;
            }
            sql.executeUpdate();
        } catch (SQLException e) {
            log.info(" SQLERROr - Update de Protestos: {}", e.getMessage());
        } finally {
            try {
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        log.info("[API-SPY] baixarProtestos - fim {}", LocalDateTime.now());
    }

    private static String parametros(int paramCount) {

        String parm = ",?";
        String params = parm.repeat(paramCount);
        params = params.substring(1);
        String parametros = String.format(SQL_UPDATE_BAIXAR_PROTESTOS, params);
        return parametros;
    }

    private void incluirProtesto(List<Titulo> titulos, Auditoria auditoria, Connection conn, Long idAuditoria) {
//        log.info("[API-SPY] incluirProtesto - inicio {}", LocalDateTime.now());
        PreparedStatement sql =  null;
        try {
            for (Titulo tit : titulos) {
                sql = conn.prepareStatement(SQL_INSERT_PROTESTO);
                sql.setLong(1, idAuditoria);
                sql.setString(2, tit.getCod_cartorio());
                sql.setString(3, tit.getDocumento_devedor());
                sql.setInt(4, Integer.parseInt(tit.getTipo_documento_devedor()));
                sql.setString(5, data.getDataCorrente());
                sql.setString(6, data.getHoraCorrente());
                sql.setString(7, null);
                sql.setString(8, null);
                sql.setString(9, tit.getNome_devedor());
                sql.setString(10, tit.getEndereco_devedor());
                sql.setString(11, tit.getCep_devedor());
                sql.setString(12, tit.getBairro_devedor());
                sql.setString(13, tit.getCidade_devedor());
                sql.setString(14, tit.getUf_devedor());
                sql.setString(15, tit.getLivro_protesto());
                sql.setString(16, tit.getFolha_protesto());
                sql.setString(17, tit.getProtocolo());
                sql.setString(18, tit.getData_protocolo());
                sql.setString(19, tit.getEspecie());
                sql.setString(20, tit.getNumero_titulo());
                sql.setString(21, tit.getData_emissao());
                sql.setString(22, tit.getData_vencimento());
                sql.setString(23, tit.getData_protesto());
                sql.setString(24, tit.getValor_original());
                sql.setString(25, tit.getValor_protestado());

                sql.executeUpdate();
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Incluir protestos na Base: documento {} = {} - {}", auditoria.getDocumento(), e.getMessage(), e.getStackTrace());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        log.info("[API-SPY] incluirProtesto - fim {}", LocalDateTime.now());
    }

    private Long salvarAuditoria(Auditoria auditoria, Connection conn) {
//        log.info("[API-SPY] Salvar auditoria inicio {}" , LocalDateTime.now());
        Long idAuditoria = incluirAuditoria(auditoria, conn);
        if (auditoria.getIdSimplificada() != null) {
            incluirSimplificada(auditoria, conn, idAuditoria);
        }
        if (auditoria.getIdDetalhada() != null) {
            incluirDetalhada(auditoria, conn, idAuditoria);
        }
        return idAuditoria;
//        log.info("[API-SPY] Salvar auditoria fim {}" , LocalDateTime.now());
    }

    private Long incluirAuditoria(Auditoria auditoria, Connection conn) {
//        log.info("[API-SPY] incluir Auditoria inicio {}" , LocalDateTime.now());
        Long idAuditoria = null;
        PreparedStatement sql =  null;
        try {
            sql = conn.prepareStatement(SQL_INSERT_AUDITORIA, Statement.RETURN_GENERATED_KEYS);
            sql.setInt(1, auditoria.getTipoDocumento());
            sql.setString(2, auditoria.getDocumento());
            sql.setInt(3, auditoria.getFonteConsulta());
            sql.setInt(4, auditoria.getQuantidadeConsulta());
            sql.setString(5, data.getDataCorrente());
            sql.setString(6, data.getHoraCorrente());
            sql.setString(7, auditoria.getJsonAuditoria());
            sql.setString(8, auditoria.getCanal());
            sql.setString(9, auditoria.getProduto());
            sql.setInt(10, auditoria.getCodigoCliente());
            sql.setString(11, String.valueOf(auditoria.getInicioConsulta()));
            sql.setString(12, String.valueOf(auditoria.getFimConsulta()));
            sql.executeUpdate();

            ResultSet rs = sql.getGeneratedKeys();
            rs.next();
            idAuditoria = rs.getLong(1);
            rs.close();

        } catch (SQLException e) {
            log.info("SQLERROR - Inclusão na base auditoria: documento {} = {} - {} ", auditoria.getDocumento(), e.getMessage(), e.getStackTrace());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idAuditoria;
//        log.info("[API-SPY] incluir Auditoria fim {}" , LocalDateTime.now());
    }


    private void incluirSimplificada(Auditoria auditoria, Connection conn, Long idAuditoria) {
//        log.info("[API-SPY] incluir Consulta Simplificada inicio {}" , LocalDateTime.now());
        PreparedStatement sql =  null;
        try {
            sql = conn.prepareStatement(SQL_INSERT_SIMPLIFICADA);
            sql.setLong(1, idAuditoria);
            sql.setString(2, data.getDataCorrente());
            sql.setString(3, data.getHoraCorrente());
            sql.setString(4, auditoria.getJsonSimplificada());
            sql.executeUpdate();

        } catch (SQLException e) {
            log.info("SQLERROR - Inclusão da consulta simplificada: documento {} =  {} - {}", auditoria.getDocumento(), e.getMessage(), e.getStackTrace());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        log.info("[API-SPY] incluir Consulta Simplificada fim {}" , LocalDateTime.now());
    }


    private void incluirDetalhada(Auditoria auditoria, Connection conn, Long idAuditoria) {
//        log.info("[API-SPY] incluirDetalhada inicio {}" , LocalDateTime.now());
        PreparedStatement sql =  null;
        try {
            sql = conn.prepareStatement(SQL_INSERT_DETALHADA);
            sql.setLong(1, idAuditoria);
            sql.setString(2, data.getDataCorrente());
            sql.setString(3, data.getHoraCorrente());
            sql.setString(4, auditoria.getJsonDetalhada());
            sql.executeUpdate();

        } catch (SQLException e) {
            log.info("SQLERROR - Inclusão na base detalhada: documento {} = {} - {}", auditoria.getDocumento(), e.getMessage(), e.getStackTrace());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        log.info("[API-SPY] incluirDetalhada fim {}" , LocalDateTime.now());
    }

    @Override
    public String getId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
