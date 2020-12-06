public class Prueba {
    public static void main(String[] args) {
        FuenteDeMemoriaNula fuente = new FuenteDeMemoriaNula("mdq.txt");
        
        //fuente.Huffman();
        
        fuente.shannonFano();
        
        fuente.mostrarTabla();
        fuente.generarCantInfo();
        System.out.println("Rendimiento" + fuente.getRendimiento());
        System.out.println("Redundancia" + fuente.getRedundancia());
    }
}