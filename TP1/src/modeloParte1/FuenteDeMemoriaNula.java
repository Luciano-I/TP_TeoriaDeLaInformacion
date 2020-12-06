package modeloParte1;

import java.text.DecimalFormat;
import java.util.Collections;

public class FuenteDeMemoriaNula extends Fuente {
	private String tabla[][];
	private double cantInfo[], cantInfoExperimental[], probabilidadesExperimental[];

	public FuenteDeMemoriaNula(String tabla[][], int cantSimbolos) {
		this.tabla = tabla;
		this.cantSimbolos = cantSimbolos;
		this.probabilidadesExperimental = new double[this.cantSimbolos];
	}

	@Override
	public void generarSecuencia(int cantidad) {
		double probAcum[] = new double[this.cantSimbolos];
		double random;
		int i, j;

		// Genera el vector de probabilidades acumuladas
		probAcum[0] = Float.parseFloat(tabla[0][1]);
		for (i = 1; i < this.cantSimbolos; i++)
			probAcum[i] = probAcum[i - 1] + Double.parseDouble(tabla[i][1]);

		// Selecciona n s�mbolos al azar de acuerdo a sus probabilidades y los agrega a
		// la secuencia, siendo n el par�metro cantidad.
		for (i = 0; i < cantidad; i++) {
			random = Math.random();
			j = 0;
			while (j < this.cantSimbolos && probAcum[j] <= random)
				j++;
			if (j >= this.cantSimbolos)
				j = this.cantSimbolos - 1;
			this.secuencia.add(this.tabla[j][0]);
		}

		// Genera un vector de probabilidades "reales" basadas en la cantidad de
		// ocurrencias de cada s�mbolo en la secuencia.
		// A medida que n crece, este vector tiende a las probabilidades te�ricas
		// almacenadas en la tabla.
		for (i = 0; i < this.cantSimbolos; i++)
			this.probabilidadesExperimental[i] = (double) Collections.frequency(this.secuencia, this.tabla[i][0])
					/ this.secuencia.size();
	}

	// PRE: Se ejecutaron los m�todos generarSecuencia() y generarCantidadInfo().
	@Override
	public void mostrarDatos() {
		int i;
		for (i = 0; i < this.cantSimbolos; i++) {
			System.out.println(this.tabla[i][0] + ":");
			System.out.println("Ocurrencias: " + Collections.frequency(this.secuencia, this.tabla[i][0]));
			System.out.println("Cantidad de informaci�n: " + this.cantInfo[i] + "\n");
		}
		System.out.println("\nEntropia: " + this.getEntropia());
	}

	public void generarCantInfo() {
		int i;
		double prob;
		this.cantInfo = new double[this.cantSimbolos];
		for (i = 0; i < this.cantSimbolos; i++) {
			prob = Double.parseDouble(tabla[i][1]);
			if (prob != 0)
				this.cantInfo[i] = Math.log(1.0 / prob) / Math.log(2);
			else
				this.cantInfo[i] = 0;
		}
	}
	
	// PRE: Se ejecut� el m�todo generarSecuencia()
	// Genera un vector con la cantidad de informaci�n de cada s�mbolo mediante sus probabilidades reales, calculadas a partir de la secuencia generada.
	public void generarCantInfoExperimental() {
		int i;
		double prob;
		this.cantInfoExperimental = new double[this.cantSimbolos];
		for (i = 0; i < this.cantSimbolos; i++) {
			prob = this.probabilidadesExperimental[i];
			if (prob != 0)
				this.cantInfoExperimental[i] = Math.log(1.0 / prob) / Math.log(2);
			else
				this.cantInfoExperimental[i] = 0;
		}
	}

	// PRE: Se ejecut� el m�todo generarCantidadInfo().
	@Override
	public double getEntropia() {
		int i;
		double sumatoria = 0;
		for (i = 0; i < this.cantSimbolos; i++)
			sumatoria += this.cantInfo[i] * Double.parseDouble(tabla[i][1]);
		return sumatoria;
	}
	
	@Override
	public double getEntropiaExperimental() {
		int i;
		double sumatoria = 0;
		for (i = 0; i < this.cantSimbolos; i++)
			sumatoria += this.cantInfoExperimental[i] * this.probabilidadesExperimental[i];
		return sumatoria;
	}

	public void mostrarTabla() {
		int i;
		System.out.println("S�mbolo:\tProbabilidad:\n");
		for (i = 0; i < this.cantSimbolos; i++)
			System.out.println(this.tabla[i][0] + "\t" + this.tabla[i][1] + "\n");
	}
	
	// PRE: Se ejecut� el m�todo generarCantInfo.
	public String getCantInfo() {
		int i;
		DecimalFormat df = new DecimalFormat("#.###");
		String retorno = "{";
		retorno += df.format(this.cantInfo[0]);
		for (i=1;i<this.cantSimbolos;i++)
			retorno += "; " + df.format(this.cantInfo[i]);
		retorno += "}";
		return retorno;
	}
	
	// PRE: Se ejecut� el m�todo generarCantInfoExperimental.
	public String getCantInfoExperimental() {
		int i;
		DecimalFormat df = new DecimalFormat("#.###");
		String retorno = "{";
		retorno += df.format(this.cantInfoExperimental[0]);
		for (i=1;i<this.cantSimbolos;i++)
			retorno += "; " + df.format(this.cantInfoExperimental[i]);
		retorno += "}";
		return retorno;
	}
	
	public String getFuente()
	{
		int i;
		String retorno = "S�mbolo:\tProbabilidad:\n";
		for (i=0;i<this.cantSimbolos;i++)
			retorno += this.tabla[i][0] + "\t" + this.tabla[i][1] + "\n";
		return retorno;
	}
	
	// PRE: Se ejecut� el m�todo generarSecuencia.
	public String getFuenteExperimental()
	{
		int i;
		String retorno = "S�mbolo:\tProbabilidad:\n";
		for (i=0;i<this.cantSimbolos;i++)
			retorno += this.tabla[i][0] + "\t" + this.probabilidadesExperimental[i] + "\n";
		return retorno;
	}
}
