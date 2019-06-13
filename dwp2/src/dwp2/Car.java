package dwp2;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Car {

	private String Marca;

	private String Modelo;

	public String getMarca() {
		return Marca;
	}
	
	public void setMarca(String marca) {
		Marca = marca;
	}
	
	public String getModelo() {
		return Modelo;
	}
	
	public void setModelo(String modelo) {
		Modelo = modelo;
	}
}