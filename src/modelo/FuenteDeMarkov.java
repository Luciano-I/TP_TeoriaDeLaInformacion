package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class FuenteDeMarkov 
{
	private int cantSimbolos;
	private double transicion[][], vEstacionario[];
	private String indice[];
	private ArrayList<String> secuencia = new ArrayList<String>();
	
	public FuenteDeMarkov(String indice[],double transicion[][],int cantSimbolos)
	{
		this.indice = indice;
		this.transicion = transicion;
		this.cantSimbolos = cantSimbolos;
		this.vEstacionario = new double[this.cantSimbolos];
	}
	
	public void generarSecuencia(int cantidad)
	{
		double probAcum[] = new double[this.cantSimbolos];
		double random;
		int i,j,anterior;
		anterior = (int) (Math.random() * this.cantSimbolos);
		this.secuencia.add(indice[anterior]);
		
		for (i=1;i<cantidad;i++)
		{
			probAcum[0] = transicion[0][anterior];
			for (j=1;j<cantSimbolos;j++)
				probAcum[j] = probAcum[j-1] + transicion[j][anterior];			
			random = Math.random();			
			anterior=0;
			while (anterior<this.cantSimbolos && probAcum[anterior] <= random)
				anterior++;
			if (anterior>=this.cantSimbolos)
				anterior = this.cantSimbolos - 1;
			this.secuencia.add(indice[anterior]);
		}
	}
	
	public void mostrarDatos()
	{
		int i;
		for (i=0;i<this.cantSimbolos;i++)
		{
			System.out.println(this.indice[i] + ":");
			System.out.println("Ocurrencias: " + Collections.frequency(this.secuencia, this.indice[i]));
		}
	}
}
