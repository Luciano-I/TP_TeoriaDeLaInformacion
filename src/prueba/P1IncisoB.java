package prueba;

import modeloParte1.FuenteDeMemoriaNula;

public class P1IncisoB {

	public static void main(String[] args) 
	{
		String tabla1[][] = {{"a","0.707"},{"b","0.0727"},{"c","0.144"},{"d","0.0763"}};
		int cantSimbolos1 = 4;
		FuenteDeMemoriaNula fuente1 = new FuenteDeMemoriaNula(tabla1,cantSimbolos1);
		
		String tabla2[][] = {{"a","0.471"},{"b","0.077"},{"c","0.2"},{"d","0.0207"},{"e","0.2313"}};
		int cantSimbolos2 = 5;
		FuenteDeMemoriaNula fuente2 = new FuenteDeMemoriaNula(tabla2,cantSimbolos2);
		
		String tabla3[][] = {{"a","0.029"},{"b","0.0689"},{"c","0.00936"},{"d","0.693"},{"e","0.0122"},{"f","0.184"},{"g","0.00354"}};
		int cantSimbolos3 = 7;
		FuenteDeMemoriaNula fuente3 = new FuenteDeMemoriaNula(tabla3,cantSimbolos3);
		
		//PARA PROBAR FUENTE 1
		/*
		fuente1.generarSecuencia(100000);
		fuente1.generarCantidadInfo();
		fuente1.mostrarDatos();
		*/
		
		//PARA PROBAR FUENTE 2
		/*
		fuente2.generarSecuencia(100000);
		fuente2.generarCantidadInfo();
		fuente2.mostrarDatos();
		*/
		
		//PARA PROBAR FUENTE 3
		
		fuente3.generarSecuencia(100);
		fuente3.generarCantidadInfo();
		fuente3.mostrarDatos();
		
	}

}
