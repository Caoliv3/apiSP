package br.com.boavista.apitubo.models;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "consultaDetalhes")
public class ConsultaDetalhadaSOAP {
	@XmlElement
	public String erro_metodo;
	@XmlElement
	public String erro_metodo_descricao;
	@XmlElementWrapper(name = "titulos")
	@XmlElement(name = "titulo")
	public List<TitulosSOAP> titulos;
}
