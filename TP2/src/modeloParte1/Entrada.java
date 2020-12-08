package modeloParte1;

import java.util.ArrayList;

public class Entrada implements Cloneable, Comparable<Entrada> {
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

    public void setProbabilidad(int cant)
    {
        this.probabilidad/=cant;
    }
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void setOcurrencia()
    {
        this.probabilidad++;
    }

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
