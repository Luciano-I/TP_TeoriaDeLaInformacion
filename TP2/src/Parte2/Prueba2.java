package Parte2;

public class Prueba2 {
	public static void main(String[] args) {
		double tablaEntrada[] = {0.3,0.3,0.3,0.05,0.05};
		double tablaCanal[][] = {{0,0,0,0.2,0.8},{0.5,0.4,0.1,0,0},{0.1,0.25,0.25,0.3,0.1},{0.2,0.2,0.2,0.2,0.2},{0,0,1,0,0}};
		Fuente fuente = new Fuente(tablaEntrada,5,tablaCanal,5);
		fuente.generarMensajeEntrada(1000);
		fuente.enviarMensaje();
		fuente.mostrarMensajes();
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		fuente.recrearTablaCanal();
		fuente.generarCantInfoTeorica();
		System.out.println(fuente.getInformacionMutuaTeorica());
		fuente.generarCantInfoExperimental();
		System.out.println(fuente.getInformacionMutuaExperimental());
	}
}
