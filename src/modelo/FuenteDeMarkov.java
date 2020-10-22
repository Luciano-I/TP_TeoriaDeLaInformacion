package modelo;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class FuenteDeMarkov extends Fuente {
    private double transicion[][], vEstacionario[];
    private String[] indice;

    public FuenteDeMarkov(String indice[], double transicion[][], int cantSimbolos) {
        this.indice = indice;
        this.transicion = transicion;
        this.cantSimbolos = cantSimbolos;
        this.vEstacionario = new double[this.cantSimbolos];
    }

    public void generarSecuencia(int cantidad) {
        double probAcum[] = new double[this.cantSimbolos];
        double random;
        int i, j, anterior;
        anterior = (int) (Math.random() * this.cantSimbolos);
        this.secuencia.add(indice[anterior]);

        for (i = 1; i < cantidad; i++) {
            probAcum[0] = transicion[0][anterior];
            for (j = 1; j < cantSimbolos; j++)
                probAcum[j] = probAcum[j - 1] + transicion[j][anterior];
            random = Math.random();
            anterior = 0;
            while (anterior < this.cantSimbolos && probAcum[anterior] <= random)
                anterior++;
            if (anterior >= this.cantSimbolos)
                anterior = this.cantSimbolos - 1;
            this.secuencia.add(indice[anterior]);
        }
    }

    public void mostrarDatos() {
        int i;
        for (i = 0; i < this.cantSimbolos; i++) {
            System.out.println(this.indice[i] + ":");
            System.out.println("Ocurrencias: " + Collections.frequency(this.secuencia, this.indice[i]));
        }
    }

    public void recrearMatriz() {
        int i, j, cont;
        String anterior, actual;
        double transicionNueva[][] = new double[this.cantSimbolos][this.cantSimbolos];
        Iterator<String> it = this.secuencia.iterator();
        anterior = it.next();
        while (it.hasNext()) {
            actual = it.next();
            transicionNueva[buscarIndice(actual)][buscarIndice(anterior)]++;
            anterior = actual;
        }
        for (j = 0; j < this.cantSimbolos; j++) {
            cont = 0;
            for (i = 0; i < this.cantSimbolos; i++)
                cont += transicionNueva[i][j];
            for (i = 0; i < this.cantSimbolos; i++)
                transicionNueva[i][j] /= cont;
        }
        this.imprimirTransiciones(transicionNueva);
    }

    private int buscarIndice(String elemento) {
        int i = 0;
        while (i < this.cantSimbolos && !this.indice[i].equals(elemento))
            i++;
        if (i < this.cantSimbolos)
            return i;
        else
            return -1;
    }

    private void imprimirTransiciones(double transicion[][]) {
        int i, j;
        for (i = 0; i < this.cantSimbolos; i++) {
            for (j = 0; j < this.cantSimbolos; j++)
                System.out.print("\t" + transicion[i][j]);
            System.out.println();
        }
    }

    public void mostrarTabla() {
        this.imprimirTransiciones(this.transicion);
    }

    public void generarVEstacionario() {
        int i, j,k;
        boolean termino = false;
        double vAux[] = new double[this.cantSimbolos];


        //matrizAux1 es P^(n-1)                                                 matrizAux2 es P^(n)
        double matrizAux1[][] = new double[this.cantSimbolos][this.cantSimbolos], matrizAux2[][] = new double[this.cantSimbolos][this.cantSimbolos];

        copiarMatriz(matrizAux1,this.transicion);

        while (!termino) {
            //Multiplicación de matriz por matriz
            for (i=0;i<this.cantSimbolos;i++)
                for (j=0;j<this.cantSimbolos;j++){
                    matrizAux2[i][j] = 0;
                    for (k=0;k<this.cantSimbolos;k++)
                        matrizAux2[i][j] += matrizAux1[i][k] * this.transicion[k][j];
                }
            termino = true;
            i=0;
            while (i < cantSimbolos && termino) {
                termino = (matrizAux1[i][i] == matrizAux2[i][i]);
                i++;
            }
            copiarMatriz(matrizAux1,matrizAux2);
        }
        for (i = 0; i < this.cantSimbolos; i++)
            this.vEstacionario[i] = matrizAux1[i][i];
        System.out.println("\n\n");
        for (i = 0; i < this.cantSimbolos; i++)
            System.out.print(this.vEstacionario[i] + " ");
        System.out.println(sumaVector(this.vEstacionario));
    }

    private double sumaVector(double vector[]) {
        double retorno = 0;
        for (int i = 0; i < this.cantSimbolos; i++)
            retorno += vector[i];
        return retorno;
    }

    private void copiarMatriz(double matriz1[][], double matriz2[][]) //copio en 1 la 2
    {
        int i,j;
        for (i=0;i<this.cantSimbolos;i++)
            for (j=0;j<this.cantSimbolos;j++)
                matriz1[i][j] = matriz2[i][j];
    }
}
