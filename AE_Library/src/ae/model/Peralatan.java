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
public class Peralatan implements Serializable {

    private String peralatan_id;
    private String peralatan_nama;
    private int peralatan_jumlah;

    /**
     * @return the peralatan_id
     */
    public String getPeralatan_id() {
        return peralatan_id;
    }

    /**
     * @param peralatan_id the peralatan_id to set
     */
    public void setPeralatan_id(String peralatan_id) {
        this.peralatan_id = peralatan_id;
    }

    /**
     * @return the peralatan_nama
     */
    public String getPeralatan_nama() {
        return peralatan_nama;
    }

    /**
     * @param peralatan_nama the peralatan_nama to set
     */
    public void setPeralatan_nama(String peralatan_nama) {
        this.peralatan_nama = peralatan_nama;
    }

    /**
     * @return the peralatan_jumlah
     */
    public int getPeralatan_jumlah() {
        return peralatan_jumlah;
    }

    /**
     * @param peralatan_jumlah the peralatan_jumlah to set
     */
    public void setPeralatan_jumlah(int peralatan_jumlah) {
        this.peralatan_jumlah = peralatan_jumlah;
    }



}
