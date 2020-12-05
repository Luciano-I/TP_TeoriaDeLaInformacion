
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


public class FuenteDeMemoriaNula {
    private ArrayList<Entrada> tabla = new ArrayList<Entrada>();
    private double cantInfo[], cantInfoExperimental[], probabilidadesExperimental[];

    public FuenteDeMemoriaNula(ArrayList<Entrada> entradas) {
        this.tabla = entradas;
    }

    public FuenteDeMemoriaNula(String direccion) {
        try {
            String cadena;
            HashMap<Character, Integer> ocurrencias = new HashMap<Character, Integer>();
            int cont = 0;
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
            System.out.println(cont);
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
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
        } catch (FileNotFoundException e) {
            System.out.println("no se encontró el archivo");
        } catch (IOException e) {
            System.out.println("problema con buffer");
        }
    }


    public void mostrarTabla() {
        Iterator<Entrada> it = this.tabla.iterator();
        Entrada aux;
        while (it.hasNext()) {
            aux = it.next();
            System.out.println(aux.getSimbolo() + "|" + aux.getCodigo() + "|" + aux.getProbabilidad());
        }
    }

    public void Huffman() {

        if (this.tabla.size() == 2) {
            this.tabla.get(0).setCodigo("0");
            this.tabla.get(1).setCodigo("1");
        } else {
            ArrayList<Entrada> sigTabla = new ArrayList<Entrada>();
            for (Entrada elemento : this.tabla)
                sigTabla.add((Entrada) elemento.clone());
            Entrada aux1, aux2, aux3 = null;
            aux1 = sigTabla.get(0);
            sigTabla.remove(0);
            aux2 = sigTabla.get(0);
            sigTabla.remove(0);
            sigTabla.add(new Entrada(aux1.getSimbolo() + aux2.getSimbolo(), aux1.getProbabilidad() + aux2.getProbabilidad()));
            Collections.sort(sigTabla);
            FuenteDeMemoriaNula sigFuente = new FuenteDeMemoriaNula(sigTabla);
            sigFuente.Huffman();

            Iterator<Entrada> it = sigTabla.iterator();
            boolean encontro = false;
            while (it.hasNext() && !encontro) {
                aux3 = it.next();
                if (aux3.getSimbolo().contains(aux1.getSimbolo()))
                    encontro = true;
            }
            aux1.setCodigo(aux3.getCodigo() + "0");
            aux2.setCodigo(aux3.getCodigo() + "1");
            sigTabla.remove(aux3);
            sigTabla.add(aux1);
            sigTabla.add(aux2);

            Collections.sort(sigTabla);
            this.tabla.clear();
            for (Entrada elemento : sigTabla)
                this.tabla.add((Entrada) elemento.clone());
        }
    }


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

    public double getLongitudMedia()
    {
        double retorno = 0;
        for (Entrada elemento : this.tabla)
            retorno += elemento.getProbabilidad() * elemento.getCodigo().length();
        return retorno;
    }

    public double getRendimiento(){
        return this.getEntropia()/this.getLongitudMedia();
    }

    public double getRedundancia()
    {
        return  (1 - this.getRendimiento());
    }

    public void ShannonFano(){

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





    // PRE: Se ejecutó el método generarSecuencia()
    // Genera un vector con la cantidad de información de cada símbolo mediante sus probabilidades reales, calculadas a partir de la secuencia generada.
    public void generarCantInfoExperimental() {
        int i;
        double prob;
        this.cantInfoExperimental = new double[this.cantSimbolos];
        for (i = 0; i < this.cantSimbolos; i++) {
            prob = this.probabilidadesExperimental[i];
            if (prob != 0)
                this.cantInfoExperimental[i] = Math.log(1.0 / prob) / Math.log(2);
            else
                this.cantInfoExperimental[i] = 0;
        }
    }



    @Override
    public double getEntropiaExperimental() {
        int i;
        double sumatoria = 0;
        for (i = 0; i < this.cantSimbolos; i++)
            sumatoria += this.cantInfoExperimental[i] * this.probabilidadesExperimental[i];
        return sumatoria;
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

    // PRE: Se ejecutó el método generarCantInfoExperimental.
    public String getCantInfoExperimental() {
        int i;
        DecimalFormat df = new DecimalFormat("#.###");
        String retorno = "{";
        retorno += df.format(this.cantInfoExperimental[0]);
        for (i=1;i<this.cantSimbolos;i++)
            retorno += "; " + df.format(this.cantInfoExperimental[i]);
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

    // PRE: Se ejecutó el método generarSecuencia.
    public String getFuenteExperimental()
    {
        int i;
        String retorno = "Símbolo:\tProbabilidad:\n";
        for (i=0;i<this.cantSimbolos;i++)
            retorno += this.tabla[i][0] + "\t" + this.probabilidadesExperimental[i] + "\n";
        return retorno;
    }
     */
}
