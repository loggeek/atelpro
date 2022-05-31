package com.loggeek.modele.connexion;

import java.sql.*;
import java.util.ArrayList;


public class ConnexionBDD
{
	private static ConnexionBDD instance = null;
	private Connection connexion = null;
	private ResultSet resultSet = null;
	
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
				System.err.println("Erreur fatale: " + e.getMessage());
				System.exit(-1);
			}
		}
	}
	
	public static ConnexionBDD getInstance(String url, String login, String pwd)
	{
		if (instance == null) instance = new ConnexionBDD(url, login, pwd);
		return instance;
	}
	
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
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void reqSelect(String req, ArrayList<Object> params)
	{
		if (connexion != null)
		{
			try
			{
				PreparedStatement statement = connexion.prepareStatement(req);
				if(params != null)
				{
					int k = 1;
					for (Object param : params) statement.setObject(k++, param);
				}
				resultSet = statement.executeQuery();
			}
			catch (SQLException e)
			{
				System.err.println(e.getMessage());
			}
		}
	}
	
	public boolean readLine()
	{
		if (resultSet == null) return false;
		try
		{
			return resultSet.next();
		}
		catch (SQLException e)
		{
			return false;
		}
	}
	
	public Object getField(String field)
	{
		if (resultSet == null) return null;
		try
		{
			return resultSet.getObject(field);
		}
		catch (SQLException e)
		{
			return null;
		}
	}
	
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
				resultSet = null;
			}
		}
	}
}
