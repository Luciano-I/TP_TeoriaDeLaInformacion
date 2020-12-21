package modeloParte1;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compresor {
	public static void generarRLC(String direccion)
	{
		try {
			int i, cont;
			char caracter;
			String entrada = new String(Files.readAllBytes(Paths.get(direccion))), textoSalida = "";
			String direccionSalida = direccion.replace(".txt", " - RLC.txt");
			
			i = 0;
			while (i < entrada.length())
			{
				cont = 0;
				caracter = entrada.charAt(i);
				while(i < entrada.length() && entrada.charAt(i) == caracter)
				{
					i++;
					cont++;
				}
				switch (caracter) {
					case ' ': textoSalida+= "' ' " + cont + " ";
					break;
					case '\n': textoSalida += "'\\n' " + cont + " ";
					break;
					case '\r': textoSalida += "'\\r " + cont + " ";
					break;
					case '\t': textoSalida+= "'\\t' " + cont + " ";
					break;
					default: textoSalida+= caracter + " " + cont + " "; 
				}
			}
			System.out.println(textoSalida);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(direccionSalida),"utf-16");
			BufferedWriter salida = new BufferedWriter(out);
			salida.write(textoSalida);
			salida.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo.");
		} catch (IOException e) {
			System.out.println("Hubo un problema con el Buffer.");
		}
	}
}
