package modeloParte1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
			Files.writeString(Paths.get(direccionSalida), textoSalida, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			
		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo.");
		} catch (IOException e) {
			System.out.println("Hubo un problema con el Buffer.");
		}
	}
}
