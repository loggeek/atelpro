package com.loggeek.modele.dal;

import com.loggeek.modele.connexion.ConnexionBDD;
import com.loggeek.modele.metier.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * <b>Classe utilitaire</b><br />
 * Contient des méthodes pour récupérer et/ou modifier les données.
 */
public class AccesDonnees
{
	private final static String url = "jdbc:mysql://localhost/mediatek86";
	private final static String login = "dbuser";
	private final static String pwd = "motdepasse";
	
	/**
	 * Constructeur caché d'une classe utilitaire.
	 */
	private AccesDonnees() {}
	
	/**
	 * Récupère les responsables.
	 *
	 * @return la liste des responsables.
	 */
	public static ArrayList<Responsable> getResponsables()
	{
		String sql = "SELECT login, motdepasse FROM responsable";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Responsable> responsables = new ArrayList<>();
		
		connexion.reqSelect(sql, null);
		
		while (connexion.readLine())
		{
			responsables.add(new Responsable(
					(String) connexion.getField("login"),
					(String) connexion.getField("motdepasse")
			));
		}
		connexion.close();
		
		return responsables;
	}
	
	/**
	 * Récupère les personnels.
	 *
	 * @return la liste des membres du personnel.
	 */
	public static ArrayList<Personnel> getPersonnels()
	{
		String sql =
			"SELECT idpersonnel, personnel.nom, prenom, tel, mail, idservice, service.nom AS nomservice " +
			"FROM personnel " +
			"JOIN service ON idservice = service";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Personnel> personnels = new ArrayList<>();
		
		connexion.reqSelect(sql, null);
		
		while (connexion.readLine())
		{
			personnels.add(new Personnel(
					(int) connexion.getField("idpersonnel"),
					(String) connexion.getField("nom"),
					(String) connexion.getField("prenom"),
					(String) connexion.getField("tel"),
					(String) connexion.getField("mail"),
					new Service(
							(int) connexion.getField("idservice"),
							connexion.getField("nomservice").toString()
					)
			));
		}
		connexion.close();
		
		return personnels;
	}
	
	/**
	 * Récupère la liste des identifiants des membres du personnel.
	 *
	 * @return la liste des identifiants des membres du personnel.
	 */
	public static ArrayList<Integer> getPersonnelIDs()
	{
		String sql = "SELECT idpersonnel FROM personnel";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Integer> ids = new ArrayList<>();
		
		connexion.reqSelect(sql, null);
		
		while (connexion.readLine())
		{
			ids.add((Integer) connexion.getField("idpersonnel"));
		}
		connexion.close();
		
		return ids;
	}
	
	/**
	 * Récupère la liste des services.
	 *
	 * @return la liste des services
	 */
	public static ArrayList<Service> getServices()
	{
		String sql = "SELECT idservice, nom FROM service";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Service> services = new ArrayList<>();
		
		connexion.reqSelect(sql, null);
		
		while (connexion.readLine())
		{
			services.add(new Service(
					(int) connexion.getField("idservice"),
					(String) connexion.getField("nom")
			));
		}
		connexion.close();
		
		return services;
	}
	
	/**
	 * Ajoute un personnel.
	 *
	 * @param personnel le membre du personnel à ajouter
	 */
	public static void ajouterPersonnel(Personnel personnel)
	{
		String sql = "INSERT INTO personnel (idpersonnel, nom, prenom, tel, mail, service) VALUES (?, ?, ?, ?, ?, ?)";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Object> params = new ArrayList<>();
		
		params.add(personnel.idpersonnel());
		params.add(personnel.nom());
		params.add(personnel.prenom());
		params.add(personnel.tel());
		params.add(personnel.mail());
		params.add(personnel.service().idservice());
		
		connexion.reqUpdate(sql, params);
	}
	
	/**
	 * Obtient les identifiants du service à partir de son nom.
	 *
	 * @param service le nom du service
	 * @return les identifiants de celui-ci
	 */
	public static ArrayList<Integer> getServiceIDs(String service)
	{
		String sql = "SELECT idservice, nom FROM service WHERE nom = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Object> params = new ArrayList<>();
		
		params.add(service);
		connexion.reqSelect(sql, params);
		
		while (connexion.readLine())
		{
			ids.add((Integer) connexion.getField("idservice"));
		}
		connexion.close();
		
		return ids;
	}
	
	/**
	 * Supprime un personnel de la base de données, ainsi que ses absences.
	 *
	 * @param id l'identifiant du personnel
	 */
	public static void supprimerPersonnel(int id)
	{
		// Suppression des absences
		
		String sql = "DELETE FROM absence WHERE personnel = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Object> params = new ArrayList<>();
		
		params.add(id);
		
		connexion.reqUpdate(sql, params);
		
		// Suppression du personnel
		
		sql = "DELETE FROM personnel WHERE idpersonnel = ?";
		
		connexion = ConnexionBDD.getInstance(url, login, pwd);
		params = new ArrayList<>();
		
		params.add(id);
		
		connexion.reqUpdate(sql, params);
	}
	
	/**
	 * Modifie un personnel.
	 *
	 * @param personnel le personnel
	 */
	public static void modifierPersonnel(Personnel personnel)
	{
		String sql =
				"UPDATE personnel " +
				"SET nom = ?, prenom = ?, tel = ?, mail = ?, service = ? " +
				"WHERE idpersonnel = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Object> params = new ArrayList<>();
		
		params.add(personnel.nom());
		params.add(personnel.prenom());
		params.add(personnel.tel());
		params.add(personnel.mail());
		params.add(personnel.service().idservice());
		params.add(personnel.idpersonnel());
		
		connexion.reqUpdate(sql, params);
	}
	
	
	/**
	 * Obtient les absences du personnel indiqué.
	 *
	 * <b> ATTENTION: le personnel figurant dans l'absence est nul, donc il ne faut pas l'exploiter! </b>
	 *
	 * @param id l'identifiant du personnel
	 * @return les absences du personnel
	 */
	public static ArrayList<Absence> getAbsencesPersonnel(int id)
	{
		String sql =
				"SELECT datedebut, datefin, idmotif, libelle " +
				"FROM absence " +
				"JOIN motif ON idmotif = motif " +
				"WHERE personnel = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Absence> absences = new ArrayList<>();
		ArrayList<Object> params = new ArrayList<>();
		
		SimpleDateFormat dateFmtSql = new SimpleDateFormat("yyyy-MM-dd");
		
		params.add(id);
		connexion.reqSelect(sql, params);
		
		while (connexion.readLine())
		{
			try
			{
				absences.add(new Absence(
						dateFmtSql.parse(connexion.getField("datedebut").toString()),
						dateFmtSql.parse(connexion.getField("datefin").toString()),
						null, // À implémenter en cas de besoin
						new Motif(
								(int) connexion.getField("idmotif"),
								(String) connexion.getField("libelle")
						)
				));
			}
			catch (ParseException e)
			{
				// Cette exception ne devrait jamais arriver
			}
		}
		connexion.close();
		
		return absences;
	}
	
	/**
	 * Récupère la liste des motifs.
	 *
	 * @return la liste des motifs
	 */
	public static ArrayList<Motif> getMotifs()
	{
		String sql = "SELECT idmotif, libelle FROM motif";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Motif> motifs = new ArrayList<>();
		
		connexion.reqSelect(sql, null);
		
		while (connexion.readLine())
		{
			motifs.add(new Motif(
					(int) connexion.getField("idmotif"),
					(String) connexion.getField("libelle")
			));
		}
		connexion.close();
		
		return motifs;
	}
	
	/**
	 * Obtient les identifiants du motif à partir de son libellé.
	 *
	 * @param motif le libellé du motif
	 * @return les identifiants de celui-ci
	 */
	public static ArrayList<Integer> getMotifIDs(String motif)
	{
		String sql = "SELECT idmotif FROM motif WHERE libelle = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Object> params = new ArrayList<>();
		
		params.add(motif);
		connexion.reqSelect(sql, params);
		
		while (connexion.readLine())
		{
			ids.add((Integer) connexion.getField("idmotif"));
		}
		connexion.close();
		
		return ids;
	}
	
	public static void ajouterAbsence(Personnel personnel, Absence absence)
	{
		String sql = "INSERT INTO absence (datedebut, datefin, personnel, motif) VALUES (?, ?, ?, ?)";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Object> params = new ArrayList<>();
		
		SimpleDateFormat dateFmtSql = new SimpleDateFormat("yyyy-MM-dd");
		
		params.add(dateFmtSql.format(absence.datedebut()));
		params.add(dateFmtSql.format(absence.datefin()));
		params.add(personnel.idpersonnel());
		params.add(absence.motif().idmotif());
		
		System.out.println(params);
		
		connexion.reqUpdate(sql, params);
	}
	
	public static void supprimerAbsence(Personnel personnel, Date date)
	{
		String sql = "DELETE FROM absence WHERE datedebut = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Object> params = new ArrayList<>();
		
		SimpleDateFormat dateFmtSql = new SimpleDateFormat("yyyy-MM-dd");
		
		params.add(dateFmtSql.format(date));
		
		connexion.reqUpdate(sql, params);
	}
	
	public static void modifierAbsence(Personnel personnel, Date datedebutOrig, Absence absence)
	{
		String sql =
				"UPDATE absence " +
				"SET datedebut = ?, datefin = ?, personnel = ?, motif = ? " +
				"WHERE datedebut = ?";
		
		ConnexionBDD connexion = ConnexionBDD.getInstance(url, login, pwd);
		ArrayList<Object> params = new ArrayList<>();
		
		SimpleDateFormat dateFmtSql = new SimpleDateFormat("yyyy-MM-dd");
		
		params.add(dateFmtSql.format(absence.datedebut()));
		params.add(dateFmtSql.format(absence.datefin()));
		params.add(personnel.idpersonnel());
		params.add(absence.motif().idmotif());
		params.add(dateFmtSql.format(datedebutOrig));
		
		connexion.reqUpdate(sql, params);
	}
}
