package modeloParte1;

import java.util.Collections;
import java.util.Iterator;

public class FuenteDeMarkov extends Fuente {
	private double transicion[][], vEstacionario[];
	private String[] indice;

	public FuenteDeMarkov(String indice[], double transicion[][], int cantSimbolos) {
		this.indice = indice;
		this.transicion = transicion;
		this.cantSimbolos = cantSimbolos;
		this.vEstacionario = new double[this.cantSimbolos];
	}

	@Override
	public void generarSecuencia(int cantidad) {
		double probAcum[] = new double[this.cantSimbolos];
		double random;
		int i, j, anterior;

		// Añade un primer elemento al azar a la secuencia. En este caso se tratan como
		// equiprobables.
		anterior = (int) (Math.random() * this.cantSimbolos);
		this.secuencia.add(indice[anterior]);

		// Agrega n-1 elementos a la secuencia, siendo n el parámetro cantidad.
		for (i = 1; i < cantidad; i++) {
			// Genera un vector de probabilidades acumuladas a partir del último elemento
			// agregado a la secuencia.
			probAcum[0] = transicion[0][anterior];
			for (j = 1; j < cantSimbolos; j++)
				probAcum[j] = probAcum[j - 1] + transicion[j][anterior];

			// Selecciona un elemento al azar basado en la función de probabilidades y lo
			// agrega a la secuencia.
			random = Math.random();
			anterior = 0;
			while (anterior < this.cantSimbolos && probAcum[anterior] <= random)
				anterior++;
			if (anterior >= this.cantSimbolos)
				anterior = this.cantSimbolos - 1;
			this.secuencia.add(indice[anterior]);
		}
	}

	public void generarVEstacionario() {
		int i, j, k;
		boolean termino = false;

		// Matriz anterior y actual respectivamente
		double matrizAux1[][] = new double[this.cantSimbolos][this.cantSimbolos],
				matrizAux2[][] = new double[this.cantSimbolos][this.cantSimbolos];

		copiarMatriz(matrizAux1, this.transicion);

		while (!termino) {
			// Multiplicación de matriz por matriz
			for (i = 0; i < this.cantSimbolos; i++)
				for (j = 0; j < this.cantSimbolos; j++) {
					matrizAux2[i][j] = 0;
					for (k = 0; k < this.cantSimbolos; k++)
						matrizAux2[i][j] += matrizAux1[i][k] * this.transicion[k][j];
				}
			// Chequea si las matrices son iguales elemento a elemento
			termino = true;
			i = 0;
			while (i < cantSimbolos && termino) {
				termino = (matrizAux1[i][i] == matrizAux2[i][i]);
				i++;
			}
			copiarMatriz(matrizAux1, matrizAux2);
		}
		// Asigna el vector estacionario
		for (i = 0; i < this.cantSimbolos; i++)
			this.vEstacionario[i] = matrizAux2[i][i];
		// Compensa por posibles errores de redondeo
		this.vEstacionario[1] += 1 - sumaVector(this.vEstacionario);
	}

	// Copia en matriz1 los datos de matriz2
	private void copiarMatriz(double matriz1[][], double matriz2[][]) {
		int i, j;
		for (i = 0; i < this.cantSimbolos; i++)
			for (j = 0; j < this.cantSimbolos; j++)
				matriz1[i][j] = matriz2[i][j];
	}

	private double sumaVector(double vector[]) {
		double retorno = 0;
		for (int i = 0; i < this.cantSimbolos; i++)
			retorno += vector[i];
		return retorno;
	}

	// PRE: Se ejecutó el método generarVEstacionario.
	@Override
	public double getEntropia() {
		double sumatoria;
		int i, j;
		double retorno = 0;
		for (j = 0; j < this.cantSimbolos; j++) {
			sumatoria = 0;
			for (i = 0; i < this.cantSimbolos; i++)
				if (transicion[i][j] != 0)
					sumatoria += -transicion[i][j] * Math.log(transicion[i][j]) / Math.log(2);
			retorno += sumatoria * this.vEstacionario[j];
		}
		return retorno;
	}

	// PRE: Ya se ejecutaron los métodos generarSecuencia y generarVEstacionario.
	@Override
	public void mostrarDatos() {
		int i;
		System.out.println("Ocurrencias de cada símbolo:");
		for (i = 0; i < this.cantSimbolos; i++)
			System.out.println(this.indice[i] + ": " + Collections.frequency(this.secuencia, this.indice[i]));
		System.out.println("\nVector estacionario: ");
		for (i = 0; i < this.cantSimbolos; i++)
			System.out.print(this.vEstacionario[i] + " ");
		System.out.println("\nSuma del vector estacionario: " + this.sumaVector(this.vEstacionario));
		System.out.println("\nLa entropia es: " + this.getEntropia());
	}

	// Crea y muestra una nueva matriz de transición basada en la secuencia
	// generada, para chequear que las probabilidades son consistentes.
	// PRE: Se ejecutó el método generarSecuencia.
	public void recrearMatriz() {
		int i, j, cont;
		String anterior, actual;
		double transicionNueva[][] = new double[this.cantSimbolos][this.cantSimbolos];
		Iterator<String> it = this.secuencia.iterator();
		anterior = it.next();
		while (it.hasNext()) {
			actual = it.next();
			transicionNueva[buscarIndice(actual)][buscarIndice(anterior)]++;
			anterior = actual;
		}
		for (j = 0; j < this.cantSimbolos; j++) {
			cont = 0;
			for (i = 0; i < this.cantSimbolos; i++)
				cont += transicionNueva[i][j];
			for (i = 0; i < this.cantSimbolos; i++)
				transicionNueva[i][j] /= cont;
		}
		this.imprimirTransiciones(transicionNueva);
	}

	// Devuelve la posición de elemento en el índice. Si no existe devuelve -1.
	private int buscarIndice(String elemento) {
		int i = 0;
		while (i < this.cantSimbolos && !this.indice[i].equals(elemento))
			i++;
		if (i < this.cantSimbolos)
			return i;
		else
			return -1;
	}

	// Imprime los datos de la matriz pasada por parámetro.
	private void imprimirTransiciones(double transicion[][]) {
		int i, j;
		for (i = 0; i < this.cantSimbolos; i++) {
			for (j = 0; j < this.cantSimbolos; j++)
				System.out.print("\t" + transicion[i][j]);
			System.out.println();
		}
	}

	public void mostrarMatriz() {
		this.imprimirTransiciones(this.transicion);
	}

}
