package ae.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;

/**
 *
 * @author Aditya
 */
public class EventOrganizer implements Serializable {
    private String eo_id;
    private String eo_nama;
    private String eo_nama_pic;
    private String eo_instansi;
    private String eo_alamat;
    private String eo_ponsel;
    private String eo_email;
    private String eo_website;
    private byte[] eo_qr;
    /**
     * @return the eo_id
     */
    public String getEo_id() {
        return eo_id;
    }

    /**
     * @param eo_id the eo_id to set
     */
    public void setEo_id(String eo_id) {
        this.eo_id = eo_id;
    }

    /**
     * @return the eo_nama
     */
    public String getEo_nama() {
        return eo_nama;
    }

    /**
     * @param eo_nama the eo_nama to set
     */
    public void setEo_nama(String eo_nama) {
        this.eo_nama = eo_nama;
    }

    /**
     * @return the eo_instansi
     */
    public String getEo_instansi() {
        return eo_instansi;
    }

    /**
     * @param eo_instansi the eo_instansi to set
     */
    public void setEo_instansi(String eo_instansi) {
        this.eo_instansi = eo_instansi;
    }

    /**
     * @return the eo_alamat
     */
    public String getEo_alamat() {
        return eo_alamat;
    }

    /**
     * @param eo_alamat the eo_alamat to set
     */
    public void setEo_alamat(String eo_alamat) {
        this.eo_alamat = eo_alamat;
    }

    /**
     * @return the eo_ponsel
     */
    public String getEo_ponsel() {
        return eo_ponsel;
    }

    /**
     * @param eo_ponsel the eo_ponsel to set
     */
    public void setEo_ponsel(String eo_ponsel) {
        this.eo_ponsel = eo_ponsel;
    }

    /**
     * @return the eo_email
     */
    public String getEo_email() {
        return eo_email;
    }

    /**
     * @param eo_email the eo_email to set
     */
    public void setEo_email(String eo_email) {
        this.eo_email = eo_email;
    }

    /**
     * @return the eo_website
     */
    public String getEo_website() {
        return eo_website;
    }

    /**
     * @param eo_website the eo_website to set
     */
    public void setEo_website(String eo_website) {
        this.eo_website = eo_website;
    }

    /**
     * @return the eo_nama_pic
     */
    public String getEo_nama_pic() {
        return eo_nama_pic;
    }

    /**
     * @param eo_nama_pic the eo_nama_pic to set
     */
    public void setEo_nama_pic(String eo_nama_pic) {
        this.eo_nama_pic = eo_nama_pic;
    }

    /**
     * @return the eo_qr
     */
    public byte[] getEo_qr() {
        return eo_qr;
    }

    /**
     * @param eo_qr the eo_qr to set
     */
    public void setEo_qr(byte[] eo_qr) {
        this.eo_qr = eo_qr;
    }


}
