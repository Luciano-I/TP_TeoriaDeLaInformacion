package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

import modeloParte2.FuenteDeMemoriaNula2;

public class VentanaParte2 extends JFrame implements ActionListener, KeyListener {
	private JPanel switchEstado;
	private JTextField fieldCantSimbolos;
	private JPanel panelFuente;
	private JButton botonSiguienteFuente, botonSiguienteInicio, botonF1;
	private JTextField[][] matrizFuente;
	private JTextArea textoEntropia, textoTeorica, textoLongMedia, textoKraft, textoCompacto;
	private int cantSimbolos;
	private FuenteDeMemoriaNula2 fuente;
	private boolean fuenteCorrecta;

	public VentanaParte2() {
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

		this.botonF1 = new JButton("Fuente de ejemplo");
		botonF1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.botonF1.addActionListener(this);
		this.botonF1.setActionCommand("FUENTE1");
		anexo.add(this.botonF1, BorderLayout.CENTER);

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
		gbl_panelResultadosTeoricos.columnWidths = new int[] { 125, 377, 125, 377 };
		gbl_panelResultadosTeoricos.rowHeights = new int[] { 30, 30 };
		gbl_panelResultadosTeoricos.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelResultadosTeoricos.rowWeights = new double[] { 0.0, 1.0 };
		panelResultadosTeoricos.setLayout(gbl_panelResultadosTeoricos);

		JLabel labelEntropiaTeorica = new JLabel("Entrop\u00EDa:");
		labelEntropiaTeorica.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelEntropiaTeorica = new GridBagConstraints();
		gbc_labelEntropiaTeorica.fill = GridBagConstraints.BOTH;
		gbc_labelEntropiaTeorica.insets = new Insets(0, 0, 5, 5);
		gbc_labelEntropiaTeorica.gridx = 0;
		gbc_labelEntropiaTeorica.gridy = 0;
		panelResultadosTeoricos.add(labelEntropiaTeorica, gbc_labelEntropiaTeorica);

		this.textoEntropia = new JTextArea();
		this.textoEntropia.setEditable(false);
		GridBagConstraints gbc_textoEntropiaTeorica = new GridBagConstraints();
		gbc_textoEntropiaTeorica.insets = new Insets(0, 0, 5, 5);
		gbc_textoEntropiaTeorica.fill = GridBagConstraints.BOTH;
		gbc_textoEntropiaTeorica.gridx = 1;
		gbc_textoEntropiaTeorica.gridy = 0;
		panelResultadosTeoricos.add(this.textoEntropia, gbc_textoEntropiaTeorica);

		JLabel labelCompacto = new JLabel("\u00BFEs compacto?");
		labelCompacto.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelCompacto = new GridBagConstraints();
		gbc_labelCompacto.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelCompacto.insets = new Insets(0, 0, 5, 5);
		gbc_labelCompacto.gridx = 2;
		gbc_labelCompacto.gridy = 0;
		panelResultadosTeoricos.add(labelCompacto, gbc_labelCompacto);

		JLabel labelLongitudMedia = new JLabel("Longitud media:");
		labelLongitudMedia.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelLongitudMedia = new GridBagConstraints();
		gbc_labelLongitudMedia.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelLongitudMedia.insets = new Insets(0, 0, 0, 5);
		gbc_labelLongitudMedia.gridx = 0;
		gbc_labelLongitudMedia.gridy = 1;
		panelResultadosTeoricos.add(labelLongitudMedia, gbc_labelLongitudMedia);

		this.textoLongMedia = new JTextArea();
		GridBagConstraints gbc_textoLongMedia = new GridBagConstraints();
		gbc_textoLongMedia.insets = new Insets(0, 0, 0, 5);
		gbc_textoLongMedia.fill = GridBagConstraints.BOTH;
		gbc_textoLongMedia.gridx = 1;
		gbc_textoLongMedia.gridy = 1;
		panelResultadosTeoricos.add(this.textoLongMedia, gbc_textoLongMedia);

		JLabel labelKraft = new JLabel("\u00BFCumple Kraft?");
		labelKraft.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelKraft = new GridBagConstraints();
		gbc_labelKraft.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelKraft.insets = new Insets(0, 0, 0, 5);
		gbc_labelKraft.gridx = 2;
		gbc_labelKraft.gridy = 1;
		panelResultadosTeoricos.add(labelKraft, gbc_labelKraft);

		this.textoCompacto = new JTextArea();
		GridBagConstraints gbc_textoCompacto = new GridBagConstraints();
		gbc_textoCompacto.insets = new Insets(0, 0, 0, 5);
		gbc_textoCompacto.fill = GridBagConstraints.BOTH;
		gbc_textoCompacto.gridx = 3;
		gbc_textoCompacto.gridy = 0;
		panelResultadosTeoricos.add(this.textoCompacto, gbc_textoCompacto);

		this.textoKraft = new JTextArea();
		GridBagConstraints gbc_textoKraft = new GridBagConstraints();
		gbc_textoKraft.insets = new Insets(0, 0, 0, 5);
		gbc_textoKraft.fill = GridBagConstraints.BOTH;
		gbc_textoKraft.gridx = 3;
		gbc_textoKraft.gridy = 1;
		panelResultadosTeoricos.add(this.textoKraft, gbc_textoKraft);

		JPanel panelFuenteTeorica = new JPanel();
		panelTeorico.add(panelFuenteTeorica, BorderLayout.CENTER);
		panelFuenteTeorica.setLayout(new BorderLayout(0, 0));

		JLabel labelFuenteTeorica = new JLabel("Fuente:");
		panelFuenteTeorica.add(labelFuenteTeorica, BorderLayout.NORTH);

		this.textoTeorica = new JTextArea();
		this.textoTeorica.setEditable(false);
		panelFuenteTeorica.add(this.textoTeorica, BorderLayout.CENTER);

		this.fuenteCorrecta = false;
		this.setMinimumSize(new Dimension(1024, 500));
		this.setResizable(false);
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
			String tabla[][] = new String[this.cantSimbolos][3];
			for (i = 0; i < this.cantSimbolos; i++) {
				tabla[i][0] = this.matrizFuente[i][0].getText();
				tabla[i][2] = this.matrizFuente[i][1].getText();
			}
			this.generarDatosFuente(tabla);
			layout.show(this.switchEstado, "RESULTADOS");

		} else if (boton.getActionCommand().equals("FUENTE1")) {
			// Pasa a la pantalla de resultados correspondiente a la fuente de ejemplo
			String tabla[][] = { { "S1", "", "0.4" }, { "S2", "", "0.1" }, { "S3", "", "0.3" }, { "S4", "", "0.2" } };
			this.cantSimbolos = 4;
			this.generarDatosFuente(tabla);
			layout.show(this.switchEstado, "RESULTADOS");
		}
	}

	// Genera la fuente con los datos ingresado y realiza todos los cálculos
	// necesarios
	public void generarDatosFuente(String tabla[][]) {
		this.fuente = new FuenteDeMemoriaNula2(tabla, this.cantSimbolos);

		this.textoTeorica.setText(this.fuente.getFuente());
		this.textoEntropia.setText(this.fuente.getEntropia() + "");
		this.textoLongMedia.setText(this.fuente.getLongitudMedia() + "");
		this.textoCompacto.setText(this.fuente.esCompacto() + "");
		this.textoKraft.setText(this.fuente.cumpleKraft() + "");
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
		} else {
			// Valida los campos de la segunda pantalla (para fuentes nuevas)
			int i, j;
			double suma;

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

			this.botonSiguienteFuente.setEnabled(this.fuenteCorrecta);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
