/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hooks.entities;


/**
 *
 * @author asus
 */
public class participant {
    int id;
    boolean confirmation;
    String nom;
    String prenom;
    String date_inscrit;
    int evenement;

    public participant(int id, boolean confirmation, String nom, String prenom, String date_inscrit) {
        this.id = id;
        this.confirmation = confirmation;
        this.nom = nom;
        this.prenom = prenom;
        this.date_inscrit = date_inscrit;
    }

    public participant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDate_inscrit() {
        return date_inscrit;
    }

    public void setDate_inscrit(String date_inscrit) {
        this.date_inscrit = date_inscrit;
    }

    public participant(int id, boolean confirmation, String nom, String prenom, String date_inscrit, int evenement) {
        this.id = id;
        this.confirmation = confirmation;
        this.nom = nom;
        this.prenom = prenom;
        this.date_inscrit = date_inscrit;
        this.evenement = evenement;
    }

    public int getEvenement() {
        return evenement;
    }

    public void setEvenement(int evenement) {
        this.evenement = evenement;
    }

    @Override
    public String toString() {
        return "participant{" + "id=" + id + ", confirmation=" + confirmation + ", nom=" + nom + ", prenom=" + prenom + ", date_inscrit=" + date_inscrit + ", evenement=" + evenement + '}';
    }
    

 
    
    
    
    
}
