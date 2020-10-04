package ma.inpt.iccn.gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RansomwareFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public RansomwareFrame() {
		setTitle("cryptICCN v1.0");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\PC30\\eclipse-workspace\\Ransomware\\img\\lock-icon.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 20, 60));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(220, 20, 60));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel titre = new JLabel("Oups... Vos fichiers ont \u00E9t\u00E9 crypt\u00E9!");
		titre.setForeground(Color.WHITE);
		titre.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(titre);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(220, 20, 60));
		contentPane.add(panel_1, BorderLayout.WEST);
		
		JLabel imgLateral = new JLabel("");
		Image image=new ImageIcon(this.getClass().getResource("/lock-icon.png")).getImage();
		imgLateral.setIcon(new ImageIcon(image));
		panel_1.add(imgLateral);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel textC = new JLabel();
		textC.setText("<html><body>"
				+ "<h1>Qu'est-ce qui est arrivé à mon ordinateur?</h1>"
				+ "<p>Vos fichiers importants ont été cryptés.<br>"
				+ "Beaucoup de vos documents, photos, vodéos, bases de données ou autres fichiers ne sont plus accessibles"
				+ " car ils sont été cryptés. Peut être vous vous êtes entrain de chercher un moyen pour les récuperer, mais ne perdez pas votre temps."
				+ "Personne ne peut récupérer vos fichiers sans notre service de decryptage</p>"
				+ "<h1>Puis-je récupérer mes fichiers?</h1>"
				+ "<p>Bien sûr! Nous vous garantissons que vous allez récupérer vos fichier en facilement et en toute sécurité. Mais vous n'avez pas assez de temps."
				+ "Pour décrypter l'ensemble de vos fichiers, vous devez payer! Vous avez 7 jours pour passer le payement. Passer ce délai, vous perdrez vos fichiers "
				+ "pour toujours.<p>"
				+ "<h1>Comment puis-je payer?</h1>"
				+ "<p>Le seul mode de payement accepté est le Bitcoin.</p>"
				+ "</body></html>");
		panel_3.add(textC);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(220, 20, 60));
		contentPane.add(panel_4, BorderLayout.SOUTH);
		
		JLabel lblEnvoyez = new JLabel("Envoyez 500\u20AC de bitcoins \u00E0 l'adresse suivante:");
		lblEnvoyez.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEnvoyez.setForeground(Color.ORANGE);
		panel_4.add(lblEnvoyez);
		
		JButton btnNewButton = new JButton("Passer au payement");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\PC30\\eclipse-workspace\\Ransomware\\img\\Bitcoin-icon.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(btnNewButton);
	}
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}

}
