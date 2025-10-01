package fabricadeCarros;

public  class Llanta {
	private float tamaño;
	private String tipo;
	public Llanta(float tamaño, String tipo) {
		super();
		this.tamaño = tamaño;
		this.tipo = tipo;
	}
	public float getTamaño() {
		return tamaño;
	}
	public void setTamaño(float tamaño) {
		this.tamaño = tamaño;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Llanta [tamaño=" + tamaño + ", tipo=" + tipo + "]";
	}
	
	
	

}
