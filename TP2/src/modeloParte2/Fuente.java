package modeloParte2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Fuente {
	private double probEntrada[];
	private int cantSimbolosEntrada, cantSimbolosSalida;
	private ArrayList<Integer> mensajeEntrada, mensajeSalida;
	private double probEntradaReal[], tablaCanal[][], tablaCanalReal[][];

	public Fuente(double tablaEntrada[], int cantSimbolosEntrada, double tablaCanal[][], int cantSimbolosSalida) {
		this.probEntrada = tablaEntrada;
		this.cantSimbolosEntrada = cantSimbolosEntrada;
		this.tablaCanal = tablaCanal;
		this.cantSimbolosSalida = cantSimbolosSalida;
		this.mensajeEntrada = new ArrayList<Integer>();
		this.mensajeSalida = new ArrayList<Integer>();
		this.probEntradaReal = new double[this.cantSimbolosEntrada];
	}

	// *****
	// MENSAJES
	// *****

	public void generarMensajeEntrada(int cantidad) {
		double probAcum[] = new double[this.cantSimbolosEntrada];
		double random;
		int i, j;
		
		// Genera el vector de probabilidades acumuladas
		probAcum[0] = probEntrada[0];
		for (i = 1; i < this.cantSimbolosEntrada; i++)
			probAcum[i] = probAcum[i - 1] + probEntrada[i];

		// Selecciona n símbolos al azar de acuerdo a sus probabilidades y los agrega al mensaje, siendo n el parámetro cantidad.
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
		// ocurrencias de cada símbolo en el mensaje.
		// A medida que n crece, este vector tiende a las probabilidades teóricas.
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			this.probEntradaReal[i] = (double) Collections.frequency(this.mensajeEntrada, i)
					/ this.mensajeEntrada.size();
	}

	//PRE: Se ejecutó el método generarMensajeEntrada
	public void enviarMensaje() {
		double probAcum[][] = new double[this.cantSimbolosEntrada][this.cantSimbolosSalida];
		int i, j;
		// Se calcula una matriz de probabilidades acumuladas en base a la matriz de probabilidades del canal
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			probAcum[i][0] = this.tablaCanal[i][0];
			for (j = 1; j < this.cantSimbolosSalida; j++)
				probAcum[i][j] = probAcum[i][j - 1] + this.tablaCanal[i][j];
		}
		// Por cada símbolo del mensaje de entrada se genera un símbolo del alfabeto de salida en base a las probabilidades calculadas.
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

	// Crea una matriz de probabilidades para el canal en base a los mensajes generados.
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

	// *****
	// ENTROPÍA
	// *****

	public double getEntropiaATeorica() {
		int i;
		double sumatoria = 0, prob;
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			prob = this.probEntrada[i];
			if (prob != 0)
				sumatoria += this.probEntrada[i] * Math.log(1.0 / prob) / Math.log(2);
		}
		return sumatoria;
	}

	//PRE: Se ejecutó el método recrearTablaCanal
	public double getEntropiaAExperimental() {
		int i;
		double sumatoria = 0, prob;
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			prob = this.probEntradaReal[i];
			if (prob != 0)
				sumatoria += this.probEntradaReal[i] * Math.log(1.0 / prob) / Math.log(2);
		}
		return sumatoria;
	}

	public double getEntropiaBTeorica() {
		int i, j;
		double sumatoria = 0;
		double pB;
		for (j = 0; j < this.cantSimbolosSalida; j++) {
			pB = 0;
			for (i = 0; i < this.cantSimbolosEntrada; i++)
				pB += this.tablaCanal[i][j] * this.probEntrada[i];
			if (pB != 0)
				sumatoria += -pB * Math.log(pB) / Math.log(2);
		}
		return sumatoria;
	}

	//PRE: Se ejecutó el método recrearTablaCanal
	public double getEntropiaBExperimental() {
		int i, j;
		double sumatoria = 0;
		double pB;
		for (j = 0; j < this.cantSimbolosSalida; j++) {
			pB = 0;
			for (i = 0; i < this.cantSimbolosEntrada; i++)
				pB += this.tablaCanalReal[i][j] * this.probEntradaReal[i];
			if (pB != 0)
				sumatoria += -pB * Math.log(pB) / Math.log(2);
		}
		return sumatoria;
	}

	public double getEntropiaCanalTeorica() {
		double retorno = 0, p;
		int i, j;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				p = this.tablaCanal[i][j] * this.probEntrada[i];
				if (p != 0)
					retorno += -p * Math.log(p) / Math.log(2);
			}
		return retorno;
	}

	//PRE: Se ejecutó el método recrearTablaCanal
	public double getEntropiaCanalExperimental() {
		double retorno = 0, p;
		int i, j;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				p = this.tablaCanalReal[i][j] * this.probEntradaReal[i];
				if (p != 0)
					retorno += -p * Math.log(p) / Math.log(2);
			}
		return retorno;
	}

	// *****
	// EQUIVOCACIÓN
	// *****

	public double getEquivocacionABTeorica() {
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

	//PRE: Se ejecutó el método recrearTablaCanal.
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

	public double getEquivocacionBATeorica() {
		double retorno = 0;
		int i, j;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				if (this.tablaCanal[i][j] > 0)
					retorno += -this.tablaCanal[i][j] * this.probEntrada[i] * Math.log(this.tablaCanal[i][j])
							/ Math.log(2);
			}
		return retorno;
	}

	//PRE: Se ejecutó el método recrearTablaCanal.
	public double getEquivocacionBAExperimental() {
		double retorno = 0;
		int i, j;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				if (this.tablaCanalReal[i][j] > 0)
					retorno += -this.tablaCanalReal[i][j] * this.probEntradaReal[i]
							* Math.log(this.tablaCanalReal[i][j]) / Math.log(2);
			}
		return retorno;
	}

	// *****
	// INFORMACIÓN MUTUA
	// *****

	public double getInformacionMutuaABTeorica() {
		return this.getEntropiaATeorica() - this.getEquivocacionABTeorica();
	}

	public double getInformacionMutuaABExperimental() {
		return this.getEntropiaAExperimental() - this.getEquivocacionABExperimental();
	}

	public double getInformacionMutuaBATeorica() {
		return this.getEntropiaBTeorica() - this.getEquivocacionBATeorica();
	}

	public double getInformacionMutuaBAExperimental() {
		return this.getEntropiaBExperimental() - this.getEquivocacionBAExperimental();
	}

	// *****
	// PROPIEDADES DE LA INFORMACIÓN MUTUA
	// *****

	public boolean propiedadATeorica() {
		return this.getInformacionMutuaABTeorica() >= 0;
	}

	public boolean propiedadAExperimental() {
		return this.getInformacionMutuaABExperimental() >= 0;
	}

	public double propiedadBTeorica() {
		return Math.abs(this.getInformacionMutuaABTeorica() - this.getInformacionMutuaBATeorica());
	}

	public double propiedadBExperimental() {
		return Math.abs(this.getInformacionMutuaABExperimental() - this.getInformacionMutuaBAExperimental());
	}

	public double propiedadCTeorica() {
		return Math.abs(this.getEntropiaCanalTeorica()
				- (this.getEntropiaATeorica() + this.getEntropiaBTeorica() - this.getInformacionMutuaABTeorica()));
	}

	public double propiedadCExperimental() {
		return Math.abs(this.getEntropiaCanalExperimental() - (this.getEntropiaAExperimental()
				+ this.getEntropiaBExperimental() - this.getInformacionMutuaABExperimental()));
	}

	// *****
	// RESULTADOS PARA VENTANA
	// *****
	
	public String getResultadosTeoricos() {
		int i, j;
		DecimalFormat df = new DecimalFormat("#.###");
		String retorno = "Probabilidades a priori:\n";
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			retorno += "A" + (i + 1) + ": " + df.format(this.probEntrada[i]) + "\n";
		retorno += "\n*****\n\n";
		retorno += "Matriz del canal:\n\t";
		for (j = 0; j < this.cantSimbolosSalida; j++)
			retorno += "B" + (j + 1) + "\t";
		retorno += "\n";
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			retorno += "A" + (i + 1) + "\t";
			for (j = 0; j < this.cantSimbolosSalida; j++)
				retorno += df.format(this.tablaCanal[i][j]) + "\t";
			retorno += "\n";
		}
		retorno += "\n*****\n\n";
		retorno += "Equivocación:\n";
		retorno += "H(A/B) = " + this.getEquivocacionABTeorica() + "\n";
		retorno += "H(B/A) = " + this.getEquivocacionBATeorica() + "\n";
		retorno += "\n*****\n\n";
		retorno += "Información Mutua:\n";
		retorno += "I(A,B) = " + this.getInformacionMutuaABTeorica() + "\n";
		retorno += "I(B,A) = " + this.getInformacionMutuaBATeorica() + "\n";
		retorno += "\n*****\n\n";
		retorno += "Propiedades de la Información Mutua:\n";
		retorno += "a) ¿I(A,B) >= 0?:\t" + this.propiedadATeorica() + "\n";
		retorno += "b) ¿|I(A,B) - I(B,A)| = 0?:\t" + this.propiedadBTeorica() + "\n";
		retorno += "c) ¿|H(A,B) - (H(A) + H(B) - I(A,B))| = 0?:\t" + this.propiedadCTeorica() + "\n";
		return retorno;
	}
	
	public String getResultadosExperimentales() {
		int i, j;
		DecimalFormat df = new DecimalFormat("#.###");
		String retorno = "Probabilidades a priori:\n";
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			retorno += "A" + (i + 1) + ": " + df.format(this.probEntradaReal[i]) + "\n";
		retorno += "\n*****\n\n";
		retorno += "Matriz del canal:\n\t";
		for (j = 0; j < this.cantSimbolosSalida; j++)
			retorno += "B" + (j + 1) + "\t";
		retorno += "\n";
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			retorno += "A" + (i + 1) + "\t";
			for (j = 0; j < this.cantSimbolosSalida; j++)
				retorno += df.format(this.tablaCanalReal[i][j]) + "\t";
			retorno += "\n";
		}
		retorno += "\n*****\n\n";
		retorno += "Equivocación:\n";
		retorno += "H(A/B) = " + this.getEquivocacionABExperimental() + "\n";
		retorno += "H(B/A) = " + this.getEquivocacionBAExperimental() + "\n";
		retorno += "\n*****\n\n";
		retorno += "Información Mutua:\n";
		retorno += "I(A,B) = " + this.getInformacionMutuaABExperimental() + "\n";
		retorno += "I(B,A) = " + this.getInformacionMutuaBAExperimental() + "\n";
		retorno += "\n*****\n\n";
		retorno += "Propiedades de la Información Mutua:\n";
		retorno += "a) ¿I(A,B) >= 0?:\t" + this.propiedadAExperimental() + "\n";
		retorno += "b) ¿|I(A,B) - I(B,A)| = 0?:\t" + this.propiedadBExperimental() + "\n";
		retorno += "c) ¿|H(A,B) - (H(A) + H(B) - I(A,B))| = 0?:\t" + this.propiedadCExperimental() + "\n";
		retorno += "\n*****\n\n";
		retorno += "Mensaje de entrada:\n";
		for (int elemento: this.mensajeEntrada)
			retorno += "A" + (elemento+1) + "\t";
		retorno += "\nMensaje de salida:\n";
		for (int elemento: this.mensajeSalida)
			retorno += "B" + (elemento+1) + "\t";
		return retorno;
	}
}
