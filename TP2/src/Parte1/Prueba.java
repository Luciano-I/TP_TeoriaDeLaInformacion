package Parte1;

public class Prueba {
    public static void main(String[] args) {
        FuenteDeMemoriaNula fuente = new FuenteDeMemoriaNula("mdq2.txt");
        
        //fuente.huffman();
        
        fuente.shannonFano();
        fuente.generarCantInfo();
        System.out.println("Rendimiento" + fuente.getRendimiento());
        System.out.println("Redundancia" + fuente.getRedundancia());
        fuente.mostrarTabla();
        fuente.generarTextoCodigo();
        System.out.println(fuente.getTasaCompresion());
    }
}