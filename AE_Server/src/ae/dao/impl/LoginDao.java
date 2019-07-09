/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.ILoginDao;
import ae.model.Login;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aditya
 */
public class LoginDao extends UnicastRemoteObject implements ILoginDao{

    private Connection conn = null;
    private String strSql = "";

    public LoginDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }
    //Method untuk Login Ke Dalam Aplikasi
    @Override
    public int loginapk(Login log) throws RemoteException {
        int result = 0;
        System.out.println("Method Login Aplikasi diakses secara remote");
        strSql = "Select * From App_Login where app_username = '" + log.getUsername() + "'";
        String Username, Password;
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            // set nilai parameter yg akan dikirim ke sql
            if(rs.next()) {
                Username = rs.getString("app_username");
                Password = rs.getString("app_password");
                int hmm;
                if (log.getUsername().equals(Username) && log.getPassword().equals(Password)) {
                    result = 1;
                }
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    //Method Untuk Login Administrator
    @Override
    public int loginadm(Login log) throws RemoteException {
        int result = 0;
        System.out.println("Method Login Administrator diakses secara remote");
        strSql = "Select * From User_Login where U_id = '" + log.getUsername() + "'";
        String Username, Password;
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            // set nilai parameter yg akan dikirim ke sql
            while (rs.next()) {
                Username = rs.getString("U_id");
                Password = rs.getString("U_pwd");
                int hmm;
                if (log.getUsername().equals(Username) && log.getPassword().equals(Password)) {
                    result = 1;
                }
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
