package com.loggeek.modele.metier;

/**
 * <b>Classe métier</b><br />
 * Contient les données relatives aux membres du personnel.
 */
public record Personnel(int idpersonnel, String nom, String prenom, String tel, String mail, int service) { }
