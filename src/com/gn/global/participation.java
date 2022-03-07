/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.global;

/**
 *
 * @author Asma Srairi
 */
public class participation {
    private int id_part;
    private int id_event;
    private String personne_part;
    private int frais_part;
    private String type_part;

    public int getId_part() {
        return id_part;
    }

    public int getId_event() {
        return id_event;
    }

    public String getPersonne_part() {
        return personne_part;
    }

    public int getFrais_part() {
        return frais_part;
    }

    public String getType_part() {
        return type_part;
    }

    public participation(int id_part, int id_event, String personne_part, int frais_part, String type_part) {
        this.id_part = id_part;
        this.id_event = id_event;
        this.personne_part = personne_part;
        this.frais_part = frais_part;
        this.type_part = type_part;
    }

}
