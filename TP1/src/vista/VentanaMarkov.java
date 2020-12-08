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

import modeloParte1.FuenteDeMarkov;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VentanaMarkov extends JFrame implements ActionListener, KeyListener {
	private JPanel switchEstado;
	private JTextField fieldElementos1, fieldElementos2, fieldCantSimbolos;
	private JPanel panelMatriz;
	private JButton botonSiguienteTrans, botonSiguienteInicio, botonM1, botonM2;
	private JTextField[][] matrizTransicion;
	private JTextField[] vectorIndices, vectorIndicesFijo;
	private JTextArea textoEntropiaTeorica, textoEntropiaExperimental, textoVEstacionarioTeorico,
			textoVEstacionarioExperimental, textoTeorica, textoExperimental;
	private int cantSimbolos;
	private FuenteDeMarkov fuente;
	private boolean elementos2Correcto, columnasCorrectas[], matrizCorrecta;

	public VentanaMarkov() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.switchEstado = new JPanel();
		getContentPane().add(this.switchEstado);
		this.switchEstado.setToolTipText("");
		this.switchEstado.setLayout(new CardLayout(0, 0));

		JPanel cardInicio = new JPanel();
		switchEstado.add(cardInicio, "INICIO");
		cardInicio.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel marcoNueva = new JPanel();
		FlowLayout flowLayout = (FlowLayout) marcoNueva.getLayout();
		flowLayout.setVgap(100);
		marcoNueva.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardInicio.add(marcoNueva);

		JPanel nueva = new JPanel();
		nueva.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoNueva.add(nueva);
		nueva.setLayout(new BorderLayout(0, 25));

		JLabel labelNueva = new JLabel("Generar nueva matriz");
		labelNueva.setHorizontalAlignment(SwingConstants.CENTER);
		labelNueva.setFont(new Font("Tahoma", Font.BOLD, 24));
		nueva.add(labelNueva, BorderLayout.NORTH);

		JLabel labelCantSimb = new JLabel("Cantidad de s\u00EDmbolos (entre 2 y 7):");
		labelCantSimb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCantSimb.setHorizontalAlignment(SwingConstants.CENTER);
		nueva.add(labelCantSimb, BorderLayout.WEST);

		this.fieldCantSimbolos = new JTextField();
		fieldCantSimbolos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.fieldCantSimbolos.setColumns(10);
		this.fieldCantSimbolos.addKeyListener(this);
		this.fieldCantSimbolos.setName("CANTSIMBOLOS");
		nueva.add(this.fieldCantSimbolos, BorderLayout.EAST);

		this.botonSiguienteInicio = new JButton("Siguiente");
		botonSiguienteInicio.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.botonSiguienteInicio.addActionListener(this);
		this.botonSiguienteInicio.setEnabled(false);
		this.botonSiguienteInicio.setActionCommand("SIGINICIO");
		nueva.add(this.botonSiguienteInicio, BorderLayout.SOUTH);

		JPanel marcoAnexo = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) marcoAnexo.getLayout();
		flowLayout_1.setVgap(95);
		marcoAnexo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardInicio.add(marcoAnexo);

		JPanel anexo = new JPanel();
		anexo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		marcoAnexo.add(anexo);
		anexo.setLayout(new BorderLayout(0, 25));

		this.botonM1 = new JButton("Anexo 2 - Matriz 1");
		botonM1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonM1.setEnabled(false);
		this.botonM1.addActionListener(this);
		this.botonM1.setActionCommand("MATRIZ1");
		anexo.add(this.botonM1, BorderLayout.WEST);

		this.botonM2 = new JButton("Anexo 2 - Matriz 2");
		botonM2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonM2.addActionListener(this);
		this.botonM2.setEnabled(false);
		this.botonM2.setActionCommand("MATRIZ2");
		anexo.add(this.botonM2, BorderLayout.EAST);

		JPanel panelSecuencia = new JPanel();
		anexo.add(panelSecuencia, BorderLayout.SOUTH);

		JLabel labelSecuencia1 = new JLabel("Cantidad de elementos a generar:");
		labelSecuencia1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSecuencia.add(labelSecuencia1);

		this.fieldElementos1 = new JTextField();
		this.fieldElementos1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.fieldElementos1.setColumns(10);
		this.fieldElementos1.addKeyListener(this);
		this.fieldElementos1.setName("ELEMENTOS1");
		panelSecuencia.add(this.fieldElementos1);

		JLabel labelAnexo = new JLabel("Generar matriz predefinida");
		labelAnexo.setHorizontalAlignment(SwingConstants.CENTER);
		labelAnexo.setFont(new Font("Tahoma", Font.BOLD, 24));
		anexo.add(labelAnexo, BorderLayout.NORTH);

		JPanel cardTransicion = new JPanel();
		switchEstado.add(cardTransicion, "TRANSICION");
		cardTransicion.setLayout(new BorderLayout(0, 0));

		JLabel labelNOTA = new JLabel(
				"NOTA: Todas las columnas de la matriz deben sumar 1. Los decimales se separan con un punto.");
		labelNOTA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardTransicion.add(labelNOTA, BorderLayout.NORTH);

		JPanel panelSigTrans = new JPanel();
		cardTransicion.add(panelSigTrans, BorderLayout.SOUTH);
		panelSigTrans.setLayout(new BorderLayout(0, 0));

		this.botonSiguienteTrans = new JButton("Siguiente");
		botonSiguienteTrans.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonSiguienteTrans.setActionCommand("SIGTRANS");
		this.botonSiguienteTrans.addActionListener(this);
		this.botonSiguienteTrans.setEnabled(false);
		panelSigTrans.add(this.botonSiguienteTrans, BorderLayout.EAST);

		JLabel labelSecuencia2 = new JLabel("Cantidad de elementos a generar:");
		labelSecuencia2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSigTrans.add(labelSecuencia2, BorderLayout.WEST);

		this.fieldElementos2 = new JTextField();
		this.fieldElementos2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.fieldElementos1.setColumns(10);
		this.fieldElementos2.addKeyListener(this);
		this.fieldElementos2.setName("ELEMENTOS2");
		panelSigTrans.add(this.fieldElementos2, BorderLayout.CENTER);
		this.fieldElementos2.setColumns(10);

		this.panelMatriz = new JPanel();
		this.panelMatriz.setVisible(false);
		cardTransicion.add(this.panelMatriz, BorderLayout.CENTER);

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

		JPanel panelResultadosTeoricos = new JPanel();
		panelTeorico.add(panelResultadosTeoricos, BorderLayout.SOUTH);
		GridBagLayout gbl_panelResultadosTeoricos = new GridBagLayout();
		gbl_panelResultadosTeoricos.columnWidths = new int[] {125, 377, 0};
		gbl_panelResultadosTeoricos.rowHeights = new int[]{22, 22, 0};
		gbl_panelResultadosTeoricos.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelResultadosTeoricos.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelResultadosTeoricos.setLayout(gbl_panelResultadosTeoricos);
				
						JLabel labelVEstacionarioTeorico = new JLabel("Vector estacionario:");
						labelVEstacionarioTeorico.setHorizontalAlignment(SwingConstants.TRAILING);
						GridBagConstraints gbc_labelVEstacionarioTeorico = new GridBagConstraints();
						gbc_labelVEstacionarioTeorico.fill = GridBagConstraints.BOTH;
						gbc_labelVEstacionarioTeorico.insets = new Insets(0, 0, 5, 5);
						gbc_labelVEstacionarioTeorico.gridx = 0;
						gbc_labelVEstacionarioTeorico.gridy = 0;
						panelResultadosTeoricos.add(labelVEstacionarioTeorico, gbc_labelVEstacionarioTeorico);
		
				this.textoVEstacionarioTeorico = new JTextArea();
				this.textoVEstacionarioTeorico.setEditable(false);
				GridBagConstraints gbc_textoVEstacionarioTeorico = new GridBagConstraints();
				gbc_textoVEstacionarioTeorico.fill = GridBagConstraints.BOTH;
				gbc_textoVEstacionarioTeorico.insets = new Insets(0, 0, 5, 0);
				gbc_textoVEstacionarioTeorico.gridx = 1;
				gbc_textoVEstacionarioTeorico.gridy = 0;
				panelResultadosTeoricos.add(this.textoVEstacionarioTeorico, gbc_textoVEstacionarioTeorico);
				
						JLabel labelEntropiaTeorica = new JLabel("Entrop\u00EDa:");
						labelEntropiaTeorica.setHorizontalAlignment(SwingConstants.TRAILING);
						GridBagConstraints gbc_labelEntropiaTeorica = new GridBagConstraints();
						gbc_labelEntropiaTeorica.fill = GridBagConstraints.BOTH;
						gbc_labelEntropiaTeorica.insets = new Insets(0, 0, 0, 5);
						gbc_labelEntropiaTeorica.gridx = 0;
						gbc_labelEntropiaTeorica.gridy = 1;
						panelResultadosTeoricos.add(labelEntropiaTeorica, gbc_labelEntropiaTeorica);
		
				this.textoEntropiaTeorica = new JTextArea();
				this.textoEntropiaTeorica.setEditable(false);
				GridBagConstraints gbc_textoEntropiaTeorica = new GridBagConstraints();
				gbc_textoEntropiaTeorica.fill = GridBagConstraints.BOTH;
				gbc_textoEntropiaTeorica.gridx = 1;
				gbc_textoEntropiaTeorica.gridy = 1;
				panelResultadosTeoricos.add(this.textoEntropiaTeorica, gbc_textoEntropiaTeorica);

		JPanel panelMatrizTeorica = new JPanel();
		panelTeorico.add(panelMatrizTeorica, BorderLayout.CENTER);
		panelMatrizTeorica.setLayout(new BorderLayout(0, 0));

		JLabel labelTransicionTeorica = new JLabel("Matriz de transici\u00F3n:");
		panelMatrizTeorica.add(labelTransicionTeorica, BorderLayout.NORTH);

		this.textoTeorica = new JTextArea();
		this.textoTeorica.setEditable(false);
		panelMatrizTeorica.add(this.textoTeorica, BorderLayout.CENTER);

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

		JPanel panelResultadosExperimentales = new JPanel();
		panelExperimental.add(panelResultadosExperimentales, BorderLayout.SOUTH);
		GridBagLayout gbl_panelResultadosExperimentales = new GridBagLayout();
		gbl_panelResultadosExperimentales.columnWidths = new int[] {125, 377, 0};
		gbl_panelResultadosExperimentales.rowHeights = new int[]{22, 22, 0};
		gbl_panelResultadosExperimentales.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelResultadosExperimentales.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelResultadosExperimentales.setLayout(gbl_panelResultadosExperimentales);
				
						JLabel labelVEstacionarioExperimental = new JLabel("Vector estacionario:");
						labelVEstacionarioExperimental.setHorizontalAlignment(SwingConstants.TRAILING);
						GridBagConstraints gbc_labelVEstacionarioExperimental = new GridBagConstraints();
						gbc_labelVEstacionarioExperimental.fill = GridBagConstraints.BOTH;
						gbc_labelVEstacionarioExperimental.insets = new Insets(0, 0, 5, 5);
						gbc_labelVEstacionarioExperimental.gridx = 0;
						gbc_labelVEstacionarioExperimental.gridy = 0;
						panelResultadosExperimentales.add(labelVEstacionarioExperimental, gbc_labelVEstacionarioExperimental);
		
				this.textoVEstacionarioExperimental = new JTextArea();
				this.textoVEstacionarioExperimental.setEditable(false);
				GridBagConstraints gbc_textoVEstacionarioExperimental = new GridBagConstraints();
				gbc_textoVEstacionarioExperimental.fill = GridBagConstraints.BOTH;
				gbc_textoVEstacionarioExperimental.insets = new Insets(0, 0, 5, 0);
				gbc_textoVEstacionarioExperimental.gridx = 1;
				gbc_textoVEstacionarioExperimental.gridy = 0;
				panelResultadosExperimentales.add(this.textoVEstacionarioExperimental, gbc_textoVEstacionarioExperimental);
		
				JLabel labelEntropiaExperimental = new JLabel("Entrop\u00EDa:");
				labelEntropiaExperimental.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_labelEntropiaExperimental = new GridBagConstraints();
				gbc_labelEntropiaExperimental.fill = GridBagConstraints.BOTH;
				gbc_labelEntropiaExperimental.insets = new Insets(0, 0, 0, 5);
				gbc_labelEntropiaExperimental.gridx = 0;
				gbc_labelEntropiaExperimental.gridy = 1;
				panelResultadosExperimentales.add(labelEntropiaExperimental, gbc_labelEntropiaExperimental);
		
				this.textoEntropiaExperimental = new JTextArea();
				this.textoEntropiaExperimental.setEditable(false);
				GridBagConstraints gbc_textoEntropiaExperimental = new GridBagConstraints();
				gbc_textoEntropiaExperimental.fill = GridBagConstraints.BOTH;
				gbc_textoEntropiaExperimental.gridx = 1;
				gbc_textoEntropiaExperimental.gridy = 1;
				panelResultadosExperimentales.add(this.textoEntropiaExperimental, gbc_textoEntropiaExperimental);

		JPanel panelMatrizExperimental = new JPanel();
		panelExperimental.add(panelMatrizExperimental, BorderLayout.CENTER);
		panelMatrizExperimental.setLayout(new BorderLayout(0, 0));

		JLabel labelTransicionExperimental = new JLabel("Matriz de transici\u00F3n:");
		panelMatrizExperimental.add(labelTransicionExperimental, BorderLayout.NORTH);

		this.textoExperimental = new JTextArea();
		this.textoExperimental.setEditable(false);
		panelMatrizExperimental.add(this.textoExperimental, BorderLayout.CENTER);

		this.matrizCorrecta = false;
		this.elementos2Correcto = false;
		this.setMinimumSize(new Dimension(1024, 500));
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton boton = (JButton) arg0.getSource();
		CardLayout layout = (CardLayout) this.switchEstado.getLayout();
		if (boton.getActionCommand().equals("SIGINICIO")) {
			// Pasa a la pantalla para generar una matriz de transici�n
			this.cantSimbolos = Integer.parseInt(this.fieldCantSimbolos.getText());
			this.inicializarPanelTransicion();
			layout.show(this.switchEstado, "TRANSICION");

		} else if (boton.getActionCommand().equals("SIGTRANS")) {
			// Pasa a la pantalla de resultados desde una matriz de transici�n ingresada por
			// el usuario
			int i, j;
			double transicion[][] = new double[this.cantSimbolos][this.cantSimbolos];
			String indice[] = new String[this.cantSimbolos];
			for (i = 0; i < this.cantSimbolos; i++) {
				indice[i] = this.vectorIndices[i].getText();
				for (j = 0; j < this.cantSimbolos; j++) {
					transicion[i][j] = Double.parseDouble(this.matrizTransicion[i][j].getText());
				}
			}
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos2.getText()), indice, transicion);
			layout.show(this.switchEstado, "RESULTADOS");

		} else if (boton.getActionCommand().equals("MATRIZ1")) {
			// Pasa a la pantalla de resultados correspondiente a la Matriz 1 del Anexo 2
			String indice[] = { "a", "b", "c", "d" };
			double transicion[][] = { { 0.299, 0.259, 0.0729, 0.0647 }, { 0.00225, 0.0226, 0.621, 0.59 },
					{ 0.622, 0.524, 0.0251, 0.0756 }, { 0.07675, 0.1944, 0.281, 0.2697 } };
			this.cantSimbolos = 4;
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos1.getText()), indice, transicion);
			layout.show(this.switchEstado, "RESULTADOS");

		} else if (boton.getActionCommand().equals("MATRIZ2")) {
			// Pasa a la pantalla de resultados correspondiente a la Matriz 2 del Anexo 2
			String indice[] = { "a", "b", "c", "d", "e" };
			double transicion[][] = { { 0.45, 0.178, 0.128, 0.37, 0.0898 }, { 0.221, 0.426, 0.401, 0.46, 0.0787 },
					{ 0.03, 0.127, 0.0668, 0.0486, 0.565 }, { 0.238, 0.155, 0.388, 0.0624, 0.062 },
					{ 0.061, 0.114, 0.0162, 0.059, 0.2045 } };
			this.cantSimbolos = 5;
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos1.getText()), indice, transicion);
			layout.show(this.switchEstado, "RESULTADOS");
		}
	}

	// Genera la fuente con los datos ingresado y realiza todos los c�lculos
	// necesarios
	public void generarDatosFuente(int n, String indice[], double transicion[][]) {
		this.fuente = new FuenteDeMarkov(indice, transicion, this.cantSimbolos);
		this.fuente.generarVEstacionario();
		this.fuente.generarSecuencia(n);
		this.fuente.recrearMatriz();
		this.fuente.generarVEstacionarioExperimental();

		this.textoTeorica.setText(this.fuente.getMatriz());
		this.textoVEstacionarioTeorico.setText(this.fuente.getVEstacionario());
		this.textoEntropiaTeorica.setText(this.fuente.getEntropia() + "");

		this.textoExperimental.setText(this.fuente.getMatrizExperimental());
		this.textoVEstacionarioExperimental.setText(this.fuente.getVEstacionarioExperimental());
		this.textoEntropiaExperimental.setText(this.fuente.getEntropiaExperimental() + "");
	}

	public void inicializarPanelTransicion() {
		int i, j;

		// Crea un layout en base a la cantidad de s�mbolos
		GridLayout layout = new GridLayout(this.cantSimbolos + 1, this.cantSimbolos + 1);
		layout.setVgap(100 / this.cantSimbolos);
		layout.setHgap(100 / this.cantSimbolos);
		this.panelMatriz.setLayout(layout);

		// Crea dos vectores de campos para mostrar como fila y columna de s�mbolos
		this.vectorIndicesFijo = new JTextField[this.cantSimbolos];
		this.vectorIndices = new JTextField[this.cantSimbolos];
		for (i = 0; i < this.cantSimbolos; i++) {
			this.vectorIndicesFijo[i] = new JTextField();
			this.vectorIndicesFijo[i].setEditable(false);
			this.vectorIndicesFijo[i].setHorizontalAlignment(JTextField.CENTER);
			this.vectorIndices[i] = new JTextField();
			this.vectorIndices[i].addKeyListener(this);
			this.vectorIndices[i].setBackground(Color.lightGray);
			this.vectorIndices[i].setHorizontalAlignment(JTextField.CENTER);
			this.vectorIndices[i].setName("" + i);
		}

		// Crea la matriz de transici�n a llenar por el usuario
		this.matrizTransicion = new JTextField[this.cantSimbolos][this.cantSimbolos];
		this.panelMatriz.add(new JLabel("S�mbolo:"));
		this.columnasCorrectas = new boolean[this.cantSimbolos];
		for (i = 0; i < this.cantSimbolos; i++)
			this.panelMatriz.add(this.vectorIndicesFijo[i]);
		for (i = 0; i < this.cantSimbolos; i++) {
			this.panelMatriz.add(vectorIndices[i]);
			this.columnasCorrectas[i] = false;
			for (j = 0; j < this.cantSimbolos; j++) {
				this.matrizTransicion[i][j] = new JTextField();
				this.matrizTransicion[i][j].addKeyListener(this);
				this.matrizTransicion[i][j].setHorizontalAlignment(JTextField.CENTER);
				this.matrizTransicion[i][j].setName(i + "," + j);
				this.panelMatriz.add(this.matrizTransicion[i][j]);
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
		if (field.getName().equals("CANTSIMBOLOS")) {
			// Valida la cantidad de s�mbolos ingresada en la primera pantalla
			String textoCantSimbolos = this.fieldCantSimbolos.getText();
			if (!textoCantSimbolos.isBlank() && textoCantSimbolos.matches("[0-9]+")
					&& Integer.parseInt(textoCantSimbolos) > 1 && Integer.parseInt(textoCantSimbolos) < 8)
				this.botonSiguienteInicio.setEnabled(true);
			else
				this.botonSiguienteInicio.setEnabled(false);
		} else if (field.getName().equals("ELEMENTOS1")) {
			// Valida la cantidad de elementos a generar ingresada en la primera pantalla
			String textoElementos1 = field.getText();
			if (!textoElementos1.isBlank() && textoElementos1.matches("[0-9]+")
					&& Integer.parseInt(textoElementos1) > 0) {
				this.botonM1.setEnabled(true);
				this.botonM2.setEnabled(true);
			} else {
				this.botonM1.setEnabled(false);
				this.botonM2.setEnabled(false);
			}
		} else {
			// Valida los campos de la segunda pantalla (para matrices nuevas)
			int i, j;
			double suma;
			if (field.getName().equals("ELEMENTOS2")) {
				// Valida la cantidad de elementos a generar ingresada en la segunda pantalla
				// (para matrices nuevas)
				String textoElementos2 = field.getText();
				if (!textoElementos2.isBlank() && textoElementos2.matches("[0-9]+")
						&& Integer.parseInt(textoElementos2) > 0)
					this.elementos2Correcto = true;
				else
					this.elementos2Correcto = false;
			} else if (field.getName().contains(",")) {
				// Valida los datos ingresados en la matriz
				String textoField = field.getText();
				int columna = Integer.parseInt(field.getName().split(",")[1]);
				if (textoField.isBlank() || !textoField.matches("[0-9,.]+") || Double.parseDouble(textoField) > 1
						|| Double.parseDouble(textoField) < 0) {
					this.columnasCorrectas[columna] = false;
					this.matrizCorrecta = false;
				} else {
					// Chequea si la suma de la columna da 1 y si es as� chequea si la matriz pasa a
					// ser correcta
					String elemento;
					suma = 0;
					i = 0;
					this.columnasCorrectas[columna] = true;
					while (i < this.cantSimbolos && this.columnasCorrectas[columna]) {
						elemento = this.matrizTransicion[i][columna].getText();
						if (!elemento.isBlank() && elemento.matches("[0-9,.]+") && Double.parseDouble(elemento) <= 1
								&& Double.parseDouble(elemento) >= 0)
							suma += Double.parseDouble(elemento);
						else {
							this.columnasCorrectas[columna] = false;
							this.matrizCorrecta = false;
						}
						i++;
					}
					if (i == this.cantSimbolos)
						if (suma != 1) {
							this.columnasCorrectas[columna] = false;
							this.matrizCorrecta = false;
						} else {
							this.matrizCorrecta = true;
							i = 0;
							while (i < this.cantSimbolos && this.columnasCorrectas[i])
								i++;
							if (i != this.cantSimbolos)
								this.matrizCorrecta = false;
						}
				}
			} else {
				// Copia el elemento del vector de indices ingresados al fijo
				int indice = Integer.parseInt(field.getName());
				this.vectorIndicesFijo[indice].setText(this.vectorIndices[indice].getText());
			}

			this.botonSiguienteTrans.setEnabled(this.elementos2Correcto && this.matrizCorrecta);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}

