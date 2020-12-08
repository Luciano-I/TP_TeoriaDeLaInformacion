package modeloParte2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Fuente {
	private double probEntrada[];
	private int cantSimbolosEntrada, cantSimbolosSalida;
	private ArrayList<Integer> mensajeEntrada, mensajeSalida;
	private double cantInfoTeorica[], cantInfoExperimental[], probEntradaReal[], tablaCanal[][], tablaCanalReal[][];

	public Fuente(double tablaEntrada[], int cantSimbolosEntrada, double tablaCanal[][], int cantSimbolosSalida) {
		this.probEntrada = tablaEntrada;
		this.cantSimbolosEntrada = cantSimbolosEntrada;
		this.tablaCanal = tablaCanal;
		this.cantSimbolosSalida = cantSimbolosSalida;
		this.mensajeEntrada = new ArrayList<Integer>();
		this.mensajeSalida = new ArrayList<Integer>();
		this.probEntradaReal = new double[this.cantSimbolosEntrada];
	}

	public void mostrarTablaEntrada() {
		int i;
		System.out.println("S�mbolo:\tProbabilidad:\n");
		for (i = 1; i <= this.cantSimbolosEntrada; i++)
			System.out.println("A" + i + "\t" + this.probEntrada[i - 1] + "\n");
	}

	public void generarMensajeEntrada(int cantidad) {
		double probAcum[] = new double[this.cantSimbolosEntrada];
		double random;
		int i, j;

		// Genera el vector de probabilidades acumuladas
		probAcum[0] = probEntrada[0];
		for (i = 1; i < this.cantSimbolosEntrada; i++)
			probAcum[i] = probAcum[i - 1] + probEntrada[i];

		// Selecciona n s�mbolos al azar de acuerdo a sus probabilidades y los agrega a
		// la secuencia, siendo n el par�metro cantidad.
		for (i = 0; i < cantidad; i++) {
			random = Math.random();
			j = 0;
			while (j < this.cantSimbolosEntrada && probAcum[j] <= random)
				j++;
			if (j >= this.cantSimbolosEntrada)
				j = this.cantSimbolosEntrada - 1;
			this.mensajeEntrada.add(j);
		}

		// Genera un vector de probabilidades "reales" basadas en la cantidad de
		// ocurrencias de cada s�mbolo en la secuencia.
		// A medida que n crece, este vector tiende a las probabilidades te�ricas
		// almacenadas en la tabla.
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			this.probEntradaReal[i] = (double) Collections.frequency(this.mensajeEntrada, i)
					/ this.mensajeEntrada.size();
	}

	// PRE: Se ejecut� el m�todo generarSecuencia()
	// Genera un vector con la cantidad de informaci�n de cada s�mbolo mediante sus probabilidades reales, calculadas a partir de la secuencia generada.
	public void generarCantInfoTeorica() {
		int i;
		double prob;
		this.cantInfoTeorica = new double[this.cantSimbolosEntrada];
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			prob = this.probEntrada[i];
			if (prob != 0)
				this.cantInfoTeorica[i] = Math.log(1.0 / prob) / Math.log(2);
			else
				this.cantInfoTeorica[i] = 0;
		}
	}

	public double getEntropiaEntradaTeorica() {
		int i;
		double sumatoria = 0;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			sumatoria += this.cantInfoTeorica[i] * this.probEntrada[i];
		return sumatoria;
	}

	// PRE: Se ejecut� el m�todo generarSecuencia()
	// Genera un vector con la cantidad de informaci�n de cada s�mbolo mediante sus probabilidades reales, calculadas a partir de la secuencia generada.
	public void generarCantInfoExperimental() {
		int i;
		double prob;
		this.cantInfoExperimental = new double[this.cantSimbolosEntrada];
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			prob = this.probEntradaReal[i];
			if (prob != 0)
				this.cantInfoExperimental[i] = Math.log(1.0 / prob) / Math.log(2);
			else
				this.cantInfoExperimental[i] = 0;
		}
	}

	public double getEntropiaEntradaExperimental() {
		int i;
		double sumatoria = 0;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			sumatoria += this.cantInfoExperimental[i] * this.probEntradaReal[i];
		return sumatoria;
	}

	public double getEntropiaSalidaExperimental() {
		int i, j;
		double sumatoria = 0;
		double pB;
		for (j = 0; j < this.cantSimbolosSalida; j++) {
			pB = 0;
			for (i = 0; i < this.cantSimbolosEntrada; i++)
				pB += this.tablaCanalReal[i][j] * this.probEntradaReal[i];
			sumatoria += -pB * Math.log(pB) / Math.log(2);
		}
		return sumatoria;
	}

	public void enviarMensaje() {
		double probAcum[][] = new double[this.cantSimbolosEntrada][this.cantSimbolosSalida];
		int i, j;
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			probAcum[i][0] = this.tablaCanal[i][0];
			for (j = 1; j < this.cantSimbolosSalida; j++)
				probAcum[i][j] = probAcum[i][j - 1] + this.tablaCanal[i][j];
		}
		for (int elemento : this.mensajeEntrada) {
			double r = Math.random();
			j = 0;
			while (j < this.cantSimbolosSalida && probAcum[elemento][j] <= r)
				j++;
			if (j == this.cantSimbolosSalida)
				j--;
			this.mensajeSalida.add(j);
		}
	}

	public void mostrarMensajes() {
		System.out.println("Entrada:");
		for (int elemento : this.mensajeEntrada)
			System.out.print(" A" + (elemento + 1));
		System.out.println("\nSalida:");
		for (int elemento : this.mensajeSalida)
			System.out.print(" B" + (elemento + 1));
	}

	public double getEquivocacionTeorica() {
		double retorno = 0;
		int i, j, k;
		double pADadoB;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				pADadoB = 0;
				for (k = 0; k < this.cantSimbolosEntrada; k++)
					pADadoB += this.tablaCanal[k][j];
				pADadoB = this.tablaCanal[i][j] / pADadoB;
				if (pADadoB > 0)
					retorno += this.tablaCanal[i][j] * this.probEntrada[i] * Math.log(1.0 / pADadoB) / Math.log(2);
			}
		return retorno;
	}

	//PRE: Se ejecutó el método recrearMatrizCanal.
	public double getEquivocacionABExperimental() {
		double retorno = 0;
		int i, j, k;
		double pADadoB;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				pADadoB = 0;
				for (k = 0; k < this.cantSimbolosEntrada; k++)
					pADadoB += this.tablaCanalReal[k][j];
				pADadoB = this.tablaCanalReal[i][j] / pADadoB;
				if (pADadoB > 0)
					retorno += this.tablaCanalReal[i][j] * this.probEntradaReal[i] * Math.log(1.0 / pADadoB)
							/ Math.log(2);
			}
		return retorno;
	}

	//PRE: Se ejecutó el método recrearMatrizCanal.
	public double getEquivocacionBAExperimental() {
		double retorno = 0;
		int i, j, k;
		double pB;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				pB = 0;
				for (k = 0; k < this.cantSimbolosEntrada; k++)
					pB += this.tablaCanalReal[k][j];
				if (this.tablaCanalReal[i][j] > 0)
					retorno += this.tablaCanalReal[i][j] * this.probEntradaReal[i]
							* Math.log(1.0 / this.tablaCanalReal[i][j]) / Math.log(2);
			}
		return retorno;
	}

	//PRE: Se ejecutó el método enviarMensaje
	public void recrearTablaCanal() {
		int i, j, cantidad;
		int cont[] = new int[this.cantSimbolosEntrada];
		this.tablaCanalReal = new double[this.cantSimbolosEntrada][this.cantSimbolosSalida];
		cantidad = this.mensajeEntrada.size();
		for (i = 0; i < cantidad; i++) {
			this.tablaCanalReal[this.mensajeEntrada.get(i)][this.mensajeSalida.get(i)]++;
			cont[this.mensajeEntrada.get(i)]++;
		}
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++)
				this.tablaCanalReal[i][j] /= cont[i];
	}

	public double getEntropiaCanalTeorica()
	{
		double retorno = 0,p;
		int i,j;
		for (i=0;i<this.cantSimbolosEntrada;i++)
			for (j=0;j<this.cantSimbolosSalida;j++) {
				p = this.tablaCanal[i][j] * this.probEntrada[i];
				if (p != 0)
					retorno += -p * Math.log(p) / Math.log(2);
			}
		return retorno;
	}
	
	public double getEntropiaCanalExperimental()
	{
		double retorno = 0,p;
		int i,j;
		for (i=0;i<this.cantSimbolosEntrada;i++)
			for (j=0;j<this.cantSimbolosSalida;j++) {
				p = this.tablaCanalReal[i][j] * this.probEntradaReal[i];
				if (p != 0)
					retorno += -p * Math.log(p) / Math.log(2);
			}
		return retorno;
	}
	
	public double getInformacionMutuaTeorica() {
		return this.getEntropiaEntradaTeorica() - this.getEquivocacionTeorica();
	}

	public double getInformacionMutuaABExperimental() {
		return this.getEntropiaEntradaExperimental() - this.getEquivocacionABExperimental();
	}

	public double getInformacionMutuaBAExperimental() {
		return this.getEntropiaSalidaExperimental() - this.getEquivocacionBAExperimental();
	}

	/*
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
	
	// PRE: Se ejecutaron los m�todos generarSecuencia() y generarCantidadInfo().
	public void mostrarDatos() {
		int i;
		for (i = 0; i < this.cantSimbolos; i++) {
			System.out.println(this.tabla[i][0] + ":");
			System.out.println("Ocurrencias: " + Collections.frequency(this.mensajeEntrada, this.tabla[i][0]));
			System.out.println("Cantidad de informaci�n: " + this.cantInfo[i] + "\n");
		}
		System.out.println("\nEntropia: " + this.getEntropia());
	}
	*/
}
