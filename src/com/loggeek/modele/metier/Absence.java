package com.loggeek.modele.metier;

/**
 * <b>Classe métier</b><br />
 * Contient les données relatives aux absences.
 */
public record Absence(java.util.Date datedebut, java.util.Date datefin, int personnel, int motif) { }
