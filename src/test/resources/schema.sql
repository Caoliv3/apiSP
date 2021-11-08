DROP TABLE IF EXISTS TABELA_PROTESTO;

CREATE TABLE TABELA_PROTESTO (
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
  Status TINYINT(1) NULL
  );