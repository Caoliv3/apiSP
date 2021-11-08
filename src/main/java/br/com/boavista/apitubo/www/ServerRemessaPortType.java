/**
 * ServerRemessaPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.boavista.apitubo.www;

public interface ServerRemessaPortType extends java.rmi.Remote {

    /**
     * Pesquisa protestos pelo keyDoc(tipoDoc+documento) e relaciona
     * todos os tabelionatos ativos e atualizados por comarca e cartorio.
     * Parametros Obrigatorios : 
     * @param:{$tipoDoc; $documento}
     */
    public java.lang.String consulta(java.lang.String tipoDoc, java.lang.String documento, java.lang.String ip) throws java.rmi.RemoteException;

    /**
     * Seleciona os tabelionatos ativos e separa em  participantes,
     * nao participantes e participantes parciais.
     */
    public java.lang.String cidades_participantes_cnp() throws java.rmi.RemoteException;

    /**
     * Método para detalhar os protestos de um determinado devedor
     * em uma comarca/cartorio especifico (parêmetros: tipoDocumento, documentoDevedor,
     * CodMun, NCart) - Parametros Obrigatorios : 
     * @param:{tipoDocumento; documentoDevedor; CodMun; NCart}
     */
    public java.lang.String consultaDetalhes(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String codMun, java.lang.String NCart, java.lang.String ipAddress) throws java.rmi.RemoteException;

    /**
     * Metodo para detalhar os protestos de um determinado devedor
     * em uma comarca/cartorio especifico (parametros: tipoDocumento, documentoDevedor,
     * CodMun, NCart) - Parametros Obrigatorios : 
     * @param:{tipoDocumento; documentoDevedor; CodMun; NCart}
     */
    public java.lang.String consultaDetalhesBiro(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String codMun, java.lang.String NCart, java.lang.String ipAddress, java.lang.String metadados) throws java.rmi.RemoteException;

    /**
     * Metodo para detalhar os protestos de um determinado devedor
     * em uma comarca/cartorio especifico (parametros: tipoDocumento, documentoDevedor,
     * CodMun, NCart) - Parametros Obrigatorios : 
     * @param:{tipoDocumento; documentoDevedor; CodMun; NCart}
     */
    public java.lang.String consultaDetalhesBiroPorDocumento(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String ipAddress, java.lang.String page, java.lang.String limit) throws java.rmi.RemoteException;
    public java.lang.String consulta_itau(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String nomeDevedor, java.lang.String numeroProtocolo, java.lang.String numeroTitulo, java.lang.String nossoNumero, java.lang.String valorTitulo, java.lang.String valorProtestado) throws java.rmi.RemoteException;
}
