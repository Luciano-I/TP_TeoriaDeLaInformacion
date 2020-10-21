package prueba;

import modelo.FuenteDeMarkov;

public class IncisoD {

	public static void main(String[] args) 
	{
		String indice[] = {"a","b","c","d"};
		double transicion1[][] = {{0.299,0.259,0.0729,0.0647},{0.00225,0.0226,0.621,0.59},{0.622,0.524,0.0251,0.0756},{0.07675,0.1944,0.281,0.2697}};
		int cantSimbolos1 = 4;
		//double transicion2[][]
		int cantSimbolos2 = 5;
		FuenteDeMarkov fuente1 = new FuenteDeMarkov(indice,transicion1,cantSimbolos1);
		fuente1.generarSecuencia(1000);
		fuente1.mostrarDatos();
		/*for (int i=0;i<4;i++)
		{
			for (int j=0;j<4;j++)
				System.out.print("\t" + transicion1[i][j]);
			System.out.println();
		}*/
	}

}
