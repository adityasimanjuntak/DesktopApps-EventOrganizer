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
public class Event implements Serializable {

    private String Event_id;
    private String Event_nama;
    private String Event_tanggal;
    private String EO_id;
    private String Event_kategori;
    private String Event_ruangan;
    private String Tambah_alat;
    private String id_alat;
    private int kuantitas;

    /**
     * @return the Event_id
     */
    public String getEvent_id() {
        return Event_id;
    }

    /**
     * @param Event_id the Event_id to set
     */
    public void setEvent_id(String Event_id) {
        this.Event_id = Event_id;
    }

    /**
     * @return the Event_nama
     */
    public String getEvent_nama() {
        return Event_nama;
    }

    /**
     * @param Event_nama the Event_nama to set
     */
    public void setEvent_nama(String Event_nama) {
        this.Event_nama = Event_nama;
    }

    /**
     * @return the Event_tanggal
     */
    public String getEvent_tanggal() {
        return Event_tanggal;
    }

    /**
     * @param Event_tanggal the Event_tanggal to set
     */
    public void setEvent_tanggal(String Event_tanggal) {
        this.Event_tanggal = Event_tanggal;
    }

    /**
     * @return the EO_id
     */
    public String getEO_id() {
        return EO_id;
    }

    /**
     * @param EO_id the EO_id to set
     */
    public void setEO_id(String EO_id) {
        this.EO_id = EO_id;
    }

    /**
     * @return the Event_kategori
     */
    public String getEvent_kategori() {
        return Event_kategori;
    }

    /**
     * @param Event_kategori the Event_kategori to set
     */
    public void setEvent_kategori(String Event_kategori) {
        this.Event_kategori = Event_kategori;
    }

    /**
     * @return the Event_ruangan
     */
    public String getEvent_ruangan() {
        return Event_ruangan;
    }

    /**
     * @param Event_ruangan the Event_ruangan to set
     */
    public void setEvent_ruangan(String Event_ruangan) {
        this.Event_ruangan = Event_ruangan;
    }

    /**
     * @return the Tambah_alat
     */
    public String getTambah_alat() {
        return Tambah_alat;
    }

    /**
     * @param Tambah_alat the Tambah_alat to set
     */
    public void setTambah_alat(String Tambah_alat) {
        this.Tambah_alat = Tambah_alat;
    }

    /**
     * @return the id_alat
     */
    public String getId_alat() {
        return id_alat;
    }

    /**
     * @param id_alat the id_alat to set
     */
    public void setId_alat(String id_alat) {
        this.id_alat = id_alat;
    }

    /**
     * @return the kuantitas
     */
    public int getKuantitas() {
        return kuantitas;
    }

    /**
     * @param kuantitas the kuantitas to set
     */
    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

}
