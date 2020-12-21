package modeloParte1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RLC {
	private String textoEntrada, textoSalida;
	private double tasaCompresion;

	public RLC(String direccion) {
		try {
			int i, cont;
			char caracter;
			this.textoEntrada = new String(Files.readAllBytes(Paths.get(direccion)));
			this.textoSalida = "";
			String direccionSalida = direccion.replace(".txt", " - RLC.txt");
			i = 0;
			while (i < this.textoEntrada.length()) {
				cont = 0;
				caracter = this.textoEntrada.charAt(i);
				while (i < this.textoEntrada.length() && this.textoEntrada.charAt(i) == caracter) {
					i++;
					cont++;
				}
				switch (caracter) {
				case ' ':
					this.textoSalida += "' ' " + cont + " ";
					break;
				case '\n':
					this.textoSalida += "'\\n' " + cont + " ";
					break;
				case '\r':
					this.textoSalida += "'\\r " + cont + " ";
					break;
				case '\t':
					this.textoSalida += "'\\t' " + cont + " ";
					break;
				default:
					this.textoSalida += caracter + " " + cont + " ";
				}
			}
			Files.writeString(Paths.get(direccionSalida), this.textoSalida, StandardCharsets.UTF_16,
					StandardOpenOption.CREATE);
			this.tasaCompresion = (double) this.textoEntrada.length() / this.textoSalida.length();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo.");
		} catch (IOException e) {
			System.out.println("Hubo un problema con el Buffer.");
		}
	}

	public String getTextoEntrada() {
		return this.textoEntrada;
	}

	public String getTextoSalida() {
		return this.textoSalida;
	}

	public double getTasaCompresion() {
		return this.tasaCompresion;
	}

}
