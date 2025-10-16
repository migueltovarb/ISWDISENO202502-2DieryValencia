package Paquetecirculo;



public class Circulo {
	
	private double radio;
	
	public double getRadio() {
		return this.radio;
	}
	
	public void setRadio(double radio) {
		this.radio=radio;
	}
	
	
	public double getArea() {
		
		double miArea=Math.PI*(this.radio*this.radio);
		
		return miArea;
		
	}
	
	public double getCircunferencia() {
		double miCircunferencia=2*Math.PI*this.radio;
		
		return miCircunferencia;
	}

	
	public Circulo() {
		this.radio=1;
	}
	
	
	public Circulo(double radio) {
		this.radio=radio;
	}

	@Override
	public String toString() {
		return "mi circulo tiene un radio de: " + radio + " ";
	}
	
	
	
}
