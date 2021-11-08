package br.com.boavista.apitubo.models;


import java.io.StringReader;
import java.util.List;  
import javax.xml.bind.annotation.XmlRootElement; 
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;


@XmlRootElement(name = "consulta")
public class ConsultaSOAP {
	@XmlElement
	public String data_consulta;
	@XmlElement
	public String retorno;
	@XmlElement
	public String erro;
	@XmlElement
	public String erro_descricao;
	@XmlElement
	public String situacao;
	@XmlElement
	public String valor_protestados_total;
	@XmlElementWrapper(name = "conteudo")
	@XmlElement(name = "cartorio")
	public List<CartorioSOAP> conteudo;
	
	public static ConsultaSOAP Xml2Object(String xml) throws JAXBException, XMLStreamException, FactoryConfigurationError {
		JAXBContext jaxbContext = JAXBContext.newInstance(ConsultaSOAP.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(xml));
		return (ConsultaSOAP)jaxbUnmarshaller.unmarshal(reader);
	}

}
