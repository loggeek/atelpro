package com.loggeek.vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import com.loggeek.controleur.Interactions;
import com.loggeek.modele.metier.Responsable;


/**
 * Permet la connexion d'un responsable.
 */
public class Connexion extends JFrame
{
	/**
	 * Affiche la fenêtre.
	 *
	 * @param argv inutilisé
	 */
	public static void main(String[] argv)
	{
		try
		{
			Connexion frame = new Connexion();
			frame.setTitle("Connexion");
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Crée la fenêtre.
	 */
	public Connexion()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblLogin = new JLabel("Login");
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.anchor = GridBagConstraints.EAST;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 0;
		contentPane.add(lblLogin, gbc_lblLogin);
		
		JTextField fieldLogin = new JTextField();
		GridBagConstraints gbc_fieldLogin = new GridBagConstraints();
		gbc_fieldLogin.insets = new Insets(0, 0, 5, 0);
		gbc_fieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldLogin.gridx = 1;
		gbc_fieldLogin.gridy = 0;
		contentPane.add(fieldLogin, gbc_fieldLogin);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		GridBagConstraints gbc_lblMotDePasse = new GridBagConstraints();
		gbc_lblMotDePasse.anchor = GridBagConstraints.EAST;
		gbc_lblMotDePasse.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotDePasse.gridx = 0;
		gbc_lblMotDePasse.gridy = 1;
		contentPane.add(lblMotDePasse, gbc_lblMotDePasse);
		
		JPasswordField fieldMotDePasse = new JPasswordField();
		GridBagConstraints gbc_fieldMotDePasse = new GridBagConstraints();
		gbc_fieldMotDePasse.insets = new Insets(0, 0, 5, 0);
		gbc_fieldMotDePasse.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldMotDePasse.gridx = 1;
		gbc_fieldMotDePasse.gridy = 1;
		contentPane.add(fieldMotDePasse, gbc_fieldMotDePasse);
		
		JButton btnValider = new JButton("Valider");
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.gridwidth = 2;
		gbc_btnValider.gridx = 0;
		gbc_btnValider.gridy = 2;
		contentPane.add(btnValider, gbc_btnValider);
		
		btnValider.addActionListener(event -> Interactions.connexion(
				this,
				new Responsable(
						fieldLogin.getText(),
						new String(fieldMotDePasse.getPassword())
				)
		));
	}
}