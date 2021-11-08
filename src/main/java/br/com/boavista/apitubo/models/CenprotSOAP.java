package br.com.boavista.apitubo.models;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

@XmlRootElement(name = "cenprot")
public class CenprotSOAP {
	@XmlElement(name = "consultaDetalhes")
	public List<ConsultaDetalhadaSOAP> consultaDetalhes;
	
	public static CenprotSOAP Xml2Object(String xml) throws JAXBException, XMLStreamException, FactoryConfigurationError {
		JAXBContext jaxbContext = JAXBContext.newInstance(CenprotSOAP.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(xml));
		
		return (CenprotSOAP)jaxbUnmarshaller.unmarshal(reader);
	}
}