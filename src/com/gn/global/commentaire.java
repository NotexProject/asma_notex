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
public class commentaire {
    private int idc;
    private String personne;
    private String contenue;
    private Date date_de_creation;

    public commentaire(int idc, String personne, String contenue, Date date_de_creation) {
        this.idc = idc;
        this.personne = personne;
        this.contenue = contenue;
        this.date_de_creation = date_de_creation;
    }

    public int getIdc() {
        return idc;
    }

    public String getPersonne() {
        return personne;
    }

    public String getContenue() {
        return contenue;
    }

    public Date getDate_de_creation() {
        return date_de_creation;
    }
    
}
