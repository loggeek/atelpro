package com.loggeek.controleur;

import com.loggeek.modele.dal.AccesDonnees;
import com.loggeek.modele.metier.*;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * <b>Classe utilitaire</b><br />
 * Contient des méthodes pour extraire des champs à partir de classes métiers.
 */
public class Extractions
{
	/**
	 * Constructeur caché d'une classe utilitaire.
	 */
	private Extractions() {}
	
	/**
	 * Permet d'extraire les données des membres du personnel.
	 *
	 * @param personnels les membres du personnel
	 * @return les données des membres du personnel
	 */
	public static ArrayList<String[]> getChampsPersonnels(ArrayList<Personnel> personnels)
	{
		ArrayList<String[]> donnees = new ArrayList<>();
		
		for (Personnel personnel: personnels)
		{
			ArrayList<String> colonne = new ArrayList<>();
			colonne.add(String.valueOf(personnel.idpersonnel()));
			colonne.add(personnel.nom());
			colonne.add(personnel.prenom());
			colonne.add(personnel.tel());
			colonne.add(personnel.mail());
			colonne.add(personnel.service().nom());
			
			donnees.add(colonne.toArray(String[]::new));
		}
		
		return donnees;
	}
	
	/**
	 * Permet d'extraire les noms des services.
	 *
	 * @param services les services
	 * @return les noms des services
	 */
	public static ArrayList<String> getNomsServices(ArrayList<Service> services)
	{
		ArrayList<String> donnees = new ArrayList<>();
		
		for (Service service: services) donnees.add(service.nom());
		
		return donnees;
	}
	
	/**
	 * Extrait l'identifiant du service à partir de son nom.
	 * Affiche une alerte s'il en existe plusieurs.
	 *
	 * @param service le nom du service
	 * @return l'identifiant de celui-ci
	 */
	public static int getServiceNomVersID(String service)
	{
		ArrayList<Integer> ids = AccesDonnees.getServiceIDs(service);
		if (ids.size() > 1)
			JOptionPane.showMessageDialog(
					null,
					"Il y existe plusieurs services avec le même nom!",
					"Attention",
					JOptionPane.INFORMATION_MESSAGE
			);
		return ids.get(0);
	}
	
	/**
	 * Permet d'extraire les données des absences.
	 *
	 * @param absences les absences
	 * @return les données des absences
	 */
	public static ArrayList<String[]> getChampsAbsences(ArrayList<Absence> absences)
	{
		ArrayList<String[]> donnees = new ArrayList<>();
		
		SimpleDateFormat dateFmtFr = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Absence absence: absences)
		{
			ArrayList<String> colonne = new ArrayList<>();
			colonne.add(dateFmtFr.format(absence.datedebut()));
			colonne.add(dateFmtFr.format(absence.datefin()));
			colonne.add(absence.motif().libelle());
			
			donnees.add(colonne.toArray(String[]::new));
		}
		
		return donnees;
	}
	
	/**
	 * Permet d'extraire les noms des motifs.
	 *
	 * @param motifs les motifs
	 * @return les noms des motifs
	 */
	public static ArrayList<String> getNomsMotifs(ArrayList<Motif> motifs)
	{
		ArrayList<String> donnees = new ArrayList<>();
		
		for (Motif motif: motifs) donnees.add(motif.libelle());
		
		return donnees;
	}
	
	/**
	 * Extrait l'identifiant du motif à partir de son nom.
	 * Affiche une alerte s'il en existe plusieurs.
	 *
	 * @param motif le libellé du motif
	 * @return l'identifiant de celui-ci
	 */
	public static int getMotifNomVersID(String motif)
	{
		ArrayList<Integer> ids = AccesDonnees.getMotifIDs(motif);
		if (ids.size() > 1)
			JOptionPane.showMessageDialog(
					null,
					"Il y existe plusieurs motifs avec le même libellé!",
					"Attention",
					JOptionPane.INFORMATION_MESSAGE
			);
		return ids.get(0);
	}
}
