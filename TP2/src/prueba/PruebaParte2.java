package prueba;

import modeloParte2.Fuente;
import vista.VentanaParte2;

public class PruebaParte2 {
	public static void main(String[] args) {
		double tablaEntrada[] = {0.5,0.5};
		double tablaCanal[][] = {{0.5,0,0.5},{0,1,0}};
		Fuente fuente = new Fuente(tablaEntrada,2,tablaCanal,3);
		fuente.generarMensajeEntrada(1000);
		fuente.enviarMensaje();
		fuente.mostrarMensajes();
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		fuente.recrearTablaCanal();
		fuente.generarCantInfoTeorica();
		fuente.generarCantInfoExperimental();
		
		System.out.println(fuente.getInformacionMutuaABExperimental());
		System.out.println(fuente.getInformacionMutuaBAExperimental());
		System.out.println();
		System.out.println(fuente.getEntropiaCanalExperimental());
		System.out.println(fuente.getEntropiaEntradaExperimental() + fuente.getEntropiaSalidaExperimental() - fuente.getInformacionMutuaABExperimental());
		
		VentanaParte2 ventana = new VentanaParte2();
	}
}
