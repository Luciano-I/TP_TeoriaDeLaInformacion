package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modeloParte1.FuenteTexto;
import modeloParte1.RLC;

public class VentanaParte1 extends JFrame implements ActionListener {
	private JPanel switchEstado;
	private JFileChooser fileChooser;
	private String direccion;
	private JButton botonHuffman, botonShannon, botonRLC;
	private JLabel labelDireccion;
	private JPanel panel_2, panelTextos;
	private JTextArea textoOriginal, textoCodigo, textoAlfabeto, textoRendimiento, textoRedundancia, textoCompresion;

	public VentanaParte1() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.switchEstado = new JPanel();
		getContentPane().add(this.switchEstado);
		this.switchEstado.setToolTipText("");
		this.switchEstado.setLayout(new CardLayout(0, 0));

		JPanel cardInicio = new JPanel();
		switchEstado.add(cardInicio, "INICIO");
		cardInicio.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardInicio.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 100));

		JPanel panelInicio = new JPanel();
		panel_3.add(panelInicio);
		panelInicio.setLayout(new GridLayout(3, 1, 0, 10));

		JButton botonElegir = new JButton("Elegir Archivo");
		botonElegir.setFont(new Font("Tahoma", Font.BOLD, 14));
		botonElegir.addActionListener(this);
		botonElegir.setActionCommand("ARCHIVO");
		panelInicio.add(botonElegir);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelInicio.add(panel_4);

		this.labelDireccion = new JLabel("");
		panel_4.add(labelDireccion);
		labelDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDireccion.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelInicioBotones = new JPanel();
		panelInicio.add(panelInicioBotones);
		panelInicioBotones.setLayout(new GridLayout(0, 3, 10, 0));

		this.botonHuffman = new JButton("Huffman");
		botonHuffman.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.botonHuffman.addActionListener(this);
		this.botonHuffman.setActionCommand("HUFFMAN");
		this.botonHuffman.setEnabled(false);
		panelInicioBotones.add(this.botonHuffman);

		this.botonShannon = new JButton("Shannon-Fano");
		botonShannon.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.botonShannon.addActionListener(this);
		this.botonShannon.setActionCommand("SHANNON");
		this.botonShannon.setEnabled(false);
		panelInicioBotones.add(this.botonShannon);

		this.botonRLC = new JButton("RLC");
		this.botonRLC.addActionListener(this);
		this.botonRLC.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.botonRLC.setEnabled(false);
		this.botonRLC.setActionCommand("RLC");
		panelInicioBotones.add(this.botonRLC);

		JPanel cardResultados = new JPanel();
		switchEstado.add(cardResultados, "RESULTADOS");
		cardResultados.setLayout(new BorderLayout(0, 5));

		JPanel panelResultados = new JPanel();
		cardResultados.add(panelResultados);
		panelResultados.setLayout(new BorderLayout(0, 10));

		this.panelTextos = new JPanel();
		panelResultados.add(this.panelTextos, BorderLayout.CENTER);
		this.panelTextos.setLayout(new GridLayout(0, 3, 10, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		this.panelTextos.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollOriginal = new JScrollPane();
		panel.add(scrollOriginal);

		this.textoOriginal = new JTextArea();
		textoOriginal.setLineWrap(true);
		textoOriginal.setEditable(false);
		scrollOriginal.setViewportView(this.textoOriginal);

		JLabel labelOriginal = new JLabel("Texto original");
		labelOriginal.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollOriginal.setColumnHeaderView(labelOriginal);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		this.panelTextos.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollCodigo = new JScrollPane();
		panel_1.add(scrollCodigo);

		this.textoCodigo = new JTextArea();
		textoCodigo.setEditable(false);
		textoCodigo.setLineWrap(true);
		scrollCodigo.setViewportView(this.textoCodigo);

		JLabel labelCodigo = new JLabel("Resultado");
		labelCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollCodigo.setColumnHeaderView(labelCodigo);
		
		this.panel_2 = new JPanel();
		this.panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		this.panelTextos.add(this.panel_2);
		this.panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollAlfabeto_1 = new JScrollPane();
		this.panel_2.add(scrollAlfabeto_1);
		
		JLabel labelAlfabeto = new JLabel("Alfabeto resultante");
		labelAlfabeto.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollAlfabeto_1.setColumnHeaderView(labelAlfabeto);
		
		this.textoAlfabeto = new JTextArea();
		textoAlfabeto.setColumns(3);
		textoAlfabeto.setRows(1);
		textoAlfabeto.setTabSize(0);
		this.textoAlfabeto.setLineWrap(true);
		this.textoAlfabeto.setEditable(false);
		scrollAlfabeto_1.setViewportView(this.textoAlfabeto);

		JPanel panelCalculos = new JPanel();
		panelCalculos.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelResultados.add(panelCalculos, BorderLayout.SOUTH);
		panelCalculos.setLayout(new GridLayout(0, 3, 10, 0));

		JPanel panelRendimiento = new JPanel();
		panelRendimiento.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelCalculos.add(panelRendimiento);
		panelRendimiento.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel labelRendimiento = new JLabel("Rendimiento del código:");
		labelRendimiento.setHorizontalAlignment(SwingConstants.TRAILING);
		labelRendimiento.setFont(new Font("Tahoma", Font.BOLD, 10));
		panelRendimiento.add(labelRendimiento);

		this.textoRendimiento = new JTextArea();
		this.textoRendimiento.setEditable(false);
		panelRendimiento.add(this.textoRendimiento);

		JPanel panelRedundancia = new JPanel();
		panelRedundancia.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelCalculos.add(panelRedundancia);
		panelRedundancia.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel labelRedundancia = new JLabel("Redundancia del código:");
		labelRedundancia.setHorizontalAlignment(SwingConstants.TRAILING);
		labelRedundancia.setFont(new Font("Tahoma", Font.BOLD, 10));
		panelRedundancia.add(labelRedundancia);

		this.textoRedundancia = new JTextArea();
		this.textoRedundancia.setEditable(false);
		panelRedundancia.add(this.textoRedundancia);

		JPanel panelCompresion = new JPanel();
		panelCompresion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelCalculos.add(panelCompresion);
		panelCompresion.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel labelCompresion = new JLabel("Tasa de compresión:");
		labelCompresion.setHorizontalAlignment(SwingConstants.TRAILING);
		labelCompresion.setFont(new Font("Tahoma", Font.BOLD, 10));
		panelCompresion.add(labelCompresion);

		this.textoCompresion = new JTextArea();
		this.textoCompresion.setEditable(false);
		panelCompresion.add(this.textoCompresion);

		this.fileChooser = new JFileChooser();
		this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto", "txt");
		this.fileChooser.setFileFilter(filtro);
		this.fileChooser.setAcceptAllFileFilterUsed(false);

		this.setMinimumSize(new Dimension(1024, 500));
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton boton = (JButton) arg0.getSource();
		CardLayout layout = (CardLayout) this.switchEstado.getLayout();
		int indice;
		if (boton.getActionCommand().equals("ARCHIVO")) {
			// Carga el archivo de entrada
			int resultado = fileChooser.showOpenDialog(this);
			if (resultado == JFileChooser.APPROVE_OPTION) {
				this.direccion = fileChooser.getSelectedFile().getAbsolutePath();
				this.botonHuffman.setEnabled(true);
				this.botonShannon.setEnabled(true);
				this.botonRLC.setEnabled(true);
				indice = this.direccion.lastIndexOf("\\");
				this.labelDireccion.setText(this.direccion.substring(indice + 1));
			}
		} else {
			if (boton.getActionCommand().equals("RLC")) {
				RLC rlc = new RLC(this.direccion);
				this.panel_2.setVisible(false);
				this.panelTextos.remove(this.panel_2);
				GridLayout grid = (GridLayout) this.panelTextos.getLayout();
				grid.setColumns(2);
				this.textoOriginal.setText(rlc.getTextoEntrada());
				this.textoOriginal.setCaretPosition(0);
				this.textoCodigo.setText(rlc.getTextoSalida());
				this.textoCodigo.setCaretPosition(0);
				this.textoRedundancia.setText("No aplica.");
				this.textoRendimiento.setText("No aplica.");
				this.textoCompresion.setText(rlc.getTasaCompresion() + " : 1");
			} else {
				// Aplica el código seleccionado, realiza los cálculos necesarios y muestra los resultados
				FuenteTexto fuente = new FuenteTexto(this.direccion);
				if (boton.getActionCommand().equals("HUFFMAN"))
					fuente.huffman();
				else
					fuente.shannonFano();
				fuente.generarTextoCodigo();
				this.textoOriginal.setText(fuente.getTexto());
				this.textoOriginal.setCaretPosition(0);
				this.textoCodigo.setText(fuente.getTextoCodigo());
				this.textoCodigo.setCaretPosition(0);
				this.textoAlfabeto.setText(fuente.getTabla());
				this.textoAlfabeto.setCaretPosition(0);
				this.textoRendimiento.setText(fuente.getRendimiento() + "");
				this.textoRedundancia.setText(fuente.getRedundancia() + "");
				this.textoCompresion.setText(fuente.getTasaCompresion() + " : 1");
			}
			layout.show(this.switchEstado, "RESULTADOS");
		}
	}
}
