DROP TABLE IF EXISTS PROTESTO;
DROP TABLE IF EXISTS auditoria;

CREATE TABLE PROTESTO (
  idprotestos char(11) NULL,
  documento char(14) NULL,
  tipo_documento TINYINT(1) NULL,
  id_cartorio Char(11) NULL,
  data_protesto CHAR(10) NULL,
  data_vencimento CHAR(10) NULL,
  valor_protestado CHAR(20) NULL,
  uf_devedor CHAR(2) NULL,
  especie CHAR(10) NULL,
  data_inclusao CHAR(10) NULL,
  data_inativacao CHAR(10) NULL
  );

  CREATE TABLE auditoria (
    id_auditoria char(36) NOT NULL,
    tipo_documento smallint NOT NULL,
    documento char(14) NOT NULL,
    fonte_consulta smallint not null,
    quantidade_consulta char(10) NOT NULL,
    data_inclusao char(10) NOT NULL,
    hora_inclusao char(08) NOT NULL,
    json json null
  );