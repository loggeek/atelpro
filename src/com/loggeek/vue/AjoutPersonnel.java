package com.loggeek.vue;

import com.loggeek.controleur.*;
import com.loggeek.modele.metier.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static com.loggeek.controleur.Extractions.getNomsServices;
import static com.loggeek.modele.dal.AccesDonnees.*;


/**
 * Permet l'ajout d'un membre du personnel.
 */
public class AjoutPersonnel extends JFrame
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
			AjoutPersonnel frame = new AjoutPersonnel();
			frame.setTitle("Ajouter un personnel");
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
	public AjoutPersonnel()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		contentPane.add(lblNom, gbc_lblNom);
		
		JTextField fieldNom = new JTextField();
		GridBagConstraints gbc_fieldNom = new GridBagConstraints();
		gbc_fieldNom.insets = new Insets(0, 0, 5, 0);
		gbc_fieldNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldNom.gridx = 1;
		gbc_fieldNom.gridy = 0;
		contentPane.add(fieldNom, gbc_fieldNom);
		fieldNom.setColumns(10);
		
		JLabel lblPrenom = new JLabel("Prénom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.anchor = GridBagConstraints.EAST;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 1;
		contentPane.add(lblPrenom, gbc_lblPrenom);
		
		JTextField fieldPrenom = new JTextField();
		GridBagConstraints gbc_fieldPrenom = new GridBagConstraints();
		gbc_fieldPrenom.insets = new Insets(0, 0, 5, 0);
		gbc_fieldPrenom.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldPrenom.gridx = 1;
		gbc_fieldPrenom.gridy = 1;
		contentPane.add(fieldPrenom, gbc_fieldPrenom);
		fieldPrenom.setColumns(10);
		
		JLabel lblTel = new JLabel("N° de Téléphone");
		GridBagConstraints gbc_lblTel = new GridBagConstraints();
		gbc_lblTel.anchor = GridBagConstraints.EAST;
		gbc_lblTel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTel.gridx = 0;
		gbc_lblTel.gridy = 2;
		contentPane.add(lblTel, gbc_lblTel);
		
		JTextField fieldTel = new JTextField();
		GridBagConstraints gbc_fieldTel = new GridBagConstraints();
		gbc_fieldTel.insets = new Insets(0, 0, 5, 0);
		gbc_fieldTel.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldTel.gridx = 1;
		gbc_fieldTel.gridy = 2;
		contentPane.add(fieldTel, gbc_fieldTel);
		fieldTel.setColumns(10);
		
		JLabel lblMail = new JLabel("Addresse Mail");
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.anchor = GridBagConstraints.EAST;
		gbc_lblMail.insets = new Insets(0, 0, 5, 5);
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 3;
		contentPane.add(lblMail, gbc_lblMail);
		
		JTextField fieldMail = new JTextField();
		GridBagConstraints gbc_fieldMail = new GridBagConstraints();
		gbc_fieldMail.insets = new Insets(0, 0, 5, 0);
		gbc_fieldMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldMail.gridx = 1;
		gbc_fieldMail.gridy = 3;
		contentPane.add(fieldMail, gbc_fieldMail);
		fieldMail.setColumns(10);
		
		JLabel lblService = new JLabel("Service");
		GridBagConstraints gbc_lblService = new GridBagConstraints();
		gbc_lblService.insets = new Insets(0, 0, 5, 5);
		gbc_lblService.anchor = GridBagConstraints.EAST;
		gbc_lblService.gridx = 0;
		gbc_lblService.gridy = 4;
		contentPane.add(lblService, gbc_lblService);
		
		JComboBox<String> comboBoxService = new JComboBox<>(
				getNomsServices(getServices()).toArray(String[]::new)
		);
		GridBagConstraints gbc_comboBoxService = new GridBagConstraints();
		gbc_comboBoxService.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxService.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxService.gridx = 1;
		gbc_comboBoxService.gridy = 4;
		contentPane.add(comboBoxService, gbc_comboBoxService);
		
		JButton btnValider = new JButton("Valider");
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.gridwidth = 2;
		gbc_btnValider.gridx = 0;
		gbc_btnValider.gridy = 5;
		contentPane.add(btnValider, gbc_btnValider);
		
		btnValider.addActionListener(event -> {
			ArrayList<Integer> personnelIDs = getPersonnelIDs();
			if (personnelIDs.isEmpty()) personnelIDs.add(0);
			
			Interactions.validationAjoutPersonnel(
					this,
					new Personnel(
							Collections.max(personnelIDs) + 1,
							fieldNom.getText(),
							fieldPrenom.getText(),
							fieldTel.getText(),
							fieldMail.getText(),
							new Service(
									Extractions.getServiceNomVersID((String) comboBoxService.getSelectedItem()),
									(String) comboBoxService.getSelectedItem()
							)
					)
			);
		});
	}
}
