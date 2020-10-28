package modeloParte1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class FuenteDeMemoriaNula extends Fuente
{
	private String tabla[][]; 
	private double cantInfo[], probabilidadesReales[];
	
	public FuenteDeMemoriaNula(String tabla[][],int cantSimbolos)
	{
		this.tabla = tabla;
		this.cantSimbolos = cantSimbolos;
		this.probabilidadesReales = new double[this.cantSimbolos];
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
			this.probabilidadesReales[i] = (double) Collections.frequency(this.secuencia, this.tabla[i][0]) / this.secuencia.size();
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
	
	public void generarCantidadInfo()
	{
		int i;
		double prob;
		this.cantInfo = new double[this.cantSimbolos];
		for (i=0;i<this.cantSimbolos;i++)
		{
			prob = Double.parseDouble(tabla[i][1]);
			if (prob != 0)
				this.cantInfo[i] = Math.log(1.0 / prob) / Math.log(2);
			else
				this.cantInfo[i] = 0;
		}
	}
	
	public double getEntropia()
	{
		int i;
		double sumatoria = 0;
		for (i=0;i<this.cantSimbolos;i++)
			sumatoria += this.cantInfo[i] * Double.parseDouble(tabla[i][1]);
		return sumatoria;
	}
	
	public void mostrarTabla()
	{
		int i;
		System.out.println("Símbolo:\tProbabilidad:\n");
		for (i=0;i<this.cantSimbolos;i++)
			System.out.println(this.tabla[i][0] + "\t" + this.tabla[i][1] + "\n");
	}
}
