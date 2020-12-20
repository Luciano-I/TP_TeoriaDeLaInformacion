package modeloParte2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Fuente {
	private double probEntrada[];
	private int cantSimbolosEntrada, cantSimbolosSalida;
	private double tablaCanal[][], entropiaPosterioriA[];

	public Fuente(double tablaEntrada[], int cantSimbolosEntrada, double tablaCanal[][], int cantSimbolosSalida) {
		this.probEntrada = tablaEntrada;
		this.cantSimbolosEntrada = cantSimbolosEntrada;
		this.tablaCanal = tablaCanal;
		this.cantSimbolosSalida = cantSimbolosSalida;
	}

	// *****
	// ENTROPÍA
	// *****

	public double getEntropiaA() {
		int i;
		double sumatoria = 0, prob;
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			prob = this.probEntrada[i];
			if (prob != 0)
				sumatoria += prob * Math.log(1.0 / prob) / Math.log(2);
		}
		return sumatoria;
	}
	
	public void generarEntropiaPosterioriA()
	{
		int i,j;
		double pADadoB, pB, sumatoria;
		this.entropiaPosterioriA = new double[this.cantSimbolosSalida];
		for (j=0;j<this.cantSimbolosSalida;j++)
		{
			sumatoria = 0;
			pB = 0;
			for (i = 0; i < this.cantSimbolosEntrada; i++)
				pB += this.tablaCanal[i][j] * this.probEntrada[i];
			for (i=0;i<this.cantSimbolosEntrada;i++)
				if (pB != 0)
				{
					pADadoB = this.tablaCanal[i][j] * this.probEntrada[i] / pB;
					if (pADadoB != 0)
						sumatoria -= pADadoB * Math.log(pADadoB) / Math.log(2);
				}
			this.entropiaPosterioriA[j] = sumatoria;
		}
	}

	public double getEntropiaB() {
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

	public double getEntropiaCanal() {
		double retorno = 0, p;
		int i, j;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				p = this.tablaCanal[i][j] * this.probEntrada[i];
				if (p != 0)
					retorno += p * Math.log(1/p) / Math.log(2);
			}
		return retorno;
	}

	// *****
	// EQUIVOCACIÓN
	// *****

	public double getEquivocacionAB() {
		double retorno = 0, pB, pADadoB;
		int i, j, k;
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			for (j = 0; j < this.cantSimbolosSalida; j++) {
				pB = 0;
				for (k = 0; k < this.cantSimbolosEntrada; k++)
					pB += this.tablaCanal[k][j] * this.probEntrada[k];
				pADadoB = this.tablaCanal[i][j] * this.probEntrada[i] / pB;
				if (pADadoB > 0)
					retorno += this.tablaCanal[i][j] * this.probEntrada[i] * Math.log(1.0 / pADadoB) / Math.log(2);
			}
		return retorno;
	}

	public double getEquivocacionBA() {
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

	// *****
	// INFORMACIÓN MUTUA
	// *****

	public double getInformacionMutuaAB() {
		return this.getEntropiaA() - this.getEquivocacionAB();
	}

	public double getInformacionMutuaBA() {
		return this.getEntropiaB() - this.getEquivocacionBA();
	}

	// *****
	// PROPIEDADES DE LA INFORMACIÓN MUTUA
	// *****

	public boolean propiedadA() {
		return this.getInformacionMutuaAB() >= 0;
	}

	public double propiedadB() {
		return this.getInformacionMutuaAB() - this.getInformacionMutuaBA();
	}

	public double propiedadC() {
		return this.getEntropiaCanal()
				- (this.getEntropiaA() + this.getEntropiaB() - this.getInformacionMutuaAB());
	}

	// *****
	// RESULTADOS PARA VENTANA
	// *****
	
	public String getResultados() {
		int i, j;
		DecimalFormat df = new DecimalFormat("#.###");
		String retorno = "<font size=\"5\"><b>Probabilidades a priori:</b><br>";
		for (i = 0; i < this.cantSimbolosEntrada; i++)
			retorno += "<b>A" + (i + 1) + ":</b> " + df.format(this.probEntrada[i]) + "<br>";
		retorno += "<br><hr><font size=\"5\"><br>";
		retorno += "<b>Matriz del canal:</b><br>&#9;";
		for (j = 0; j < this.cantSimbolosSalida; j++)
			retorno += "<b>B" + (j + 1) + "</b>&#9;";
		retorno += "<br>";
		for (i = 0; i < this.cantSimbolosEntrada; i++) {
			retorno += "<b>A" + (i + 1) + "</b>&#9;";
			for (j = 0; j < this.cantSimbolosSalida; j++)
				retorno += df.format(this.tablaCanal[i][j]) + "&#9;";
			retorno += "<br>";
		}
		retorno += "<br><hr><font size=\"5\"><br>";
		retorno += "<b>Entropías:</b><br>";
		retorno += "<b>Entropía \"a-priori\" de A - H(A) = </b>" + this.getEntropiaA() + "<br><br>";
		this.generarEntropiaPosterioriA();
		retorno += "<b>Entropías \"a-posteriori\" de A:</b><br>";
		for (j = 0; j < this.cantSimbolosSalida; j++)
			retorno += "<b>H(A/B" + (j + 1) + ")</b> = " + df.format(this.entropiaPosterioriA[j]) + "&#9;&#9;";
		retorno += "<br><br>";
		retorno += "<b>Entropía de salida del canal - H(B)</b> = " + this.getEntropiaB() + "<br>";
		retorno += "<br>";
		retorno += "<b>Entropía en el canal - H(A,B)</b> = " + this.getEntropiaCanal() + "<br>";
		retorno += "<br>";
		retorno += "<b>Equivocación:</b><br>";
		retorno += "<b>H(A/B)</b> (entropía media a posteriori) = " + this.getEquivocacionAB() + "<br>";
		retorno += "<b>H(B/A)</b> = " + this.getEquivocacionBA() + "<br>";
		retorno += "<br><hr><font size=\"5\"><br>";
		retorno += "<b>Información Mutua:</b><br>";
		retorno += "<b>I(A,B) = </b>" + this.getEntropiaA() + " - " + this.getEquivocacionAB() + "<b> = " + this.getInformacionMutuaAB() + "</b><br>";
		retorno += "<b>I(B,A) = </b>" + this.getEntropiaB() + " - " + this.getEquivocacionBA() + "<b> = " + this.getInformacionMutuaBA() + "</b><br>";
		retorno += "<br><hr><font size=\"5\"><br>";
		retorno += "<b>Propiedades de la Información Mutua:</b><br>";
		retorno += "<b>a) ¿I(A,B) >= 0?:&#9;</b>" + this.propiedadA() + "<br>";
		retorno += "<b>b) ¿I(A,B) - I(B,A) = 0?:&#9;</b>" + this.propiedadB() + "<br>";
		retorno += "<b>c) ¿H(A,B) - (H(A) + H(B) - I(A,B)) = 0?:&#9;</b>" + this.propiedadC() + "<br>";
		return retorno;
	}
}
