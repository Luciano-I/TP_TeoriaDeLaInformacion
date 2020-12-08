package modeloParte1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FuenteTexto {
	private ArrayList<Entrada> tabla, textoEntradas;
	private String textoCodigo, textoRLC;
	private double[] probAcum;
	private String texto;
	private int contadorCaracteres;

	// Genera una fuente de memoria nula a partir de un archivo de texto.
	public FuenteTexto(String direccion) {
		try {
			Entrada aux;
			char caracter;
			HashMap<Character, Entrada> caracteresTexto = new HashMap<Character, Entrada>();
			this.contadorCaracteres = 0;
			this.tabla = new ArrayList<Entrada>();
			this.texto = new String(Files.readAllBytes(Paths.get(direccion)));
			this.textoEntradas = new ArrayList<Entrada>();
			for (int i = 0; i < this.texto.length(); i++) {
				this.contadorCaracteres++;
				caracter = this.texto.charAt(i);
				if (!caracteresTexto.containsKey(caracter)) {
					aux = new Entrada(caracter + "", 1);
					this.tabla.add(aux);
					caracteresTexto.put(caracter, aux);
				} else
					caracteresTexto.get(caracter).setOcurrencia();
				this.textoEntradas.add(caracteresTexto.get(caracter));
			}

			for (Entrada elemento : this.tabla)
				elemento.setProbabilidad(this.contadorCaracteres);
			Collections.sort(this.tabla);

		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo.");
		} catch (IOException e) {
			System.out.println("Hubo un problema con el Buffer.");
		}
	}

	//PRE: La fuente de texto contiene al menos dos símbolos distintos.
	//PRE: Las probabilidades de la tabla están ordenadas de manera ascendente.
	public void huffman() {
		if (this.tabla.size() == 2) {
			this.tabla.get(0).setCodigo("0");
			this.tabla.get(1).setCodigo("1");
		} else {
			// Ida de la recursividad
			Entrada aux1, aux2, aux3 = null;
			aux1 = this.tabla.get(0);
			this.tabla.remove(0);
			aux2 = this.tabla.get(0);
			this.tabla.remove(0);
			aux3 = new Entrada(aux1.getSimbolo() + aux2.getSimbolo(), aux1.getProbabilidad() + aux2.getProbabilidad());
			this.tabla.add(aux3);
			Collections.sort(this.tabla);
			this.huffman();
			// Vuelta de la recursividad
			aux1.setCodigo(aux3.getCodigo() + "0");
			aux2.setCodigo(aux3.getCodigo() + "1");
			this.tabla.remove(aux3);
			this.tabla.add(aux1);
			this.tabla.add(aux2);
			Collections.sort(this.tabla);
		}
	}

	public void shannonFano() {
		this.generarProbAcumSF();
		this.recShannonFano(1, this.tabla.size());
	}

	private void generarProbAcumSF() {
		int i = 1;
		double acum = 0;
		// Para invertir el orden del ArrayList
		Collections.reverse(this.tabla);
		this.probAcum = new double[this.tabla.size() + 1];
		for (Entrada elemento : this.tabla) {
			acum += elemento.getProbabilidad();
			this.probAcum[i] = acum;
			i++;
		}
	}

	public void recShannonFano(int inicio, int fin) {
		if (inicio != fin) {
			int k, i;
			Entrada elemento;
			k = this.getK(inicio, fin);
			// Para evitar llamados infinitos con los mismos parámetros
			if (k == fin)
				k--;
			for (i = inicio - 1; i < k; i++) {
				elemento = this.tabla.get(i);
				elemento.setCodigo(elemento.getCodigo() + "0");
			}
			for (i = k; i < fin; i++) {
				elemento = this.tabla.get(i);
				elemento.setCodigo(elemento.getCodigo() + "1");
			}

			this.recShannonFano(inicio, k);
			this.recShannonFano(k + 1, fin);
		}
	}
	
	// Devuelve el índice separador K para el cual la diferencia entre las sumatorias de probabilidades de cada subconjunto sea mínima 
	private int getK(int inicio, int fin) {
		int k = inicio;
		double probRango = this.probAcum[fin] - this.probAcum[inicio - 1],
				probMax = probRango / 2.0 + this.probAcum[inicio - 1];
		while (k <= fin && probAcum[k] < probMax)
			k++;
		return k;
	}

	public double getEntropia() {
		int i;
		double sumatoria = 0, prob = 0;
		for (i = 0; i < this.tabla.size(); i++)
		{
			prob = this.tabla.get(i).getProbabilidad();
			if (prob != 0)
				sumatoria += this.tabla.get(i).getProbabilidad() * Math.log(1.0 / prob) / Math.log(2);
		}
		return sumatoria;
	}

	//PRE: Se ejecutó huffman o shannonFano
	public double getLongitudMedia() {
		double retorno = 0;
		for (Entrada elemento : this.tabla)
			retorno += elemento.getProbabilidad() * elemento.getCodigo().length();
		return retorno;
	}

	public double getRendimiento() {
		return this.getEntropia() / this.getLongitudMedia();
	}

	public double getRedundancia() {
		return 1 - this.getRendimiento();
	}

	//PRE: Se ejecutó huffman o shannonFano
	public void generarTextoCodigo() {
		this.textoCodigo = "";
		for (Entrada elemento : this.textoEntradas)
			this.textoCodigo += elemento.getCodigo();	
	}
	
	//PRE: Se ejecutó generarTextoCodigo 
		public void generarRLC() {
			int i, longitud, cont;
			this.textoRLC = "";
			longitud = this.textoCodigo.length();
			i = 0;
			while (i<longitud)
	        {
	            cont = 0;
	            while (i<longitud && this.textoCodigo.charAt(i)=='0') {
	                cont++;
	                i++;
	            }
	            this.textoRLC += cont;
	            cont = 0;
	            while (i<longitud && this.textoCodigo.charAt(i)=='1'){
	                cont++;
	                i++;
	            }
	            if (cont!=0)
	            	this.textoRLC += cont;

	        }
		}

	// Se ejecutaron generarTextoCodigo y generarRLC
	public double getTasaCompresion() {
		return (double) this.textoCodigo.length() / this.textoRLC.length();
	}

	public String getTexto() {
		return this.texto;
	}

	// Se ejecutó generarTextoCodigo
	public String getTextoCodigo() {
		return this.textoCodigo;
	}

	// Se ejecutó generarRLC
	public String getTextoRLC() {
		return this.textoRLC;
	}
}
