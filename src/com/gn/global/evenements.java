/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.global;
import java.sql.Date;

/**
 *
 * @author Asma Srairi
 */
public class evenements {

    private int id_event;
    private String nom_responsable;
    private String nom_event;
    private Date date_event;
    private String category_event;
    private String localisation_event;
    private int nbr_places;
    private int total_frais;
    

    public evenements(int id_event, String nom_responsable, String nom_event, Date date_event, String category_event, String localisation_event, int nbr_places, int total_frais) {
        this.id_event = id_event;
        this.nom_responsable = nom_responsable;
        this.nom_event = nom_event;
        this.date_event = date_event;
        this.category_event = category_event;
        this.localisation_event = localisation_event;
        this.nbr_places = nbr_places;
        this.total_frais = total_frais;
        
    }

    public int getId_event() {
        return id_event;
    }

    public String getNom_responsable() {
        return nom_responsable;
    }

    public String getNom_event() {
        return nom_event;
    }

    public Date getDate_event() {
        return date_event;
    }

    public String getCategory_event() {
        return category_event;
    }

    public String getLocalisation_event() {
        return localisation_event;
    }

    public int getNbr_places() {
        return nbr_places;
    }

    public int getTotal_frais() {
        return total_frais;
    }



   
}
