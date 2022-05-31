package com.loggeek.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class AjoutAbsence extends JFrame
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() -> {
			try
			{
				AjoutAbsence frame = new AjoutAbsence();
				frame.setTitle("Ajouter une absence");
				frame.setVisible(true);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
	
	public AjoutAbsence()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblDebut = new JLabel("Début");
		GridBagConstraints gbc_lblDebut = new GridBagConstraints();
		gbc_lblDebut.insets = new Insets(0, 0, 5, 5);
		gbc_lblDebut.anchor = GridBagConstraints.EAST;
		gbc_lblDebut.gridx = 0;
		gbc_lblDebut.gridy = 0;
		contentPane.add(lblDebut, gbc_lblDebut);
		
		JTextField fieldDebut = new JTextField();
		GridBagConstraints gbc_fieldDebut = new GridBagConstraints();
		gbc_fieldDebut.insets = new Insets(0, 0, 5, 0);
		gbc_fieldDebut.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldDebut.gridx = 1;
		gbc_fieldDebut.gridy = 0;
		contentPane.add(fieldDebut, gbc_fieldDebut);
		fieldDebut.setColumns(10);
		
		JLabel lblFin = new JLabel("Fin");
		GridBagConstraints gbc_lblFin = new GridBagConstraints();
		gbc_lblFin.anchor = GridBagConstraints.EAST;
		gbc_lblFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFin.gridx = 0;
		gbc_lblFin.gridy = 1;
		contentPane.add(lblFin, gbc_lblFin);
		
		JTextField fieldFin = new JTextField();
		GridBagConstraints gbc_fieldFin = new GridBagConstraints();
		gbc_fieldFin.insets = new Insets(0, 0, 5, 0);
		gbc_fieldFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldFin.gridx = 1;
		gbc_fieldFin.gridy = 1;
		contentPane.add(fieldFin, gbc_fieldFin);
		fieldFin.setColumns(10);
		
		JLabel lblMotif = new JLabel("Motif");
		GridBagConstraints gbc_lblMotif = new GridBagConstraints();
		gbc_lblMotif.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotif.anchor = GridBagConstraints.EAST;
		gbc_lblMotif.gridx = 0;
		gbc_lblMotif.gridy = 2;
		contentPane.add(lblMotif, gbc_lblMotif);
		
		JComboBox<String> comboBoxMotif = new JComboBox<>();
		GridBagConstraints gbc_comboBoxMotif = new GridBagConstraints();
		gbc_comboBoxMotif.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxMotif.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMotif.gridx = 1;
		gbc_comboBoxMotif.gridy = 2;
		contentPane.add(comboBoxMotif, gbc_comboBoxMotif);
		
		JButton btnValider = new JButton("Valider");
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.gridwidth = 2;
		gbc_btnValider.gridx = 0;
		gbc_btnValider.gridy = 3;
		contentPane.add(btnValider, gbc_btnValider);
		
		btnValider.addActionListener(event -> System.out.println("btnValider"));
	}
}
