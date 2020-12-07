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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import modeloParte1.FuenteTexto;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VentanaParte1 extends JFrame implements ActionListener, KeyListener {
	private JPanel switchEstado;
	private JFileChooser fileChooser;
	private String direccion;
	private JButton botonHuffman, botonShannon;
	private JLabel labelDireccion;
	private FuenteTexto fuente;
	private JTextArea textoOriginal, textoCodigo, textoRLC;

	public VentanaParte1() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.switchEstado = new JPanel();
		getContentPane().add(this.switchEstado);
		this.switchEstado.setToolTipText("");
		this.switchEstado.setLayout(new CardLayout(0, 0));

		JPanel cardInicio = new JPanel();
		switchEstado.add(cardInicio, "INICIO");
		cardInicio.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panelInicio = new JPanel();
		cardInicio.add(panelInicio);
		panelInicio.setLayout(new GridLayout(3, 1, 0, 0));

		JButton botonElegir = new JButton("Elegir Archivo");
		botonElegir.addActionListener(this);
		botonElegir.setActionCommand("ARCHIVO");
		panelInicio.add(botonElegir);

		this.labelDireccion = new JLabel("");
		labelDireccion.setHorizontalAlignment(SwingConstants.CENTER);
		panelInicio.add(this.labelDireccion);

		JPanel panelInicioBotones = new JPanel();
		panelInicio.add(panelInicioBotones);
		panelInicioBotones.setLayout(new GridLayout(0, 2, 0, 0));

		this.botonHuffman = new JButton("Huffman");
		this.botonHuffman.addActionListener(this);
		this.botonHuffman.setActionCommand("HUFFMAN");
		this.botonHuffman.setEnabled(false);
		panelInicioBotones.add(this.botonHuffman);

		this.botonShannon = new JButton("Shannon-Fano");
		this.botonShannon.addActionListener(this);
		this.botonShannon.setActionCommand("SHANNON");
		this.botonShannon.setEnabled(false);
		panelInicioBotones.add(this.botonShannon);

		JPanel cardResultados = new JPanel();
		switchEstado.add(cardResultados, "RESULTADOS");
		cardResultados.setLayout(new BorderLayout(0, 0));
		
		JPanel panelResultados = new JPanel();
		cardResultados.add(panelResultados);
		panelResultados.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTextos = new JPanel();
		panelResultados.add(panelTextos, BorderLayout.CENTER);
		panelTextos.setLayout(new GridLayout(0, 3, 0, 0));
		
		JScrollPane scrollOriginal = new JScrollPane();
		panelTextos.add(scrollOriginal);
		
		this.textoOriginal = new JTextArea();
		scrollOriginal.setViewportView(this.textoOriginal);
		
		JLabel labelOriginal = new JLabel("Texto original");
		scrollOriginal.setColumnHeaderView(labelOriginal);
		
		JScrollPane scrollCodigo = new JScrollPane();
		panelTextos.add(scrollCodigo);
		
		this.textoCodigo = new JTextArea();
		textoCodigo.setLineWrap(true);
		scrollCodigo.setViewportView(this.textoCodigo);
		
		JLabel labelCodigo = new JLabel("Texto codificado");
		scrollCodigo.setColumnHeaderView(labelCodigo);
		
		JScrollPane scrollRLC = new JScrollPane();
		panelTextos.add(scrollRLC);
		
		this.textoRLC = new JTextArea();
		textoRLC.setLineWrap(true);
		scrollRLC.setViewportView(this.textoRLC);
		
		JLabel labelRLC = new JLabel("RLC");
		scrollRLC.setColumnHeaderView(labelRLC);

		this.fileChooser = new JFileChooser();
		this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

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
				System.out.println(this.direccion);
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
			this.textoCodigo.setText(this.fuente.getStringCodigo());
			this.textoRLC.setText(this.fuente.getStringRLC());
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
