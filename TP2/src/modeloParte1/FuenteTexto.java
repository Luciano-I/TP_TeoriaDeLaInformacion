package modeloParte1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//
//
//CAMBIO: VER EL MÉTODO GENERARRLC
//
//
public class FuenteTexto {
	private ArrayList<Entrada> tabla, textoEntradas;
	private double cantInfo[];
	private String textoCodificado, textoRLC;
	private double[] probAcum;
	private String texto;
	private int contadorCaracteres;

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

	public void mostrarTabla() {
		for (Entrada aux : this.tabla) {
			System.out.println(
					aux.getSimbolo() + "|" + aux.getCodigo() + "|" + aux.getProbabilidad());
		}
	}

	//PRE: La fuente de texto contiene al menos dos símbolos distintos.
	//PRE: Las probabilidades de la tabla están ordenadas de manera ascendente.
	public void huffman() {
		if (this.tabla.size() == 2) {
			this.tabla.get(0).setCodigo("0");
			this.tabla.get(1).setCodigo("1");
		} else {
			Entrada aux1, aux2, aux3 = null;
			aux1 = this.tabla.get(0);
			this.tabla.remove(0);
			aux2 = this.tabla.get(0);
			this.tabla.remove(0);
			aux3 = new Entrada(aux1.getSimbolo() + aux2.getSimbolo(), aux1.getProbabilidad() + aux2.getProbabilidad());
			this.tabla.add(aux3);
			Collections.sort(this.tabla);
			this.huffman();

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
		//Para invertir el orden del ArrayList
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

	private int getK(int inicio, int fin) {
		int k = inicio;
		double probRango = this.probAcum[fin] - this.probAcum[inicio - 1],
				probMax = probRango / 2.0 + this.probAcum[inicio - 1];
		while (k <= fin && probAcum[k] < probMax)
			k++;
		return k;
	}

	//El orden del vector generado depende del orden de la tabla (código utilizado)
	public void generarCantInfo() {
		int i;
		double prob;
		this.cantInfo = new double[this.tabla.size()];
		for (i = 0; i < this.tabla.size(); i++) {
			prob = this.tabla.get(i).getProbabilidad();
			if (prob != 0)
				this.cantInfo[i] = Math.log(1.0 / prob) / Math.log(2);
			else
				this.cantInfo[i] = 0;
		}
	}

	// PRE: Se ejecutó el método generarCantidadInfo().
	public double getEntropia() {
		int i;
		double sumatoria = 0;
		for (i = 0; i < this.tabla.size(); i++)
			sumatoria += this.cantInfo[i] * this.tabla.get(i).getProbabilidad();
		return sumatoria;
	}

	//PRE: Ejecutar Huffman o Shanon-Fano
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
		return (1 - this.getRendimiento());
	}

	//PRE: Ejecutar Huffman o Shanon-Fano
	public void generarTextoCodigo() {
		this.textoCodificado = "";
		for (Entrada elemento : this.textoEntradas)
			this.textoCodificado += elemento.getCodigo();	
	}
	
	
	//CAMBIOS: La generación de RLC/compresión no tiene que ser símbolo a símbolo sino con el texto entero.
	//Antes le asignabamos a cada Entrada un RLC y después las concatenabamos, pero quedaban muchísimos ceros en medio.
	//De esa manera no era descomprimible (a menos que tengas una tabla que te diga qué RLC le corresponde a qué símbolo).
	//Por ejemplo si A=1110 tendría RLC=031 y M=0000000 tendría RLC=7, AM tendría RLC=0317 que se descomprime como 11101111111 (nada que ver con lo que en realidad es)
	//PRE: Se ejecutó generarTextoCodigo 
		public void generarRLC() {
			int i, longitud, cont;
			this.textoRLC = "";
			longitud = this.textoCodificado.length();
			i = 0;
			while (i<longitud)
	        {
	            cont = 0;
	            while (i<longitud && this.textoCodificado.charAt(i)=='0') {
	                cont++;
	                i++;
	            }
	            this.textoRLC += cont;
	            cont = 0;
	            while (i<longitud && this.textoCodificado.charAt(i)=='1'){
	                cont++;
	                i++;
	            }
	            if (cont!=0)
	            	this.textoRLC += cont;

	        }
		}

	public double getTasaCompresion() {
		return (double) this.textoCodificado.length() / this.textoRLC.length();
	}

	public String getStringOriginal() {
		return this.texto;
	}

	public String getStringCodigo() {
		return this.textoCodificado;
	}

	public String getStringRLC() {
		return this.textoRLC;
	}
}
