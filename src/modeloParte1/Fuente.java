package modeloParte1;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Fuente {
	protected int cantSimbolos;
	protected ArrayList<String> secuencia = new ArrayList<String>();

	public abstract void generarSecuencia(int cantidad);

	public void mostrarSecuencia() {
		Iterator<String> it = this.secuencia.iterator();
		while (it.hasNext())
			System.out.print(it.next() + " ");
	}

	public abstract void mostrarDatos();

	public abstract double getEntropia();
}
