public class Prueba {
    public static void main(String[] args) {
        FuenteDeMemoriaNula fuente = new FuenteDeMemoriaNula("mdq.TXT");
        fuente.mostrarTabla();
        fuente.Huffman();
        System.out.println("\n\n\n");
        fuente.mostrarTabla();
        fuente.generarCantInfo();
        System.out.println("Rendimiento" + fuente.getRendimiento());
        System.out.println("Redundancia" + fuente.getRedundancia());
    }
}