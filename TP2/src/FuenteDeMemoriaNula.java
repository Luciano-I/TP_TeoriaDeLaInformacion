
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FuenteDeMemoriaNula {
	private ArrayList<Entrada> tabla = new ArrayList<Entrada>();
	private double cantInfo[];
	private double[] probAcum;

	public FuenteDeMemoriaNula(ArrayList<Entrada> entradas) {
		this.tabla = entradas;
	}

	public FuenteDeMemoriaNula(String direccion) {
		try {
			String cadena;
			char caracter;
			HashMap<Character, Integer> ocurrencias = new HashMap<Character, Integer>();
			int cont = 0;
			/*
			FileReader reader = new FileReader(direccion);
			BufferedReader buffer = new BufferedReader(reader);
			while ((cadena = buffer.readLine()) != null) {
				char caracter;
				for (int i = 0; i < cadena.length(); i++) {
					cont++;
					caracter = cadena.charAt(i);
					if (!ocurrencias.containsKey(cadena.charAt(i)))
						ocurrencias.put(caracter, 1);
					else
						ocurrencias.put(caracter, ocurrencias.get(caracter) + 1);
				}
			}
			*/
			//CAMBIO: NUEVA MANERA DE LEER EL ARCHIVO BYTE A BYTE (INCLUYE \n y \t)
			//En Windows cada salto de linea cuenta por 2: \n \r
			cadena = new String(Files.readAllBytes(Paths.get(direccion)));
			for (int i = 0; i < cadena.length(); i++) {
				cont++;
				caracter = cadena.charAt(i);
				if (!ocurrencias.containsKey(cadena.charAt(i)))
					ocurrencias.put(caracter, 1);
				else
					ocurrencias.put(caracter, ocurrencias.get(caracter) + 1);
			}

			System.out.println("Cantidad de caracteres: " + cont);
			System.out.println("\n\n\n\n\n\n\n\n\n\n");

			/*
			Map<Character, Integer> ocurrenciasOrdenadas = ocurrencias.entrySet()
			        .stream()
			        .sorted(Map.Entry.comparingByValue())
			        .collect(Collectors.toMap(
			                Map.Entry::getKey,
			                Map.Entry::getValue,
			                (viejo, nuevo) -> viejo, LinkedHashMap::new));
			Iterator itordenado = ocurrenciasOrdenadas.entrySet().iterator();
			System.out.println("\n\n");                    
			double aux;
			while (itordenado.hasNext()) {
			    Map.Entry<Character, Integer> par = (Map.Entry<Character, Integer>) itordenado.next();
			    this.tabla.add(new Entrada(par.getKey() + "", (double) par.getValue() / cont));
			}
			*/
			//CAMBIO: Nueva manera de generar la tabla ordenada
			this.tabla = new ArrayList<Entrada>();
			Iterator it = ocurrencias.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Character, Integer> entrada = (Map.Entry<Character, Integer>) it.next();
				this.tabla.add(new Entrada("" + entrada.getKey(), (double) entrada.getValue() / cont));
			}
			Collections.sort(this.tabla);
		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo.");
		} catch (IOException e) {
			System.out.println("Hubo un problema con el Buffer.");
		}
	}

	public void mostrarTabla() {
		for (Entrada aux : this.tabla) {
			System.out.println(aux.getSimbolo() + "|" + aux.getCodigo() + "|" + aux.getProbabilidad());
		}
	}

	//PRE: Las probabilidades de la tabla están ordenadas de manera ascendente.
	public void huffman() {
		if (this.tabla.size() == 2) {
			this.tabla.get(0).setCodigo("0");
			this.tabla.get(1).setCodigo("1");
		} else {
			//CAMBIO: Ya no se usa una sigFuente ni una sigTabla
			Entrada aux1, aux2, aux3 = null;
			aux1 = this.tabla.get(0);
			this.tabla.remove(0);
			aux2 = this.tabla.get(0);
			this.tabla.remove(0);
			aux3 = new Entrada(aux1.getSimbolo() + aux2.getSimbolo(), aux1.getProbabilidad() + aux2.getProbabilidad());
			this.tabla.add(aux3);
			Collections.sort(this.tabla);
			this.huffman();

			/*
			Iterator<Entrada> it = sigTabla.iterator();
			boolean encontro = false;
			while (it.hasNext() && !encontro) {
				aux3 = it.next();
				if (aux3.getSimbolo().contains(aux1.getSimbolo()))
					encontro = true;
			}
			*/
			//CAMBIO: Generando aux3 arriba no es necesario buscarlo. Misma referencia.
			aux1.setCodigo(aux3.getCodigo() + "0");
			aux2.setCodigo(aux3.getCodigo() + "1");
			this.tabla.remove(aux3);
			this.tabla.add(aux1);
			this.tabla.add(aux2);
			Collections.sort(this.tabla);
			//CAMBIO: Ya no es necesario clonar
			/*
			this.tabla.clear();
			for (Entrada elemento : sigTabla)
				this.tabla.add((Entrada) elemento.clone());
			*/
		}
	}

	public void shannonFano() {
		this.generarProbAcumSF();
		this.recShannonFano(1, this.tabla.size());
	}

	public void generarProbAcumSF() {
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
	/*
	// PRE: Se ejecutaron los métodos generarSecuencia() y generarCantidadInfo().
	@Override
	public void mostrarDatos() {
	    int i;
	    for (i = 0; i < this.cantSimbolos; i++) {
	        System.out.println(this.tabla[i][0] + ":");
	        System.out.println("Ocurrencias: " + Collections.frequency(this.secuencia, this.tabla[i][0]));
	        System.out.println("Cantidad de información: " + this.cantInfo[i] + "\n");
	    }
	    System.out.println("\nEntropia: " + this.getEntropia());
	}
	
	public void mostrarTabla() {
	    int i;
	    System.out.println("Símbolo:\tProbabilidad:\n");
	    for (i = 0; i < this.cantSimbolos; i++)
	        System.out.println(this.tabla[i][0] + "\t" + this.tabla[i][1] + "\n");
	}
	
	// PRE: Se ejecutó el método generarCantInfo.
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
	
	public String getFuente()
	{
	    int i;
	    String retorno = "Símbolo:\tProbabilidad:\n";
	    for (i=0;i<this.cantSimbolos;i++)
	        retorno += this.tabla[i][0] + "\t" + this.tabla[i][1] + "\n";
	    return retorno;
	}
	 */
}
