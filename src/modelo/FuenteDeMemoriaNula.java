package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


//REVISAR COMO SE USAN LOS STRINGS. HAY QUE USAR EL CONSTRUCTOR
public class FuenteDeMemoriaNula 
{
	private int cantSimbolos;
	private String tabla[][]; 
	private ArrayList<String> secuencia = new ArrayList<String>();
	private double cantInfo[], probabilidades[];
	
	public FuenteDeMemoriaNula(String tabla[][],int cantSimbolos)
	{
		this.tabla = tabla;
		this.cantSimbolos = cantSimbolos;
		this.probabilidades = new double[this.cantSimbolos];
	}
	
	public void generarSecuencia(int cantidad)
	{
		double probAcum[] = new double[this.cantSimbolos];
		double random;
		int i,j;
		probAcum[0] = Float.parseFloat(tabla[0][1]);
		
		for (i=1;i<this.cantSimbolos;i++)
			probAcum[i] = probAcum[i-1] + Double.parseDouble(tabla[i][1]);
		
		for (i=0;i<cantidad;i++)
		{
			random = Math.random();
			j = 0;
			while (j<this.cantSimbolos && probAcum[j] <= random)
				j++;
			if (j>=this.cantSimbolos)
				j = this.cantSimbolos - 1;
			this.secuencia.add(this.tabla[j][0]);
		}
		
		for (i=0;i<this.cantSimbolos;i++)
			this.probabilidades[i] = (double) Collections.frequency(this.secuencia, this.tabla[i][0]) / this.secuencia.size();
		
		this.generarCantidadInfo();
	}
	
	public void mostrarSecuencia()
	{
		Iterator<String> it = this.secuencia.iterator();
		while (it.hasNext())
			System.out.print(it.next() + " ");
	}
	
	public void mostrarDatos()
	{
		int i;
		for (i=0;i<this.cantSimbolos;i++)
		{
			System.out.println(this.tabla[i][0] + ":");
			System.out.println("Ocurrencias: " + Collections.frequency(this.secuencia, this.tabla[i][0]));
			System.out.println("Cantidad de información: " + this.cantInfo[i] + "\n");
		}
		System.out.println("\nEntropia: " + this.getEntropia());
	}
	
	private void generarCantidadInfo()
	{
		int i, ocurrencias;
		this.cantInfo = new double[this.cantSimbolos];
		for (i=0;i<this.cantSimbolos;i++)
		{
			ocurrencias = Collections.frequency(this.secuencia, this.tabla[i][0]);
			if (ocurrencias != 0)
				this.cantInfo[i] = Math.log(1.0 / this.probabilidades[i]) / Math.log(2);
			else
				this.cantInfo[i] = 0;
		}
	}
	
	public double getEntropia()
	{
		int i;
		double sumatoria = 0;
		for (i=0;i<this.cantSimbolos;i++)
			sumatoria += this.cantInfo[i] * this.probabilidades[i];
		return sumatoria;
	}
	
	public FuenteDeMemoriaNula getOrdenN(int n)
	{
		int cantNueva = (int) Math.pow(this.cantSimbolos, n),h,i,j,k,l,tamBloque,repeticiones;
		String tablaNueva[][] = new String[cantNueva][3];
		double probNuevas[] = new double[cantNueva];
		
		//Inicializo la tabla nueva
		tamBloque = cantNueva;
		for (k=0;k<cantNueva;k++)
		{
			tablaNueva[k][0] = "";
			probNuevas[k] = 1;
		}
		
		//Realizo la extensión
		repeticiones = 1;
		for (h=0;h<n;h++) //En cada pasada agrego un símbolo del conjunto original a cada secuencia
		{
			k = 0;
			tamBloque /= this.cantSimbolos;
			for (l=0;l<repeticiones;l++) //En cada pasada agrego un bloque de cada símbolo original
				for (i=0;i<this.cantSimbolos;i++)
					for (j=0;j<tamBloque;j++)
					{
						tablaNueva[k][0] += this.tabla[i][0]; //La tabla no tiene formato simbolo-codigo-prob?
						probNuevas[k] *= Double.parseDouble(this.tabla[i][1]); //La tabla no tiene formato simbolo-codigo-prob?
						k++;
					}
			repeticiones *= this.cantSimbolos;
		}
		
		//Asigno las probabilidades (en formato String para respetar la tabla)
		for (k=0;k<cantNueva;k++)
			tablaNueva[k][1] = Double.toString(probNuevas[k]); //La tabla no tiene formato simbolo-codigo-prob?
			
		return new FuenteDeMemoriaNula(tablaNueva,cantNueva);
	}
	
	public void mostrarTabla()
	{
		int i;
		System.out.println("Símbolo:\tProbabilidad:\n");
		for (i=0;i<this.cantSimbolos;i++)
			System.out.println(this.tabla[i][0] + "\t" + this.tabla[i][1] + "\n");
	}
}
