package Paquetecirculo;

import java.util.Scanner;

public class Programa {

	public static void main(String[] args) {
		
		Circulo miCirculo=new Circulo();
		
		System.out.println(miCirculo);
		
		System.out.println("ingrese el valor del radio");
		
		Scanner miScanner=new Scanner(System.in);
		
		double radioLeido=miScanner.nextDouble();
		
		miCirculo.setRadio(radioLeido);
		
		System.out.println(miCirculo);
		
		System.out.println("El àrea del circulo es: " + miCirculo.getArea());
		
		System.out.println("El diametro del circulo es: "+miCirculo.getCircunferencia());
		
		

	}

}
