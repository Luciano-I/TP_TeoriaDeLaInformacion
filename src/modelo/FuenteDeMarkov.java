package modelo;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Iterator;

public class FuenteDeMarkov extends Fuente
{
	private double transicion[][], vEstacionario[];
	private String[] indice;
	
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
	
	public void recrearMatriz()
	{
		int i,j,cont;
		String anterior, actual;
		double transicionNueva[][] = new double[this.cantSimbolos][this.cantSimbolos];
		Iterator<String> it = this.secuencia.iterator();
		anterior = it.next();
		while (it.hasNext())
		{
			actual = it.next();
			transicionNueva[buscarIndice(actual)][buscarIndice(anterior)]++;
			anterior = actual;
		}
		for (j=0;j<this.cantSimbolos;j++)
		{
			cont = 0;
			for (i=0;i<this.cantSimbolos;i++)
				cont += transicionNueva[i][j];
			for (i=0;i<this.cantSimbolos;i++)
				transicionNueva[i][j] /= cont;
		}
		this.imprimirTransiciones(transicionNueva);
	}
	
	private int buscarIndice(String elemento)
	{
		int i = 0;
		while (i<this.cantSimbolos && !this.indice[i].equals(elemento))
			i++;
		if (i<this.cantSimbolos)
			return i;
		else
			return -1;
	}
	
	private void imprimirTransiciones(double transicion[][])
	{
		int i,j;
		for (i=0;i<this.cantSimbolos;i++)
		{
			for (j=0;j<this.cantSimbolos;j++)
				System.out.print("\t" + transicion[i][j]);
			System.out.println();
		}
	}
	
	public void mostrarTabla()
	{
		this.imprimirTransiciones(this.transicion);
	}
	
	public void generarVEstacionario()
	{
		int i,j,cont = 0;
		double sumaV,sumaVAux;
		boolean termino = false;
		double vAux[] = new double[this.cantSimbolos];
		double transAux[][] = transicion.clone();
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setRoundingMode( RoundingMode.DOWN);
		
		for (i=0;i<this.cantSimbolos;i++)
			this.vEstacionario[i] = transAux[i][i];
		//M-I
		for (i=0;i<this.cantSimbolos;i++)
			transAux[i][i] -= 1;
		while (!termino)
		{
			cont++;
			//Multiplicación de matriz por vector
			for (i=0;i<this.cantSimbolos;i++)
				for (j=0;j<this.cantSimbolos;j++)
					vAux[i] += transAux[i][j] * this.vEstacionario[j];
			sumaV = sumaVAux = 0;
			for (i=0;i<this.cantSimbolos;i++)
			{
				sumaV += this.vEstacionario[i];
				sumaVAux += vAux[i];
			}
			if (Math.abs(sumaV - sumaVAux) == 0)
				termino = true;
			else
				for (i=0;i<this.cantSimbolos;i++)
				{
					this.vEstacionario[i] = vAux[i];
					vAux[i] = 0;
				}
		}
		for (i=0;i<this.cantSimbolos;i++)
			System.out.println(this.vEstacionario[i] + " ");
		System.out.println(cont);
	}
}
