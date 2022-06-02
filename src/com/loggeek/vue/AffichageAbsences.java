package com.loggeek.vue;

import com.loggeek.controleur.Extractions;
import com.loggeek.controleur.Interactions;
import com.loggeek.modele.metier.Personnel;
import com.loggeek.modele.metier.Service;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Permet l'affichage et la gestion des absences.
 */
public class AffichageAbsences extends JFrame
{
	private final String[] colonnes = {"Début", "Fin", "Motif"};
	private final DefaultTableModel modele;
	
	private static AffichageAbsences frame = null;
	
	/**
	 * Permet de récupérer une référence à cette fenêtre.
	 *
	 * @return la fenêtre principale
	 */
	public static AffichageAbsences getFenetre()
	{
		return frame;
	}
	
	/**
	 * Affiche la fenêtre.
	 *
	 * @param argv contient les données du personnel
	 */
	public static void main(String[] argv)
	{
		try
		{
			frame = new AffichageAbsences(new Personnel(
					Integer.parseInt(argv[0]),
					argv[1],
					argv[2],
					argv[3],
					argv[4],
					new Service(
							Extractions.getServiceNomVersID(argv[5]),
							argv[5]
					)
			));
			frame.setTitle(String.format("Absences de %s, %s", argv[1], argv[2]));
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private final Personnel personnel;
	
	/**
	 * Crée la fenêtre.
	 */
	public AffichageAbsences(Personnel personnel)
	{
		this.personnel = personnel;
		
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
		
		table.setAutoCreateRowSorter(true);
		this.majTable();
		
		btnAjouterAbsence.addActionListener(event -> AjoutAbsence.main(personnel));
		btnModifierAbsence.addActionListener(event -> {
			if (table.getSelectedRow() != -1)
				ModificationAbsence.main(new String[] {
						table.getValueAt(table.getSelectedRow(), 0).toString(),
						table.getValueAt(table.getSelectedRow(), 1).toString(),
						table.getValueAt(table.getSelectedRow(), 2).toString()
				}, personnel);
			else
				JOptionPane.showMessageDialog(
						this,
						"Aucun membre du personnel a été sélectionné!",
						"Erreur",
						JOptionPane.WARNING_MESSAGE
				);
		});
		btnSupprimerAbsence.addActionListener(event -> {
			SimpleDateFormat dateFmtFr = new SimpleDateFormat("dd/MM/yyyy");
			
			try
			{
				if (table.getSelectedRow() != -1)
					Interactions.supprimerAbsence(
							this,
							personnel,
							dateFmtFr.parse(table.getValueAt(table.getSelectedRow(), 0).toString())
					);
				else
					JOptionPane.showMessageDialog(
							this,
							"Aucune absence a été sélectionnée!",
							"Erreur",
							JOptionPane.WARNING_MESSAGE
					);
			}
			catch (ParseException e)
			{
				// N'arrive jamais
			}
		});
	}
	
	/**
	 * Met à jour des données de la table des absences.
	 */
	public void majTable()
	{
		modele.setDataVector(Interactions.majTableAbsences(personnel), colonnes);
		modele.fireTableDataChanged();
	}
}
