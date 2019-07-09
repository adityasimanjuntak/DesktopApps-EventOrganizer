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
public class User implements Serializable {

    private String emp_id, emp_name, emp_gender, emp_noponsel, emp_email;
    private byte[] emp_qrcode, emp_foto;

    /**
     * @return the emp_id
     */
    public String getEmp_id() {
        return emp_id;
    }

    /**
     * @param emp_id the emp_id to set
     */
    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    /**
     * @return the emp_name
     */
    public String getEmp_name() {
        return emp_name;
    }

    /**
     * @param emp_name the emp_name to set
     */
    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    /**
     * @return the emp_gender
     */
    public String getEmp_gender() {
        return emp_gender;
    }

    /**
     * @param emp_gender the emp_gender to set
     */
    public void setEmp_gender(String emp_gender) {
        this.emp_gender = emp_gender;
    }

    /**
     * @return the emp_noponsel
     */
    public String getEmp_noponsel() {
        return emp_noponsel;
    }

    /**
     * @param emp_noponsel the emp_noponsel to set
     */
    public void setEmp_noponsel(String emp_noponsel) {
        this.emp_noponsel = emp_noponsel;
    }

    /**
     * @return the emp_email
     */
    public String getEmp_email() {
        return emp_email;
    }

    /**
     * @param emp_email the emp_email to set
     */
    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    /**
     * @return the emp_qrcode
     */
    public byte[] getEmp_qrcode() {
        return emp_qrcode;
    }

    /**
     * @param emp_qrcode the emp_qrcode to set
     */
    public void setEmp_qrcode(byte[] emp_qrcode) {
        this.emp_qrcode = emp_qrcode;
    }

    /**
     * @return the emp_foto
     */
    public byte[] getEmp_foto() {
        return emp_foto;
    }

    /**
     * @param emp_foto the emp_foto to set
     */
    public void setEmp_foto(byte[] emp_foto) {
        this.emp_foto = emp_foto;
    }

}
