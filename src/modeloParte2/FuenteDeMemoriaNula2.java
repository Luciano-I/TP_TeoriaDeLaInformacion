package modeloParte2;

import java.util.TreeMap;

public class FuenteDeMemoriaNula2 {
	private String tabla[][];
	private int cantSimbolos;

	public FuenteDeMemoriaNula2(String tabla[][], int cantSimbolos) {
		int i, j, k;
		String aux;
		this.tabla = tabla;
		this.cantSimbolos = cantSimbolos;

		// Se usa una colección TreeMap para ordenar los conjuntos
		// (probabilidad,símbolo) en base al primero.
		TreeMap<Double, String> ordenados = new TreeMap<Double, String>();
		for (i = 0; i < this.cantSimbolos; i++)
			ordenados.put(Double.parseDouble(tabla[i][2]), tabla[i][0]);

		// Saca los elementos del TreeMap desde el último hasta el primero y les asigna
		// un código de ceros terminados por un uno.
		i = 0;
		while (i < this.cantSimbolos) {
			i++;
			aux = ordenados.pollLastEntry().getValue();
			j = 0;
			while (j < this.cantSimbolos && !tabla[j][0].equals(aux))
				j++;
			tabla[j][1] = "";
			for (k = 1; k < i; k++)
				tabla[j][1] += "0";
			tabla[j][1] += "1";
		}

		// Chequea que la codificación generada sea instantanea (nunca debería entrar al
		// if).
		if (!this.esInstantaneo())
			System.out.println("El código generado no es instantáneo.");
	}

	public void mostrarTabla() {
		int i;
		System.out.println("Símbolo:\tCódigo:\tProbabilidad:\n");
		for (i = 0; i < this.cantSimbolos; i++)
			System.out.println(this.tabla[i][0] + "\t" + this.tabla[i][1] + "\t" + this.tabla[i][2] + "\n");
	}

	public boolean esInstantaneo() {
		boolean retorno = true;
		int i = 0, j;
		while (i < this.cantSimbolos && retorno) {
			j = 0;
			while (j < this.cantSimbolos && retorno) {
				if (i != j)
					retorno = !this.tabla[j][1].startsWith(this.tabla[i][1]);
				j++;
			}
			i++;
		}
		return retorno;
	}

	public boolean cumpleKraft() {
		float sumatoria = 0;
		int i;
		for (i = 0; i < this.cantSimbolos; i++)
			sumatoria += Math.pow(2, -this.tabla[i][1].length());
		return sumatoria <= 1;
	}

	public double getEntropia() {
		double sumatoria = 0, p;
		int i;
		for (i = 0; i < this.cantSimbolos; i++) {
			p = Double.parseDouble(this.tabla[i][2]);
			if (p != 0)
				sumatoria += p * Math.log(1 / p) / Math.log(2);
		}
		return sumatoria;
	}

	public double getLongitudMedia() {
		double sumatoria = 0, p;
		int i;
		for (i = 0; i < this.cantSimbolos; i++) {
			p = Double.parseDouble(this.tabla[i][2]);
			sumatoria += p * this.tabla[i][1].length();
		}
		return sumatoria;
	}

	public boolean esCompacto() {
		return this.getEntropia() <= this.getLongitudMedia();
	}
}
