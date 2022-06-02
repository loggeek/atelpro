package com.loggeek.vue;

import com.loggeek.controleur.Interactions;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;


/**
 * Fenêtre principale de l'application.
 */
public class FenetrePrincipale extends JFrame
{
	private final String[] colonnes = {"Identifiant", "Nom", "Prénom", "N° de Téléphone", "Adresse Mail", "Service"};
	private final DefaultTableModel modele;
	
	private static FenetrePrincipale frame = null;
	
	/**
	 * Affiche la fenêtre.
	 *
	 * @param argv inutilisé
	 */
	
	public static void main(String[] argv)
	{
		try
		{
			frame = new FenetrePrincipale();
			frame.setTitle("Logiciel de gestion pour MediaTek86");
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de récupérer une référence à la fenêtre principale.
	 *
	 * @return la fenêtre principale
	 */
	public static FenetrePrincipale getFenetre()
	{
		return frame;
	}
	
	/**
	 * Crée la fenêtre.
	 */
	public FenetrePrincipale()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{1, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		modele = new DefaultTableModel(null, colonnes)
		{
			@Override public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		JTable table = new JTable(modele);
		JScrollPane pane = new JScrollPane(table);
		GridBagConstraints gbc_pane = new GridBagConstraints();
		gbc_pane.gridheight = 5;
		gbc_pane.insets = new Insets(0, 0, 0, 5);
		gbc_pane.fill = GridBagConstraints.BOTH;
		gbc_pane.gridx = 0;
		gbc_pane.gridy = 0;
		contentPane.add(pane, gbc_pane);
		
		table.setAutoCreateRowSorter(true);
		this.majTable();
		
		JButton btnAjouterPersonnel = new JButton("Ajouter Personnel");
		GridBagConstraints gbc_btnAjouterPersonnel = new GridBagConstraints();
		gbc_btnAjouterPersonnel.insets = new Insets(0, 0, 5, 0);
		gbc_btnAjouterPersonnel.gridx = 1;
		gbc_btnAjouterPersonnel.gridy = 0;
		contentPane.add(btnAjouterPersonnel, gbc_btnAjouterPersonnel);
		
		JButton btnModifierPersonnel = new JButton("Modifier Personnel");
		GridBagConstraints gbc_btnModifierPersonnel = new GridBagConstraints();
		gbc_btnModifierPersonnel.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifierPersonnel.gridx = 1;
		gbc_btnModifierPersonnel.gridy = 1;
		contentPane.add(btnModifierPersonnel, gbc_btnModifierPersonnel);
		
		JButton btnSupprimerPersonnel = new JButton("Supprimer Personnel");
		GridBagConstraints gbc_btnSupprimerPersonnel = new GridBagConstraints();
		gbc_btnSupprimerPersonnel.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimerPersonnel.gridx = 1;
		gbc_btnSupprimerPersonnel.gridy = 2;
		contentPane.add(btnSupprimerPersonnel, gbc_btnSupprimerPersonnel);
		
		JButton btnAfficherAbsences = new JButton("Afficher Absences");
		GridBagConstraints gbc_btnAfficherAbsences = new GridBagConstraints();
		gbc_btnAfficherAbsences.insets = new Insets(0, 0, 5, 0);
		gbc_btnAfficherAbsences.gridx = 1;
		gbc_btnAfficherAbsences.gridy = 3;
		contentPane.add(btnAfficherAbsences, gbc_btnAfficherAbsences);
		
		btnAjouterPersonnel.addActionListener(event -> AjoutPersonnel.main(null));
		btnModifierPersonnel.addActionListener(event -> {
			if (table.getSelectedRow() != -1)
				ModificationPersonnel.main(new String[] {
						table.getValueAt(table.getSelectedRow(), 0).toString(),
						table.getValueAt(table.getSelectedRow(), 1).toString(),
						table.getValueAt(table.getSelectedRow(), 2).toString(),
						table.getValueAt(table.getSelectedRow(), 3).toString(),
						table.getValueAt(table.getSelectedRow(), 4).toString(),
						table.getValueAt(table.getSelectedRow(), 5).toString(),
				});
			else
				JOptionPane.showMessageDialog(
						this,
						"Aucun membre du personnel a été sélectionné!",
						"Erreur",
						JOptionPane.WARNING_MESSAGE
				);
		});
		btnSupprimerPersonnel.addActionListener(event -> {
			if (table.getSelectedRow() != -1)
				Interactions.supprimerPersonnel(
						this,
						Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString())
				);
			else
				JOptionPane.showMessageDialog(
						this,
						"Aucun membre du personnel a été sélectionné!",
						"Erreur",
						JOptionPane.WARNING_MESSAGE
				);
		});
		btnAfficherAbsences.addActionListener(event -> {
			if (table.getSelectedRow() != -1)
				AffichageAbsences.main(new String[] {
						table.getValueAt(table.getSelectedRow(), 0).toString(),
						table.getValueAt(table.getSelectedRow(), 1).toString(),
						table.getValueAt(table.getSelectedRow(), 2).toString(),
						table.getValueAt(table.getSelectedRow(), 3).toString(),
						table.getValueAt(table.getSelectedRow(), 4).toString(),
						table.getValueAt(table.getSelectedRow(), 5).toString(),
				});
			else
				JOptionPane.showMessageDialog(
						this,
						"Aucun membre du personnel a été sélectionné!",
						"Erreur",
						JOptionPane.WARNING_MESSAGE
				);
		});
	}
	
	/**
	 * Met à jour les données de la table des absences.
	 */
	public void majTable()
	{
		modele.setDataVector(Interactions.majTablePersonnels(), colonnes);
		modele.fireTableDataChanged();
	}
}
