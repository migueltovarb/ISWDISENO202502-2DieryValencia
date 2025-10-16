package fabricadeCarros;

import java.util.List;

public class Fabrica {
	private String Nombre;
	private List<Planta> Planta;
	public Fabrica(String nombre, List<fabricadeCarros.Planta> planta) {
		super();
		Nombre = nombre;
		Planta = planta;
	}
	public Fabrica() {
		// TODO Auto-generated constructor stub
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public List<Planta> getPlanta() {
		return Planta;
	}
	public void setPlanta(List<Planta> planta) {
		Planta = planta;
	}
	
	@Override
	public String toString() {
		return "Fabrica [Nombre=" + Nombre + ", Planta=" + Planta + "]";
	}
	

}
