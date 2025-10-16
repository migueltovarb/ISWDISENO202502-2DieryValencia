package fabricadeCarros;

import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {
		
		Fabrica checrolet =new Fabrica();
		
		List<Planta> listaPlantas = new ArrayList<Planta>();
		
		Llanta llantaPasto=new Llanta(17f, "Pista");
		Chasis chasisPasto=new Chasis(500f, Material.Acero);
		Color colorPasto=Color.Azul;
		
		Planta plantaPasto=new Planta(
				llantaPasto,
				chasisPasto,
				colorPasto
				);
		
		listaPlantas.add(plantaPasto);
		
		Fabrica mazda =new Fabrica();
				
				List<Planta> listaPlantas2 = new ArrayList<Planta>();
				
				Llanta llantaIpiales=new Llanta(22f, "Pista");
				Chasis chasisIpiales=new Chasis(600f, Material.Aluminio);
				Color colorIpiales=Color.Rojo;
				
				Planta plantaIpiales=new Planta(
						llantaIpiales,
						chasisIpiales,
						colorIpiales
						);
		
		listaPlantas2.add(plantaIpiales);
		
		for(int i=1; i<= 20; i++) {
			Carro nuevoCarroPasto=plantaPasto.fabricar();
			System.out.println(nuevoCarroPasto);
			
			Carro nuevoCarroIpiales=plantaIpiales.fabricar();
			System.out.println(nuevoCarroIpiales);
		}
		

	}

}
