package br.com.boavista.apitubo.www;

public class ServerRemessaPortTypeProxy implements br.com.boavista.apitubo.www.ServerRemessaPortType {
  private String _endpoint = null;
  private br.com.boavista.apitubo.www.ServerRemessaPortType serverRemessaPortType = null;
  
  public ServerRemessaPortTypeProxy() {
    _initServerRemessaPortTypeProxy();
  }
  
  public ServerRemessaPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initServerRemessaPortTypeProxy();
  }
  
  private void _initServerRemessaPortTypeProxy() {
    try {
      serverRemessaPortType = (new br.com.boavista.apitubo.www.ServerRemessaLocator()).getServerRemessaPort();
      if (serverRemessaPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serverRemessaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serverRemessaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serverRemessaPortType != null)
      ((javax.xml.rpc.Stub)serverRemessaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.com.boavista.apitubo.www.ServerRemessaPortType getServerRemessaPortType() {
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType;
  }
  
  public java.lang.String consulta(java.lang.String tipoDoc, java.lang.String documento, java.lang.String ip) throws java.rmi.RemoteException{
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType.consulta(tipoDoc, documento, ip);
  }
  
  public java.lang.String cidades_participantes_cnp() throws java.rmi.RemoteException{
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType.cidades_participantes_cnp();
  }
  
  public java.lang.String consultaDetalhes(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String codMun, java.lang.String NCart, java.lang.String ipAddress) throws java.rmi.RemoteException{
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType.consultaDetalhes(tipoDocumento, documentoDevedor, codMun, NCart, ipAddress);
  }
  
  public java.lang.String consultaDetalhesBiro(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String codMun, java.lang.String NCart, java.lang.String ipAddress, java.lang.String metadados) throws java.rmi.RemoteException{
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType.consultaDetalhesBiro(tipoDocumento, documentoDevedor, codMun, NCart, ipAddress, metadados);
  }
  
  public java.lang.String consultaDetalhesBiroPorDocumento(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String ipAddress, java.lang.String page, java.lang.String limit) throws java.rmi.RemoteException{
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType.consultaDetalhesBiroPorDocumento(tipoDocumento, documentoDevedor, ipAddress, page, limit);
  }
  
  public java.lang.String consulta_itau(java.lang.String tipoDocumento, java.lang.String documentoDevedor, java.lang.String nomeDevedor, java.lang.String numeroProtocolo, java.lang.String numeroTitulo, java.lang.String nossoNumero, java.lang.String valorTitulo, java.lang.String valorProtestado) throws java.rmi.RemoteException{
    if (serverRemessaPortType == null)
      _initServerRemessaPortTypeProxy();
    return serverRemessaPortType.consulta_itau(tipoDocumento, documentoDevedor, nomeDevedor, numeroProtocolo, numeroTitulo, nossoNumero, valorTitulo, valorProtestado);
  }
  
  
}