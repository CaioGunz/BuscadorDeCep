package cep;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField textCep;
	private JTextField textEndereco;
	private JTextField textBairro;
	private JTextField textCidade;
	private JComboBox cboUf;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cep() {
		setTitle("Buscar CEP");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("CEP:");
		lblNewLabel.setBounds(35, 60, 45, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Endere\u00E7o:");
		lblNewLabel_1.setBounds(10, 97, 70, 14);
		contentPane.add(lblNewLabel_1);

		textCep = new JTextField();
		textCep.setBounds(73, 57, 117, 20);
		contentPane.add(textCep);
		textCep.setColumns(10);

		textEndereco = new JTextField();
		textEndereco.setBounds(73, 94, 312, 20);
		contentPane.add(textEndereco);
		textEndereco.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Bairro:");
		lblNewLabel_2.setBounds(27, 133, 53, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Cidade:");
		lblNewLabel_3.setBounds(21, 171, 59, 14);
		contentPane.add(lblNewLabel_3);

		textBairro = new JTextField();
		textBairro.setBounds(73, 130, 312, 20);
		contentPane.add(textBairro);
		textBairro.setColumns(10);

		textCidade = new JTextField();
		textCidade.setBounds(73, 168, 218, 20);
		contentPane.add(textCidade);
		textCidade.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("UF:");
		lblNewLabel_4.setBounds(305, 171, 46, 14);
		contentPane.add(lblNewLabel_4);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(334, 167, 51, 22);
		contentPane.add(cboUf);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(35, 215, 89, 23);
		contentPane.add(btnLimpar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o CEP!");
					textCep.requestFocus();
				} else {
					buscarCep();
				}
			}
		});
		btnBuscar.setBounds(225, 56, 89, 23);
		contentPane.add(btnBuscar);

		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Cep.class.getResource("/img/about.png")));
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBorder(null);
		btnSobre.setBackground(SystemColor.control);
		btnSobre.setBounds(334, 11, 48, 48);
		contentPane.add(btnSobre);

		/* Uso da Blibioteca Atxy2k para valida��o do campo txtCep */
		RestrictedTextField validar = new RestrictedTextField(textCep);

		lblStatus = new JLabel("");
		lblStatus.setBounds(195, 57, 24, 22);
		contentPane.add(lblStatus);
		validar.setOnlyNums(true);
		validar.setLimit(8);
	} // fim do construtor

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = textCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					textCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					textBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					} else {
						JOptionPane.showMessageDialog(null, "CEP n�o encontrado!");
					}
				}
			}
			// setar o campo endereco
			textEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void limpar() {
		textCep.setText(null);
		textEndereco.setText(null);
		textBairro.setText(null);
		textCidade.setText(null);
		cboUf.setSelectedItem(null);
		textCep.requestFocus();
		lblStatus.setIcon(null);
	}
}
