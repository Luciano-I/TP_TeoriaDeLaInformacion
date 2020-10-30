package prueba;

import modeloParte1.FuenteDeMarkov;

public class P1IncisoD {

	public static void main(String[] args)
	{
		String indice1[] = {"a","b","c","d"};
		double transicion1[][] = {{0.299,0.259,0.0729,0.0647},{0.00225,0.0226,0.621,0.59},{0.622,0.524,0.0251,0.0756},{0.07675,0.1944,0.281,0.2697}};
		int cantSimbolos1 = 4;
		FuenteDeMarkov fuente1 = new FuenteDeMarkov(indice1,transicion1,cantSimbolos1);
		
		String indice2[] = {"a","b","c","d","e"};
		double transicion2[][] = {{0.45,0.178,0.128,0.37,0.0898},{0.221,0.426,0.401,0.46,0.0787},{0.03,0.127,0.0668,0.0486,0.565},{0.238,0.155,0.388,0.0624,0.062},{0.061,0.114,0.0162,0.059,0.2045}};
		int cantSimbolos2 = 5;
		FuenteDeMarkov fuente2 = new FuenteDeMarkov(indice2,transicion2,cantSimbolos2);
		
		//PARA PROBAR FUENTE 1
		/*
		fuente1.generarSecuencia(100000);
		fuente1.generarVEstacionario();
		fuente1.generarEntropia();
		fuente1.mostrarDatos();
		*/
		
		//PARA PROBAR FUENTE 2
		
		fuente2.generarSecuencia(100000);
		fuente2.generarVEstacionario();
		fuente2.generarEntropia();
		fuente2.mostrarDatos();
		fuente2.mostrarMatriz();
	}
}
