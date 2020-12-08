package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modeloParte2.Fuente;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JScrollPane;

public class VentanaParte2 extends JFrame implements ActionListener, KeyListener {
	private JPanel switchEstado;
	private JPanel panelMatriz;
	private JButton botonSiguienteCanal, botonSiguienteInicio, botonC1, botonC3, botonC2;
	private JTextField[][] matrizCanal;
	private JTextField[] vectorPriori;
	private JTextArea textoTeorica, textoExperimental;
	private Fuente fuente;
	private boolean filasCorrectas[], matrizCorrecta, vectorCorrecto;
	private JTextField fieldCantEntrada;
	private JTextField fieldCantSalida;
	private JTextField fieldLongitud;
	private int cantEntrada, cantSalida, longitud;
	double tablaEntrada[] = {}, tablaCanal[][] = { {} };

	public VentanaParte2() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.switchEstado = new JPanel();
		getContentPane().add(this.switchEstado);
		this.switchEstado.setToolTipText("");
		this.switchEstado.setLayout(new CardLayout(0, 0));

		JPanel cardInicio = new JPanel();
		switchEstado.add(cardInicio, "INICIO");
		cardInicio.setLayout(new BorderLayout(0, 0));

		JPanel marcoNueva = new JPanel();
		marcoNueva.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardInicio.add(marcoNueva, BorderLayout.WEST);
		marcoNueva.setLayout(new FlowLayout(FlowLayout.CENTER, 42, 100));

		JPanel nueva = new JPanel();
		nueva.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoNueva.add(nueva);
		nueva.setLayout(new BorderLayout(0, 25));

		JLabel labelNueva = new JLabel("Generar nuevo canal");
		labelNueva.setHorizontalAlignment(SwingConstants.CENTER);
		labelNueva.setFont(new Font("Tahoma", Font.BOLD, 24));
		nueva.add(labelNueva, BorderLayout.NORTH);

		JPanel panelCantidades = new JPanel();
		panelCantidades.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		nueva.add(panelCantidades, BorderLayout.CENTER);
		panelCantidades.setLayout(new GridLayout(2, 2, 0, 0));

		JLabel labelCantEntrada = new JLabel("Cant. de símbolos de entrada:");
		labelCantEntrada.setHorizontalAlignment(SwingConstants.TRAILING);
		labelCantEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCantidades.add(labelCantEntrada);

		fieldCantEntrada = new JTextField();
		fieldCantEntrada.addKeyListener(this);
		fieldCantEntrada.setName("INICIO");
		fieldCantEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldCantEntrada.setColumns(10);
		panelCantidades.add(fieldCantEntrada);

		JLabel labelCantSalida = new JLabel("Cant. de símbolos de salida:");
		labelCantSalida.setHorizontalAlignment(SwingConstants.TRAILING);
		labelCantSalida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCantidades.add(labelCantSalida);

		fieldCantSalida = new JTextField();
		fieldCantSalida.addKeyListener(this);
		fieldCantSalida.setName("INICIO");
		fieldCantSalida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldCantSalida.setColumns(10);
		panelCantidades.add(fieldCantSalida);

		this.botonSiguienteInicio = new JButton("Siguiente");
		botonSiguienteInicio.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.botonSiguienteInicio.addActionListener(this);
		this.botonSiguienteInicio.setEnabled(false);
		this.botonSiguienteInicio.setActionCommand("SIGINICIO");
		nueva.add(this.botonSiguienteInicio, BorderLayout.SOUTH);

		JPanel marcoAnexo = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) marcoAnexo.getLayout();
		flowLayout_1.setHgap(42);
		flowLayout_1.setVgap(100);
		marcoAnexo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardInicio.add(marcoAnexo, BorderLayout.EAST);

		JPanel anexo = new JPanel();
		anexo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoAnexo.add(anexo);
		anexo.setLayout(new BorderLayout(0, 25));

		this.botonC1 = new JButton("Anexo 2 - Canal 1");
		this.botonC1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonC1.addActionListener(this);
		this.botonC1.setEnabled(false);
		this.botonC1.addActionListener(this);
		this.botonC1.setActionCommand("CANAL1");
		anexo.add(this.botonC1, BorderLayout.WEST);

		this.botonC3 = new JButton("Anexo 2 - Canal 3");
		this.botonC3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonC3.addActionListener(this);
		this.botonC3.setEnabled(false);
		this.botonC3.setActionCommand("CANAL3");
		anexo.add(this.botonC3, BorderLayout.EAST);

		this.botonC2 = new JButton("Anexo 2 - Canal 2");
		this.botonC2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonC2.addActionListener(this);
		this.botonC2.setEnabled(false);
		this.botonC2.setActionCommand("CANAL2");
		anexo.add(this.botonC2, BorderLayout.CENTER);

		JLabel labelAnexo = new JLabel("Generar canal predefinido");
		labelAnexo.setHorizontalAlignment(SwingConstants.CENTER);
		labelAnexo.setFont(new Font("Tahoma", Font.BOLD, 24));
		anexo.add(labelAnexo, BorderLayout.NORTH);

		JPanel cardCanal = new JPanel();
		switchEstado.add(cardCanal, "CANAL");
		cardCanal.setLayout(new BorderLayout(0, 0));

		JLabel labelNOTA = new JLabel(
				"NOTAS: Las prob. de los símbolos de entrada así como todas las filas de la matriz deben sumar 1. Los decimales se separan con un punto.");
		labelNOTA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardCanal.add(labelNOTA, BorderLayout.NORTH);

		JPanel panelSigCanal = new JPanel();
		cardCanal.add(panelSigCanal, BorderLayout.SOUTH);
		panelSigCanal.setLayout(new BorderLayout(0, 0));

		this.botonSiguienteCanal = new JButton("Siguiente");
		botonSiguienteCanal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonSiguienteCanal.setActionCommand("SIGCANAL");
		this.botonSiguienteCanal.addActionListener(this);
		this.botonSiguienteCanal.setEnabled(false);
		panelSigCanal.add(this.botonSiguienteCanal, BorderLayout.EAST);

		JPanel marcoLongitud = new JPanel();
		marcoLongitud.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardInicio.add(marcoLongitud, BorderLayout.NORTH);

		JPanel panelLongitud = new JPanel();
		panelLongitud.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoLongitud.add(panelLongitud);

		JLabel labelLongitud = new JLabel("Longitud del mensaje a generar:");
		labelLongitud.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelLongitud.add(labelLongitud);

		fieldLongitud = new JTextField();
		fieldLongitud.addKeyListener(this);
		fieldLongitud.setName("INICIO");
		fieldLongitud.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldLongitud.setColumns(10);
		panelLongitud.add(fieldLongitud);

		this.panelMatriz = new JPanel();
		this.panelMatriz.setVisible(false);
		cardCanal.add(this.panelMatriz, BorderLayout.CENTER);

		JPanel cardResultados = new JPanel();
		switchEstado.add(cardResultados, "RESULTADOS");
		cardResultados.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel marcoTeorico = new JPanel();
		marcoTeorico.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardResultados.add(marcoTeorico);
		marcoTeorico.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelTeorico = new JPanel();
		panelTeorico.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoTeorico.add(panelTeorico);
		panelTeorico.setLayout(new BorderLayout(0, 0));

		JLabel labelPanelTeorico = new JLabel("Resultados Te\u00F3ricos");
		labelPanelTeorico.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelPanelTeorico.setHorizontalAlignment(SwingConstants.CENTER);
		panelTeorico.add(labelPanelTeorico, BorderLayout.NORTH);

		JPanel panelMatrizTeorica = new JPanel();
		panelTeorico.add(panelMatrizTeorica, BorderLayout.CENTER);
		panelMatrizTeorica.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollTeorica = new JScrollPane();
		panelMatrizTeorica.add(scrollTeorica, BorderLayout.CENTER);

		this.textoTeorica = new JTextArea();
		this.textoTeorica.setEditable(false);
		scrollTeorica.setViewportView(this.textoTeorica);

		JPanel marcoExperimental = new JPanel();
		marcoExperimental.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardResultados.add(marcoExperimental);
		marcoExperimental.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelExperimental = new JPanel();
		panelExperimental.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoExperimental.add(panelExperimental);
		panelExperimental.setLayout(new BorderLayout(0, 0));

		JLabel labelPanelExperimental = new JLabel("Resultados Experimentales");
		labelPanelExperimental.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanelExperimental.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelExperimental.add(labelPanelExperimental, BorderLayout.NORTH);

		JPanel panelMatrizExperimental = new JPanel();
		panelExperimental.add(panelMatrizExperimental, BorderLayout.CENTER);
		panelMatrizExperimental.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollExperimental = new JScrollPane();
		panelMatrizExperimental.add(scrollExperimental, BorderLayout.CENTER);

		this.textoExperimental = new JTextArea();
		this.textoExperimental.setEditable(false);
		scrollExperimental.setViewportView(this.textoExperimental);

		this.vectorCorrecto = false;
		this.matrizCorrecta = false;
		this.setMinimumSize(new Dimension(1024, 500));
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton boton = (JButton) arg0.getSource();
		CardLayout layout = (CardLayout) this.switchEstado.getLayout();
		this.longitud = Integer.parseInt(this.fieldLongitud.getText());
		if (boton.getActionCommand().equals("SIGINICIO")) {
			// Pasa a la pantalla para generar un canal
			this.cantEntrada = Integer.parseInt(this.fieldCantEntrada.getText());
			this.cantSalida = Integer.parseInt(this.fieldCantSalida.getText());
			this.inicializarPanelCanal();
			layout.show(this.switchEstado, "CANAL");

		} else {
			if (boton.getActionCommand().equals("SIGCANAL")) {
				// Pasa a la pantalla de resultados desde un canal ingresado por
				// el usuario
				int i, j;
				this.tablaEntrada = new double[this.cantEntrada];
				this.tablaCanal = new double[this.cantEntrada][this.cantSalida];
				for (i = 0; i < this.cantEntrada; i++) {
					this.tablaEntrada[i] = Double.parseDouble(this.vectorPriori[i].getText());
					for (j = 0; j < this.cantSalida; j++) {
						this.tablaCanal[i][j] = Double.parseDouble(this.matrizCanal[i][j].getText());
					}
				}
			} else if (boton.getActionCommand().equals("CANAL1")) {
				// Pasa a la pantalla de resultados correspondiente al Canal 1 del Anexo 2
				double auxEntrada[] = { 0.4, 0.6 };
				this.tablaEntrada = auxEntrada;
				this.cantEntrada = 2;
				double auxCanal[][] = { { 0, 0, 0.3, 0.3, 0.4 }, { 0.5, 0.5, 0, 0, 0 } };
				this.tablaCanal = auxCanal;
				this.cantSalida = 5;
			} else if (boton.getActionCommand().equals("CANAL2")) {
				// Pasa a la pantalla de resultados correspondiente al Canal 2 del Anexo 2
				double auxEntrada[] = { 0.3, 0.3, 0.3, 0.05, 0.05 };
				this.tablaEntrada = auxEntrada;
				this.cantEntrada = 5;
				double auxCanal[][] = { { 0, 0, 0, 0.2, 0.8 }, { 0.5, 0.4, 0.1, 0, 0 }, { 0.1, 0.25, 0.25, 0.3, 0.1 },
						{ 0.2, 0.2, 0.2, 0.2, 0.2 }, { 0, 0, 1, 0, 0 } };
				this.tablaCanal = auxCanal;
				this.cantSalida = 5;
			} else if (boton.getActionCommand().equals("CANAL3")) {
				// Pasa a la pantalla de resultados correspondiente al Canal 3 del Anexo 2
				double auxEntrada[] = { 0.25, 0.25, 0.25, 0.25 };
				this.tablaEntrada = auxEntrada;
				this.cantEntrada = 4;
				double auxCanal[][] = { { 0.3, 0.5, 0.2 }, { 0.3, 0.5, 0.2 }, { 0.3, 0.5, 0.2 }, { 0, 1, 0 } };
				this.tablaCanal = auxCanal;
				this.cantSalida = 3;
			}
			this.generarDatosCanal();
			layout.show(this.switchEstado, "RESULTADOS");
		}
	}

	public void generarDatosCanal() {
		this.fuente = new Fuente(this.tablaEntrada, cantEntrada, this.tablaCanal, cantSalida);
		this.textoTeorica.setText(this.fuente.getResultadosTeoricos());
		this.textoTeorica.setCaretPosition(0);
		this.fuente.generarMensajeEntrada(this.longitud);
		this.fuente.enviarMensaje();
		this.fuente.recrearTablaCanal();
		this.textoExperimental.setText(this.fuente.getResultadosExperimentales());
		this.textoExperimental.setCaretPosition(0);
	}

	public void inicializarPanelCanal() {
		int i, j;
		JLabel[] vectorIndicesA, vectorIndicesB;
		JLabel pA = new JLabel();

		pA.setText("P(A)");
		pA.setHorizontalAlignment(JTextField.CENTER);

		// Crea un layout en base a la cantidad de s�mbolos
		GridLayout layout = new GridLayout(this.cantEntrada + 1, this.cantSalida + 2);
		layout.setVgap(100 / this.cantEntrada);
		layout.setHgap(100 / this.cantSalida);
		this.panelMatriz.setLayout(layout);

		// Crea dos vectores de campos para mostrar como fila y columna de símbolos, además de un vector de campos para probabilidades a priori
		vectorIndicesA = new JLabel[this.cantEntrada];
		vectorIndicesB = new JLabel[this.cantSalida];
		this.vectorPriori = new JTextField[this.cantEntrada];
		for (i = 0; i < this.cantEntrada; i++) {
			vectorIndicesA[i] = new JLabel();
			vectorIndicesA[i].setText("A" + (i + 1));
			vectorIndicesA[i].setHorizontalAlignment(JTextField.CENTER);
			this.vectorPriori[i] = new JTextField();
			this.vectorPriori[i].setHorizontalAlignment(JTextField.CENTER);
			this.vectorPriori[i].setBackground(Color.LIGHT_GRAY);
			this.vectorPriori[i].addKeyListener(this);
			this.vectorPriori[i].setName(i + "");
		}
		for (i = 0; i < this.cantSalida; i++) {
			vectorIndicesB[i] = new JLabel();
			vectorIndicesB[i].setText("B" + (i + 1));
			vectorIndicesB[i].setHorizontalAlignment(JTextField.CENTER);
		}

		// Crea la matriz a llenar llenar por el usuario
		this.matrizCanal = new JTextField[this.cantEntrada][this.cantSalida];
		this.panelMatriz.add(new JLabel());
		this.panelMatriz.add(pA);
		this.filasCorrectas = new boolean[this.cantEntrada];
		for (j = 0; j < this.cantSalida; j++)
			this.panelMatriz.add(vectorIndicesB[j]);
		for (i = 0; i < this.cantEntrada; i++) {
			this.panelMatriz.add(vectorIndicesA[i]);
			this.panelMatriz.add(this.vectorPriori[i]);
			this.filasCorrectas[i] = false;
			for (j = 0; j < this.cantSalida; j++) {
				this.matrizCanal[i][j] = new JTextField();
				this.matrizCanal[i][j].addKeyListener(this);
				this.matrizCanal[i][j].setHorizontalAlignment(JTextField.CENTER);
				this.matrizCanal[i][j].setName(i + "," + j);
				this.panelMatriz.add(this.matrizCanal[i][j]);
			}
		}

		this.panelMatriz.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		JTextField field = (JTextField) arg0.getSource();
		if (field.getName().equals("INICIO")) {
			// Valida los datos necesarios para activar los botones de la primera pantalla
			String textoCantEntrada = this.fieldCantEntrada.getText();
			String textoCantSalida = this.fieldCantSalida.getText();
			String textoLongitud = this.fieldLongitud.getText();
			boolean validoCantEntrada = !textoCantEntrada.isBlank() && textoCantEntrada.matches("[0-9]+")
					&& Integer.parseInt(textoCantEntrada) > 1 && Integer.parseInt(textoCantEntrada) < 8;
			boolean validoCantSalida = !textoCantSalida.isBlank() && textoCantSalida.matches("[0-9]+")
					&& Integer.parseInt(textoCantSalida) > 1 && Integer.parseInt(textoCantSalida) < 8;
			boolean validoLongitud = !textoLongitud.isBlank() && textoLongitud.matches("[0-9]+")
					&& Integer.parseInt(textoLongitud) > 1;
			if (validoLongitud) {
				this.botonC1.setEnabled(true);
				this.botonC2.setEnabled(true);
				this.botonC3.setEnabled(true);
				if (validoCantEntrada && validoCantSalida)
					this.botonSiguienteInicio.setEnabled(true);
				else
					this.botonSiguienteInicio.setEnabled(false);
			} else {
				this.botonC1.setEnabled(false);
				this.botonC2.setEnabled(false);
				this.botonC3.setEnabled(false);
				this.botonSiguienteInicio.setEnabled(false);
			}
		} else {
			// Valida los campos de la segunda pantalla (para canales nuevas)
			int i, j;
			double suma;
			String textoField = field.getText(), elemento;
			if (field.getName().contains(",")) {
				// Valida los datos ingresados en la matriz
				int fila = Integer.parseInt(field.getName().split(",")[0]);
				if (textoField.isBlank() || !textoField.matches("[0-9,.]+") || Double.parseDouble(textoField) > 1
						|| Double.parseDouble(textoField) < 0) {
					this.filasCorrectas[fila] = false;
					this.matrizCorrecta = false;
				} else {
					// Chequea si la suma de la fila da 1 y si es así chequea si la matriz pasa a
					// ser correcta
					suma = 0;
					j = 0;
					this.filasCorrectas[fila] = true;
					while (j < this.cantSalida && this.filasCorrectas[fila]) {
						elemento = this.matrizCanal[fila][j].getText();
						if (!elemento.isBlank() && elemento.matches("[0-9,.]+") && Double.parseDouble(elemento) <= 1
								&& Double.parseDouble(elemento) >= 0)
							suma += Double.parseDouble(elemento);
						else {
							this.filasCorrectas[fila] = false;
							this.matrizCorrecta = false;
						}
						j++;
					}
					if (j == this.cantSalida)
						if (suma != 1) {
							this.filasCorrectas[fila] = false;
							this.matrizCorrecta = false;
						} else {
							this.matrizCorrecta = true;
							i = 0;
							while (i < this.cantEntrada && this.filasCorrectas[i])
								i++;
							if (i != this.cantEntrada)
								this.matrizCorrecta = false;
						}
				}
			} else {
				// Valida los datos ingresados en el vector de probabilidades a priori
				i = 0;
				suma = 0;
				this.vectorCorrecto = true;
				while (i < this.cantEntrada && this.vectorCorrecto)
				{
					elemento = this.vectorPriori[i].getText();
					if (!elemento.isBlank() && elemento.matches("[0-9,.]+") && Double.parseDouble(elemento) <= 1
							&& Double.parseDouble(elemento) >= 0)
						suma += Double.parseDouble(elemento);
					else
						this.vectorCorrecto = false;
					i++;
				}
				if (this.vectorCorrecto && suma != 1)
					this.vectorCorrecto = false;
			}
			this.botonSiguienteCanal.setEnabled(this.vectorCorrecto && this.matrizCorrecta);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
