/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Aditya
 */
public class Ruangan implements Serializable{

    private String Ruangan_id;
    private String Ruangan_nama;
    private String Ruangan_tanggal;
    private String Ruangan_jam;
    private String Ruangan_sapras_id;
    private String Ruangan_status;
    private int Ruangan_jumlah_alat;
    private String Ruangan_id_alat;
    private String kd_daftar;

    /**
     * @return the Ruangan_id
     */
    public String getRuangan_id() {
        return Ruangan_id;
    }

    /**
     * @param Ruangan_id the Ruangan_id to set
     */
    public void setRuangan_id(String Ruangan_id) {
        this.Ruangan_id = Ruangan_id;
    }

    /**
     * @return the Ruangan_nama
     */
    public String getRuangan_nama() {
        return Ruangan_nama;
    }

    /**
     * @param Ruangan_nama the Ruangan_nama to set
     */
    public void setRuangan_nama(String Ruangan_nama) {
        this.Ruangan_nama = Ruangan_nama;
    }

    /**
     * @return the Ruangan_tanggal
     */
    public String getRuangan_tanggal() {
        return Ruangan_tanggal;
    }

    /**
     * @param Ruangan_tanggal the Ruangan_tanggal to set
     */
    public void setRuangan_tanggal(String Ruangan_tanggal) {
        this.Ruangan_tanggal = Ruangan_tanggal;
    }

    /**
     * @return the Ruangan_jam
     */
    public String getRuangan_jam() {
        return Ruangan_jam;
    }

    /**
     * @param Ruangan_jam the Ruangan_jam to set
     */
    public void setRuangan_jam(String Ruangan_jam) {
        this.Ruangan_jam = Ruangan_jam;
    }

    /**
     * @return the Ruangan_sapras_id
     */
    public String getRuangan_sapras_id() {
        return Ruangan_sapras_id;
    }

    /**
     * @param Ruangan_sapras_id the Ruangan_sapras_id to set
     */
    public void setRuangan_sapras_id(String Ruangan_sapras_id) {
        this.Ruangan_sapras_id = Ruangan_sapras_id;
    }

    /**
     * @return the Ruangan_status
     */
    public String getRuangan_status() {
        return Ruangan_status;
    }

    /**
     * @param Ruangan_status the Ruangan_status to set
     */
    public void setRuangan_status(String Ruangan_status) {
        this.Ruangan_status = Ruangan_status;
    }

    /**
     * @return the Ruangan_jumlah_alat
     */
    public int getRuangan_jumlah_alat() {
        return Ruangan_jumlah_alat;
    }

    /**
     * @param Ruangan_jumlah_alat the Ruangan_jumlah_alat to set
     */
    public void setRuangan_jumlah_alat(int Ruangan_jumlah_alat) {
        this.Ruangan_jumlah_alat = Ruangan_jumlah_alat;
    }

    /**
     * @return the Ruangan_id_alat
     */
    public String getRuangan_id_alat() {
        return Ruangan_id_alat;
    }

    /**
     * @param Ruangan_id_alat the Ruangan_id_alat to set
     */
    public void setRuangan_id_alat(String Ruangan_id_alat) {
        this.Ruangan_id_alat = Ruangan_id_alat;
    }

    /**
     * @return the kd_daftar
     */
    public String getKd_daftar() {
        return kd_daftar;
    }

    /**
     * @param kd_daftar the kd_daftar to set
     */
    public void setKd_daftar(String kd_daftar) {
        this.kd_daftar = kd_daftar;
    }

}
