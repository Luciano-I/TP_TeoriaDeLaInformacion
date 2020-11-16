package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import modeloParte1.FuenteDeMemoriaNula;

import modeloParte1.FuenteDeMemoriaNula;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VentanaMemoriaNula extends JFrame implements ActionListener, KeyListener {
	private JPanel switchEstado;
	private JTextField fieldElementos1, fieldElementos2, fieldCantSimbolos;
	private JPanel panelFuente;
	private JButton botonSiguienteFuente, botonSiguienteInicio, botonF1, botonF2, botonF3;
	private JTextField[][] matrizFuente;
	private JTextArea textoEntropiaTeorica, textoEntropiaExperimental, textoCantInfoTeorica,
			textoCantInformacionExperimental, textoTeorica, textoExperimental;
	private int cantSimbolos;
	private FuenteDeMemoriaNula fuente;
	private boolean elementos2Correcto, fuenteCorrecta;

	public VentanaMemoriaNula() {
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

		JLabel labelNueva = new JLabel("Generar nueva fuente");
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

		this.botonF1 = new JButton("Anexo 1 - Fuente 1");
		botonF1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonF1.setEnabled(false);
		this.botonF1.addActionListener(this);
		this.botonF1.setActionCommand("FUENTE1");
		anexo.add(this.botonF1, BorderLayout.WEST);

		this.botonF2 = new JButton("Anexo 1 - Fuente 2");
		botonF2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonF2.setEnabled(false);
		this.botonF2.addActionListener(this);
		this.botonF2.setActionCommand("FUENTE2");
		anexo.add(this.botonF2, BorderLayout.CENTER);

		this.botonF3 = new JButton("Anexo 1 - Fuente 3");
		this.botonF3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonF3.setEnabled(false);
		this.botonF3.addActionListener(this);
		this.botonF3.setActionCommand("FUENTE3");
		anexo.add(this.botonF3, BorderLayout.EAST);

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

		JLabel labelAnexo = new JLabel("Generar fuente predefinida");
		labelAnexo.setHorizontalAlignment(SwingConstants.CENTER);
		labelAnexo.setFont(new Font("Tahoma", Font.BOLD, 24));
		anexo.add(labelAnexo, BorderLayout.NORTH);

		JPanel cardFuente = new JPanel();
		switchEstado.add(cardFuente, "FUENTE");
		cardFuente.setLayout(new BorderLayout(0, 0));

		JLabel labelNOTA = new JLabel(
				"NOTA: Todas las probabilidades del vector deben sumar 1. Los decimales se separan con un punto.");
		labelNOTA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardFuente.add(labelNOTA, BorderLayout.NORTH);

		JPanel panelSigFuente = new JPanel();
		cardFuente.add(panelSigFuente, BorderLayout.SOUTH);
		panelSigFuente.setLayout(new BorderLayout(0, 0));

		this.botonSiguienteFuente = new JButton("Siguiente");
		botonSiguienteFuente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonSiguienteFuente.setActionCommand("SIGFUENTE");
		this.botonSiguienteFuente.addActionListener(this);
		this.botonSiguienteFuente.setEnabled(false);
		panelSigFuente.add(this.botonSiguienteFuente, BorderLayout.EAST);

		JLabel labelSecuencia2 = new JLabel("Cantidad de elementos a generar:");
		labelSecuencia2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSigFuente.add(labelSecuencia2, BorderLayout.WEST);

		this.fieldElementos2 = new JTextField();
		this.fieldElementos2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.fieldElementos1.setColumns(10);
		this.fieldElementos2.addKeyListener(this);
		this.fieldElementos2.setName("ELEMENTOS2");
		panelSigFuente.add(this.fieldElementos2, BorderLayout.CENTER);
		this.fieldElementos2.setColumns(10);

		this.panelFuente = new JPanel();
		this.panelFuente.setVisible(false);
		cardFuente.add(this.panelFuente, BorderLayout.CENTER);

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
		gbl_panelResultadosTeoricos.columnWidths = new int[] {150, 352, 0};
		gbl_panelResultadosTeoricos.rowHeights = new int[] { 22, 22, 0 };
		gbl_panelResultadosTeoricos.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelResultadosTeoricos.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelResultadosTeoricos.setLayout(gbl_panelResultadosTeoricos);

		JLabel labelCantInformacionTeorico = new JLabel("Cantidad de informaci\u00F3n:");
		labelCantInformacionTeorico.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelCantInformacionTeorico = new GridBagConstraints();
		gbc_labelCantInformacionTeorico.fill = GridBagConstraints.BOTH;
		gbc_labelCantInformacionTeorico.insets = new Insets(0, 0, 5, 5);
		gbc_labelCantInformacionTeorico.gridx = 0;
		gbc_labelCantInformacionTeorico.gridy = 0;
		panelResultadosTeoricos.add(labelCantInformacionTeorico, gbc_labelCantInformacionTeorico);

		this.textoCantInfoTeorica = new JTextArea();
		this.textoCantInfoTeorica.setEditable(false);
		GridBagConstraints gbc_textoCantInfoTeorica = new GridBagConstraints();
		gbc_textoCantInfoTeorica.fill = GridBagConstraints.BOTH;
		gbc_textoCantInfoTeorica.insets = new Insets(0, 0, 5, 0);
		gbc_textoCantInfoTeorica.gridx = 1;
		gbc_textoCantInfoTeorica.gridy = 0;
		panelResultadosTeoricos.add(this.textoCantInfoTeorica, gbc_textoCantInfoTeorica);

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

		JPanel panelFuenteTeorica = new JPanel();
		panelTeorico.add(panelFuenteTeorica, BorderLayout.CENTER);
		panelFuenteTeorica.setLayout(new BorderLayout(0, 0));

		JLabel labelFuenteTeorica = new JLabel("Fuente:");
		panelFuenteTeorica.add(labelFuenteTeorica, BorderLayout.NORTH);

		this.textoTeorica = new JTextArea();
		this.textoTeorica.setEditable(false);
		panelFuenteTeorica.add(this.textoTeorica, BorderLayout.CENTER);

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
		gbl_panelResultadosExperimentales.columnWidths = new int[] {150, 352, 0};
		gbl_panelResultadosExperimentales.rowHeights = new int[] { 22, 22, 0 };
		gbl_panelResultadosExperimentales.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelResultadosExperimentales.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelResultadosExperimentales.setLayout(gbl_panelResultadosExperimentales);

		JLabel labelCantInformacionExperimental = new JLabel("Cantidad de informaci\u00F3n:");
		labelCantInformacionExperimental.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelCantInformacionExperimental = new GridBagConstraints();
		gbc_labelCantInformacionExperimental.fill = GridBagConstraints.BOTH;
		gbc_labelCantInformacionExperimental.insets = new Insets(0, 0, 5, 5);
		gbc_labelCantInformacionExperimental.gridx = 0;
		gbc_labelCantInformacionExperimental.gridy = 0;
		panelResultadosExperimentales.add(labelCantInformacionExperimental, gbc_labelCantInformacionExperimental);

		this.textoCantInformacionExperimental = new JTextArea();
		this.textoCantInformacionExperimental.setEditable(false);
		GridBagConstraints gbc_textoCantInformacionExperimental = new GridBagConstraints();
		gbc_textoCantInformacionExperimental.fill = GridBagConstraints.BOTH;
		gbc_textoCantInformacionExperimental.insets = new Insets(0, 0, 5, 0);
		gbc_textoCantInformacionExperimental.gridx = 1;
		gbc_textoCantInformacionExperimental.gridy = 0;
		panelResultadosExperimentales.add(this.textoCantInformacionExperimental, gbc_textoCantInformacionExperimental);

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

		JLabel labelFuenteExperimental = new JLabel("Fuente:");
		panelMatrizExperimental.add(labelFuenteExperimental, BorderLayout.NORTH);

		this.textoExperimental = new JTextArea();
		this.textoExperimental.setEditable(false);
		panelMatrizExperimental.add(this.textoExperimental, BorderLayout.CENTER);

		this.fuenteCorrecta = false;
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
			// Pasa a la pantalla para generar una fuente
			this.cantSimbolos = Integer.parseInt(this.fieldCantSimbolos.getText());
			this.inicializarPanelFuente();
			layout.show(this.switchEstado, "FUENTE");

		} else if (boton.getActionCommand().equals("SIGFUENTE")) {
			// Pasa a la pantalla de resultados desde una fuente ingresada por
			// el usuario
			int i, j;
			String tabla[][] = new String[this.cantSimbolos][2];
			for (i = 0; i < this.cantSimbolos; i++) {
				tabla[i][0] = this.matrizFuente[i][0].getText();
				tabla[i][1] = this.matrizFuente[i][1].getText();
			}
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos2.getText()), tabla);
			layout.show(this.switchEstado, "RESULTADOS");

		} else if (boton.getActionCommand().equals("FUENTE1")) {
			// Pasa a la pantalla de resultados correspondiente a la Fuente 1 del Anexo 2
			String tabla[][] = { { "a", "0.707" }, { "b", "0.0727" }, { "c", "0.144" }, { "d", "0.0763" } };
			this.cantSimbolos = 4;
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos1.getText()), tabla);
			layout.show(this.switchEstado, "RESULTADOS");

		} else if (boton.getActionCommand().equals("FUENTE2")) {
			// Pasa a la pantalla de resultados correspondiente a la Fuente 1 del Anexo 2
			String tabla[][] = { { "a", "0.471" }, { "b", "0.077" }, { "c", "0.2" }, { "d", "0.0207" },
					{ "e", "0.2313" } };
			this.cantSimbolos = 5;
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos1.getText()), tabla);
			layout.show(this.switchEstado, "RESULTADOS");
		} else if (boton.getActionCommand().equals("FUENTE3")) {
			// Pasa a la pantalla de resultados correspondiente a la Fuente 1 del Anexo 2
			String tabla[][] = { { "a", "0.029" }, { "b", "0.0689" }, { "c", "0.00936" }, { "d", "0.693" },
					{ "e", "0.0122" }, { "f", "0.184" }, { "g", "0.00354" } };
			this.cantSimbolos = 7;
			this.generarDatosFuente(Integer.parseInt(this.fieldElementos1.getText()), tabla);
			layout.show(this.switchEstado, "RESULTADOS");
		}
	}

	// Genera la fuente con los datos ingresado y realiza todos los cálculos
	// necesarios
	public void generarDatosFuente(int n, String tabla[][]) {
		this.fuente = new FuenteDeMemoriaNula(tabla, this.cantSimbolos);
		this.fuente.generarSecuencia(n);
		this.fuente.generarCantInfo();
		this.fuente.generarCantInfoExperimental();

		this.textoTeorica.setText(this.fuente.getFuente());
		this.textoCantInfoTeorica.setText(this.fuente.getCantInfo());
		this.textoEntropiaTeorica.setText(this.fuente.getEntropia() + "");

		this.textoExperimental.setText(this.fuente.getFuenteExperimental());
		this.textoCantInformacionExperimental.setText(this.fuente.getCantInfoExperimental());
		this.textoEntropiaExperimental.setText(this.fuente.getEntropiaExperimental() + "");
	}

	public void inicializarPanelFuente() {
		int i, j;

		// Crea un layout en base a la cantidad de símbolos
		GridLayout layout = new GridLayout(this.cantSimbolos + 1, 2);
		layout.setVgap(100 / this.cantSimbolos);
		layout.setHgap(50);
		this.panelFuente.setLayout(layout);

		// Crea la fuente a llenar por el usuario
		this.matrizFuente = new JTextField[this.cantSimbolos + 1][2];
		this.panelFuente.add(new JLabel("Símbolo:"));
		this.panelFuente.add(new JLabel("Probabilidad:"));
		for (i = 0; i < this.cantSimbolos; i++) {
			this.matrizFuente[i][0] = new JTextField();
			this.matrizFuente[i][0].addKeyListener(this);
			this.matrizFuente[i][0].setHorizontalAlignment(JTextField.CENTER);
			this.matrizFuente[i][0].setName("SIMBOLO");
			this.panelFuente.add(this.matrizFuente[i][0]);
			this.matrizFuente[i][1] = new JTextField();
			this.matrizFuente[i][1].addKeyListener(this);
			this.matrizFuente[i][1].setHorizontalAlignment(JTextField.CENTER);
			this.matrizFuente[i][1].setName("PROBABILIDAD");
			this.panelFuente.add(this.matrizFuente[i][1]);
		}

		this.panelFuente.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		JTextField field = (JTextField) arg0.getSource();
		if (field.getName().equals("CANTSIMBOLOS")) {
			// Valida la cantidad de símbolos ingresada en la primera pantalla
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
				this.botonF1.setEnabled(true);
				this.botonF2.setEnabled(true);
				this.botonF3.setEnabled(true);
			} else {
				this.botonF1.setEnabled(false);
				this.botonF2.setEnabled(false);
				this.botonF3.setEnabled(false);
			}
		} else {
			// Valida los campos de la segunda pantalla (para fuentes nuevas)
			int i, j;
			double suma;
			if (field.getName().equals("ELEMENTOS2")) {
				// Valida la cantidad de elementos a generar ingresada en la segunda pantalla
				// (para fuentes nuevas)
				String textoElementos2 = field.getText();
				if (!textoElementos2.isBlank() && textoElementos2.matches("[0-9]+")
						&& Integer.parseInt(textoElementos2) > 0)
					this.elementos2Correcto = true;
				else
					this.elementos2Correcto = false;
			} else {
				// Valida los datos ingresados en la fuente
				String textoField = field.getText();
				if (textoField.isBlank() || !textoField.matches("[0-9,.]+") || Double.parseDouble(textoField) > 1
						|| Double.parseDouble(textoField) < 0) {
					this.fuenteCorrecta = false;
				} else {
					// Chequea si la columna de probabilidades es válida.
					String elemento;
					suma = 0;
					i = 0;
					this.fuenteCorrecta = true;
					while (i < this.cantSimbolos && this.fuenteCorrecta) {
						elemento = this.matrizFuente[i][1].getText();
						if (!elemento.isBlank() && elemento.matches("[0-9,.]+") && Double.parseDouble(elemento) <= 1
								&& Double.parseDouble(elemento) >= 0)
							suma += Double.parseDouble(elemento);
						else {
							this.fuenteCorrecta = false;
						}
						i++;
					}
					if (i == this.cantSimbolos && suma != 1)
						this.fuenteCorrecta = false;
				}
			}

			this.botonSiguienteFuente.setEnabled(this.elementos2Correcto && this.fuenteCorrecta);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
