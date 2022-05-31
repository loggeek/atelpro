package com.loggeek.controleur;

import com.loggeek.controleur.util.Hash;
import com.loggeek.modele.dal.*;
import com.loggeek.modele.metier.*;
import com.loggeek.vue.*;
import javax.swing.*;
import java.util.ArrayList;


public class Interactions
{
	/**
	 * Effectue une tentative de connexion.
	 *
	 * @param window la fenêtre d'où vient la tentative
	 * @param login le nom d'utilisateur
	 * @param motdepasse le mot de passe
	 */
	public static void connexion(JFrame window, String login, String motdepasse)
	{
		if (login.isEmpty() || motdepasse.isEmpty()) return;
		
		ArrayList<Responsable> responsables = AccesDonnees.getResponsables();
		ArrayList<String> logins = new ArrayList<>();
		
		for (Responsable responsable: responsables)
			logins.add(responsable.login());
		int index = logins.indexOf(login);
		
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
		
		if (responsables.get(index).motdepasse().equals(Hash.sha256(motdepasse)))
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
}
