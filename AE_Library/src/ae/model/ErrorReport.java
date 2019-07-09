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
public class ErrorReport implements Serializable {
    private String error_id;
    private String error_emp_id;
    private String error_text;
    private String error_state;
    private String error_title;
    private String error_pic;
    /**
     * @return the error_id
     */
    public String getError_id() {
        return error_id;
    }

    /**
     * @param error_id the error_id to set
     */
    public void setError_id(String error_id) {
        this.error_id = error_id;
    }

    /**
     * @return the error_emp_id
     */
    public String getError_emp_id() {
        return error_emp_id;
    }

    /**
     * @param error_emp_id the error_emp_id to set
     */
    public void setError_emp_id(String error_emp_id) {
        this.error_emp_id = error_emp_id;
    }

    /**
     * @return the error_text
     */
    public String getError_text() {
        return error_text;
    }

    /**
     * @param error_text the error_text to set
     */
    public void setError_text(String error_text) {
        this.error_text = error_text;
    }

    /**
     * @return the error_state
     */
    public String getError_state() {
        return error_state;
    }

    /**
     * @param error_state the error_state to set
     */
    public void setError_state(String error_state) {
        this.error_state = error_state;
    }

    /**
     * @return the error_title
     */
    public String getError_title() {
        return error_title;
    }

    /**
     * @param error_title the error_title to set
     */
    public void setError_title(String error_title) {
        this.error_title = error_title;
    }

    /**
     * @return the error_pic
     */
    public String getError_pic() {
        return error_pic;
    }

    /**
     * @param error_pic the error_pic to set
     */
    public void setError_pic(String error_pic) {
        this.error_pic = error_pic;
    }

}
