package com.loggeek.vue;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;


/**
 * Permet l'affichage et la gestion des absences.
 */
public class AffichageAbsences extends JFrame
{
	private final String[] colonnes = {"Début", "Fin", "Motif"};
	private final DefaultTableModel modele;
	
	/**
	 * Affiche la fenêtre.
	 *
	 * @param argv inutilisé
	 */
	public static void main(String[] argv)
	{
		EventQueue.invokeLater(() -> {
			try
			{
				AffichageAbsences frame = new AffichageAbsences();
				frame.setTitle("Absences");
				frame.setVisible(true);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Crée la fenêtre.
	 */
	public AffichageAbsences()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{1, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		modele = new DefaultTableModel(null, colonnes);
		JTable table = new JTable(modele);
		JScrollPane pane = new JScrollPane(table);
		GridBagConstraints gbc_pane = new GridBagConstraints();
		gbc_pane.gridheight = 4;
		gbc_pane.insets = new Insets(0, 0, 0, 5);
		gbc_pane.fill = GridBagConstraints.BOTH;
		gbc_pane.gridx = 0;
		gbc_pane.gridy = 0;
		contentPane.add(pane, gbc_pane);
		
		JButton btnAjouterAbsence = new JButton("Ajouter Absence");
		GridBagConstraints gbc_btnAjouterAbsence = new GridBagConstraints();
		gbc_btnAjouterAbsence.insets = new Insets(0, 0, 5, 0);
		gbc_btnAjouterAbsence.gridx = 1;
		gbc_btnAjouterAbsence.gridy = 0;
		contentPane.add(btnAjouterAbsence, gbc_btnAjouterAbsence);
		
		JButton btnModifierAbsence = new JButton("Modifier Absence");
		GridBagConstraints gbc_btnModifierAbsence = new GridBagConstraints();
		gbc_btnModifierAbsence.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifierAbsence.gridx = 1;
		gbc_btnModifierAbsence.gridy = 1;
		contentPane.add(btnModifierAbsence, gbc_btnModifierAbsence);
		
		JButton btnSupprimerAbsence = new JButton("Supprimer Absence");
		GridBagConstraints gbc_btnSupprimerAbsence = new GridBagConstraints();
		gbc_btnSupprimerAbsence.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimerAbsence.gridx = 1;
		gbc_btnSupprimerAbsence.gridy = 2;
		contentPane.add(btnSupprimerAbsence, gbc_btnSupprimerAbsence);
		
		btnAjouterAbsence.addActionListener(event -> System.out.println("btnAjouterAbsence"));
		btnModifierAbsence.addActionListener(event -> System.out.println("btnModifierAbsence"));
		btnSupprimerAbsence.addActionListener(event -> System.out.println("btnSupprimerAbsence"));
	}
	
	/**
	 * Met à jour des données de la table des absences.
	 *
	 * @param donnees les données à mettre dans la table
	 */
	public void majTable(String[][] donnees)
	{
		modele.setDataVector(donnees, colonnes);
		modele.fireTableDataChanged();
	}
}
