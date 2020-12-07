package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import modeloParte1.FuenteTexto;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.border.TitledBorder;

public class VentanaParte1 extends JFrame implements ActionListener, KeyListener {
	private JPanel switchEstado;
	private JFileChooser fileChooser;
	private String direccion;
	private JButton botonHuffman, botonShannon;
	private JLabel labelDireccion;
	private FuenteTexto fuente;
	private JTextArea textoOriginal, textoCodigo, textoRLC, textoRendimiento, textoRedundancia, textoCompresion;

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
		panelInicioBotones.setLayout(new GridLayout(0, 2, 10, 0));

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

		JPanel cardResultados = new JPanel();
		switchEstado.add(cardResultados, "RESULTADOS");
		cardResultados.setLayout(new BorderLayout(0, 5));
		
		JPanel panelResultados = new JPanel();
		cardResultados.add(panelResultados);
		panelResultados.setLayout(new BorderLayout(0, 10));
		
		JPanel panelTextos = new JPanel();
		panelResultados.add(panelTextos, BorderLayout.CENTER);
		panelTextos.setLayout(new GridLayout(0, 3, 10, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelTextos.add(panel);
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
		panelTextos.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollCodigo = new JScrollPane();
		panel_1.add(scrollCodigo);
		
		this.textoCodigo = new JTextArea();
		textoCodigo.setEditable(false);
		textoCodigo.setLineWrap(true);
		scrollCodigo.setViewportView(this.textoCodigo);
		
		JLabel labelCodigo = new JLabel("Texto codificado");
		labelCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollCodigo.setColumnHeaderView(labelCodigo);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelTextos.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollRLC = new JScrollPane();
		panel_2.add(scrollRLC);
		
		this.textoRLC = new JTextArea();
		textoRLC.setEditable(false);
		textoRLC.setLineWrap(true);
		scrollRLC.setViewportView(this.textoRLC);
		
		JLabel labelRLC = new JLabel("RLC");
		labelRLC.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollRLC.setColumnHeaderView(labelRLC);
		
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
			int resultado = fileChooser.showOpenDialog(this);
			if (resultado == JFileChooser.APPROVE_OPTION) {
				this.direccion = fileChooser.getSelectedFile().getAbsolutePath();
				this.botonHuffman.setEnabled(true);
				this.botonShannon.setEnabled(true);
				indice = this.direccion.lastIndexOf("\\");
				this.labelDireccion.setText(this.direccion.substring(indice+1));
			}
		}
		else 
		{
			this.fuente = new FuenteTexto(this.direccion);
			if (boton.getActionCommand().equals("HUFFMAN"))
				this.fuente.huffman();
			else
				this.fuente.shannonFano();
			this.fuente.generarCantInfo();
			this.fuente.generarTextoCodigo();
			this.textoOriginal.setText(this.fuente.getStringOriginal());
			this.textoOriginal.setCaretPosition(0);
			this.textoCodigo.setText(this.fuente.getStringCodigo());
			this.textoCodigo.setCaretPosition(0);
			this.textoRLC.setText(this.fuente.getStringRLC());
			this.textoRLC.setCaretPosition(0);
			this.textoRendimiento.setText(this.fuente.getRendimiento() + "");
			this.textoRedundancia.setText(this.fuente.getRedundancia() + "");
			this.textoCompresion.setText(this.fuente.getTasaCompresion() + ":1");
			layout.show(this.switchEstado, "RESULTADOS");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
