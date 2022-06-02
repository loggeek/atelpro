package com.loggeek.controleur;

import com.loggeek.controleur.util.Hash;
import com.loggeek.modele.dal.*;
import com.loggeek.modele.metier.*;
import com.loggeek.vue.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

import static com.loggeek.controleur.Extractions.getChampsPersonnels;
import static com.loggeek.modele.dal.AccesDonnees.getAbsencesPersonnel;
import static com.loggeek.modele.dal.AccesDonnees.getPersonnels;


public class Interactions
{
	/**
	 * Constructeur caché d'une classe utilitaire.
	 */
	private Interactions() {}
	
	/**
	 * Effectue une tentative de connexion.
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param responsable le responsable qui doit correspondre à un qui figure dans la base
	 */
	public static void connexion(JFrame window, Responsable responsable)
	{
		if (responsable.login().isEmpty() || responsable.motdepasse().isEmpty()) return;
		
		ArrayList<Responsable> responsables = AccesDonnees.getResponsables();
		ArrayList<String> logins = new ArrayList<>();
		
		for (Responsable resp: responsables)
			logins.add(resp.login());
		
		int index = logins.indexOf(responsable.login());
		if (index == -1)
		{
			JOptionPane.showMessageDialog(
					window,
					"Login incorrect!",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		
		if (responsables.get(index).motdepasse().equals(Hash.sha256(responsable.motdepasse())))
		{
			window.dispose();
			FenetrePrincipale.main(null);
		}
		else
		{
			JOptionPane.showMessageDialog(
					window,
					"Mot de passe incorrect!",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
			);
		}
	}
	
	/**
	 * Récupère les données des membres du personnel dans un format compatible avec une JTable.
	 *
	 * @return les données
	 */
	public static String[][] majTablePersonnels()
	{
		return getChampsPersonnels(getPersonnels()).toArray(String[][]::new);
	}
	
	/**
	 * Valide l'ajout d'un personnel.
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param personnel le membre du personnel à ajouter
	 */
	public static void validationAjoutPersonnel(JFrame window, Personnel personnel)
	{
		if (personnel.nom().isEmpty()
				|| personnel.prenom().isEmpty()
				|| personnel.tel().isEmpty()
				|| personnel.mail().isEmpty()
		){
			JOptionPane.showMessageDialog(
					window,
					"Tous les champs ne sont pas remplis!",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		if (personnel.tel().length() > 10) // Le numéro de téléphone ne doit pas dépasser 10 caractères
		{
			JOptionPane.showMessageDialog(
					window,
					"Le numéro de téléphone rentré est invalide!",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		
		AccesDonnees.ajouterPersonnel(personnel);
		FenetrePrincipale.getFenetre().majTable();
		window.dispose();
	}
	
	/**
	 * Supprime un personnel de la base de données.
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param id l'identifiant du membre du personnel à supprimer
	 */
	public static void supprimerPersonnel(JFrame window, int id)
	{
		String[] options = {"Oui", "Non"};
		int evs = JOptionPane.showOptionDialog(
				window,
				"Êtes-vous sûr de vouloir retirer ce membre du personnel de la base de données?",
				"Attention",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[1]
		);
		if (evs == JOptionPane.YES_OPTION)
		{
			AccesDonnees.supprimerPersonnel(id);
			FenetrePrincipale.getFenetre().majTable();
		}
	}
	
	/**
	 * Valide la modification d'un personnel.
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param personnel le membre du personnel à ajouter
	 */
	public static void validationModificationPersonnel(JFrame window, Personnel personnel)
	{
		if (personnel.nom().isEmpty()
				|| personnel.prenom().isEmpty()
				|| personnel.tel().isEmpty()
				|| personnel.mail().isEmpty()
		){
			JOptionPane.showMessageDialog(
					window,
					"Tous les champs ne sont pas remplis!",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		if (personnel.tel().length() > 10) // Le numéro de téléphone ne doit pas dépasser 10 caractères
		{
			JOptionPane.showMessageDialog(
					window,
					"Le numéro de téléphone rentré est invalide!",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		
		AccesDonnees.modifierPersonnel(personnel);
		FenetrePrincipale.getFenetre().majTable();
		window.dispose();
	}
	
	/**
	 * Récupère les données des absences dans un format compatible avec une JTable.
	 *
	 * @param personnel le personnel dont les absences doivent être affichées
	 * @return les données
	 */
	public static String[][] majTableAbsences(Personnel personnel)
	{
		return Extractions.getChampsAbsences(getAbsencesPersonnel(personnel.idpersonnel())).toArray(String[][]::new);
	}
	
	/**
	 * Valide l'ajout d'une absence.
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param personnel le membre du personnel concerné
	 * @param absence l'absence à rajouter
	 */
	public static void validationAjoutAbsence(JFrame window, Personnel personnel, Absence absence)
	{
		AccesDonnees.ajouterAbsence(personnel, absence);
		AffichageAbsences.getFenetre().majTable();
		window.dispose();
	}
	
	/**
	 * Valide la modification d'une absence
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param personnel le membre du personnel concerné
	 * @param absence l'absence à rajouter
	 */
	public static void validationModificationAbsence(
			JFrame window,
			Date datedebutOrig,
			Personnel personnel,
			Absence absence
	){
		AccesDonnees.modifierAbsence(personnel, datedebutOrig, absence);
		AffichageAbsences.getFenetre().majTable();
		window.dispose();
	}
	
	public static void supprimerAbsence(AffichageAbsences window, Personnel personnel, Date date)
	{
		String[] options = {"Oui", "Non"};
		int evs = JOptionPane.showOptionDialog(
				window,
				"Êtes-vous sûr de vouloir retirer cette absence base de données?",
				"Attention",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[1]
		);
		if (evs == JOptionPane.YES_OPTION)
		{
			AccesDonnees.supprimerAbsence(personnel, date);
			AffichageAbsences.getFenetre().majTable();
		}
	}
}
