package com.loggeek.controleur.util;

import java.nio.charset.StandardCharsets;
import java.security.*;


/**
 * <b>Classe utilitaire</b><br />
 * Contient des méthodes pour hacher des valeurs sensibles, comme des mots de passe.
 */
public class Hash
{
	/**
	 * Constructeur caché d'une classe utilitaire.
	 */
	private Hash() {}
	
	/**
	 * Hache une chaîne en utilisant l'algorithme SHA-256.
	 *
	 * @param str la chaîne à hacher
	 * @return le haché
	 */
	public static String sha256(String str)
	{
		try
		{
			return bytesToHex(MessageDigest.getInstance("SHA-256").digest(str.getBytes(StandardCharsets.UTF_8)));
		}
		catch (NoSuchAlgorithmException e)
		{
			// Ne devrait jamais arriver
			return null;
		}
	}
	
	/**
	 * Convertit des bytes provenant
	 *
	 * @param hash le hash
	 * @return la chaîne correspondante
	 */
	private static String bytesToHex(byte[] hash)
	{
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (byte b: hash)
		{
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
