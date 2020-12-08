package modeloParte1;

public class Entrada implements Comparable<Entrada> {
	private String simbolo;
	private String codigo;
	private double probabilidad;

	public Entrada(String simbolo, double probabilidad) {
		this.simbolo = simbolo;
		this.probabilidad = probabilidad;
		this.codigo = "";
	}

	public String getSimbolo() {
		return this.simbolo;
	}

	public double getProbabilidad() {
		return this.probabilidad;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String cod) {
		this.codigo = cod;
	}

	// PRE: El atributo probabilidad contiene las ocurrencias del símbolo.
	public void setProbabilidad(int cant) {
		this.probabilidad /= cant;
	}

	public void setOcurrencia() {
		this.probabilidad++;
	}

	// Devuelve -1 o 1 dependiendo de la relación entre las probabilidades de this y o
	@Override
	public int compareTo(Entrada o) {
		if (o != null)
			if (o != this)
				if (this.probabilidad < o.probabilidad)
					return -1;
				else
					return 1;
			else
				return 0;
		else
			return -1;
	}
}
