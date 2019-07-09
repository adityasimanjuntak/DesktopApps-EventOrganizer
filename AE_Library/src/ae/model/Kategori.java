/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.model;

import java.io.Serializable;

/**
 *
 * @author Aditya
 */
public class Kategori implements Serializable {

    private String id_kat;
    private String nama_kat;

    /**
     * @return the id_kat
     */
    public String getId_kat() {
        return id_kat;
    }

    /**
     * @param id_kat the id_kat to set
     */
    public void setId_kat(String id_kat) {
        this.id_kat = id_kat;
    }

    /**
     * @return the nama_kat
     */
    public String getNama_kat() {
        return nama_kat;
    }

    /**
     * @param nama_kat the nama_kat to set
     */
    public void setNama_kat(String nama_kat) {
        this.nama_kat = nama_kat;
    }

}
