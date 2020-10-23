package prueba;

import modeloParte2.FuenteDeMemoriaNula2;

public class P2IncisoB {

	public static void main(String[] args) 
	{
		String tabla[][] = {{"S1","","0.4"},{"S2","","0.1"},{"S3","","0.3"},{"S4","","0.2"}};
		int cantSimbolos = 4;
		FuenteDeMemoriaNula2 fuente = new FuenteDeMemoriaNula2(tabla,cantSimbolos);
		fuente.mostrarTabla();
		
		System.out.println("Longitud media: " + fuente.getLongitudMedia());
		System.out.println("Entropia: " + fuente.getEntropia());
		System.out.println("Es compacto: " + fuente.esCompacto());
		System.out.println("Cumple con la ley de Kraft: " + fuente.cumpleKraft());
	}
}
