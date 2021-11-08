/**
 * ServerRemessaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.boavista.apitubo.www;

import org.apache.axis.transport.http.HTTPConstants;

public class ServerRemessaLocator extends org.apache.axis.client.Service implements br.com.boavista.apitubo.www.ServerRemessa {

    public ServerRemessaLocator() {
    }


    public ServerRemessaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServerRemessaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServerRemessaPort
    private java.lang.String ServerRemessaPort_address = System.getenv("URL_ENDPOINT_INSTITUTO");

    public java.lang.String getServerRemessaPortAddress() {
        return ServerRemessaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServerRemessaPortWSDDServiceName = "server.RemessaPort";

    public java.lang.String getServerRemessaPortWSDDServiceName() {
        return ServerRemessaPortWSDDServiceName;
    }

    public void setServerRemessaPortWSDDServiceName(java.lang.String name) {
        ServerRemessaPortWSDDServiceName = name;
    }

    public br.com.boavista.apitubo.www.ServerRemessaPortType getServerRemessaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServerRemessaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServerRemessaPort(endpoint);
    }

    public br.com.boavista.apitubo.www.ServerRemessaPortType getServerRemessaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {

            br.com.boavista.apitubo.www.ServerRemessaBindingStub _stub = new br.com.boavista.apitubo.www.ServerRemessaBindingStub(portAddress, this);
//            _stub._setProperty(HTTPConstants.HEADER_AUTHORIZATION,System.getenv("TOKEN_API"));
            _stub.setPortName(getServerRemessaPortWSDDServiceName());
            _stub.setUsername(System.getenv("USERNAME_INSTITUTO_SP"));
            _stub.setPassword(System.getenv("PASSWD_INSTITUTO_SP"));
//            System.out.println("Usuario instituto " + System.getenv("USERNAME_INSTITUTO_SP"));
//            System.out.println("Senha instituto " + System.getenv("PASSWD_INSTITUTO_SP"));
//            System.out.println("token instituto " + System.getenv("TOKEN_API"));
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServerRemessaPortEndpointAddress(java.lang.String address) {
        ServerRemessaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.com.boavista.apitubo.www.ServerRemessaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                br.com.boavista.apitubo.www.ServerRemessaBindingStub _stub = new br.com.boavista.apitubo.www.ServerRemessaBindingStub(new java.net.URL(ServerRemessaPort_address), this);
                _stub.setPortName(getServerRemessaPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }
    
    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("server.RemessaPort".equals(inputPortName)) {
            return getServerRemessaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.protesto.com.br", "server.Remessa");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.protesto.com.br", "server.RemessaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServerRemessaPort".equals(portName)) {
            setServerRemessaPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
