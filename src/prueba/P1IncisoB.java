package prueba;

import modeloParte1.FuenteDeMemoriaNula;

public class P1IncisoB {

	public static void main(String[] args) 
	{
		String tabla1[][] = {{"a","0.707"},{"b","0.0727"},{"c","0.144"},{"d","0.0763"}};
		int cantSimbolos1 = 4;
		String tabla2[][] = {{"a","0.471"},{"b","0.077"},{"c","0.2"},{"d","0.0207"},{"e","0.2313"}};
		int cantSimbolos2 = 5;
		String tabla3[][] = {{"a","0.029"},{"b","0.0689"},{"c","0.00936"},{"d","0.693"},{"e","0.0122"},{"f","0.184"},{"g","0.00354"}};
		int cantSimbolos3 = 7;
		
		
		//COMPLETAR
		FuenteDeMemoriaNula fuente1 = new FuenteDeMemoriaNula(tabla1,cantSimbolos1);
		//fuente1.generarSecuencia(100000);
		//fuente1.mostrarDatos();
	}

}
