package com.loggeek.modele.connexion;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;


/**
 * <b>Classe singleton</b><br />
 * Contient des méthodes pour la connexion SQL.
 */
public class ConnexionBDD
{
	private static ConnexionBDD instance = null;
	private Connection connexion = null;
	private ResultSet resultSet = null;
	
	/**
	 * Crée une connexion à la base de données, si il n'en existe pas déjà.
	 *
	 * @param url l'addresse de la base de données
	 * @param login le nom de l'utilisateur
	 * @param pwd le mot de passe
	 */
	private ConnexionBDD(String url, String login, String pwd)
	{
		if (connexion == null)
		{
			try
			{
				connexion = DriverManager.getConnection(url, login, pwd);
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(
						null,
						e.getLocalizedMessage(),
						"Erreur fatale",
						JOptionPane.ERROR_MESSAGE
				);
				System.exit(-1);
			}
		}
	}
	
	/**
	 * Récupère l'instance unique de la classe.
	 *
	 * @param url l'addresse de la base de données
	 * @param login le nom de l'utilisateur
	 * @param pwd le mot de passe
	 * @return l'instance unique de la classe
	 */
	public static ConnexionBDD getInstance(String url, String login, String pwd)
	{
		if (instance == null) instance = new ConnexionBDD(url, login, pwd);
		return instance;
	}
	
	/**
	 * Exécute une requête ajoutant des données dans la base.
	 *
	 * @param req la requête SQL
	 * @param params les données à mettre dans la base
	 */
	public void reqUpdate(String req, ArrayList<Object> params)
	{
		if (connexion != null)
		{
			try
			{
				PreparedStatement statement = connexion.prepareStatement(req);
				if (params != null)
				{
					int k = 1;
					for (Object param: params) statement.setObject(k++, param);
				}
				statement.executeUpdate();
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(
						null,
						e.getLocalizedMessage(),
						"Erreur",
						JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	/**
	 * Effectue une requête permettant l'obtention de données dans la base.
	 * Cette fonction permet aussi la modification de celle-ci.
	 *
	 * @param req la requête SQL
	 * @param params les données à mettre dans la base
	 */
	public void reqSelect(String req, ArrayList<Object> params)
	{
		if (connexion != null)
		{
			try
			{
				PreparedStatement statement = connexion.prepareStatement(req);
				if (params != null)
				{
					int k = 1;
					for (Object param : params) statement.setObject(k++, param);
				}
				resultSet = statement.executeQuery();
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(
						null,
						e.getLocalizedMessage(),
						"Erreur",
						JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	/**
	 * Déplace le curseur d'une ligne.
	 *
	 * @return faux si il y a une erreur ou si il ne reste plus de données, vrai sinon
	 */
	public boolean readLine()
	{
		if (resultSet == null) return false;
		try
		{
			return resultSet.next();
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(
					null,
					e.getLocalizedMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
	}
	
	/**
	 * Récupère les données vers lesquelles le curseur est en train de pointer.
	 *
	 * @param field le champ concerné
	 * @return les données associées à ce champ
	 */
	public Object getField(String field)
	{
		if (resultSet == null) return null;
		try
		{
			return resultSet.getObject(field);
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(
					null,
					e.getLocalizedMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
			);
			return null;
		}
	}
	
	/**
	 * Ferme la connexion vers la base de données.
	 */
	public void close()
	{
		if (resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(
						null,
						e.getLocalizedMessage(),
						"Erreur",
						JOptionPane.ERROR_MESSAGE
				);
				resultSet = null;
			}
		}
	}
}
