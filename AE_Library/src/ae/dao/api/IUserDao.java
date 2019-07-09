/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.Login;
import ae.model.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aditya
 */
public interface IUserDao extends Remote {

    int saveDataEmp(User U) throws RemoteException;

    int saveDataEmpLogin(Login L) throws RemoteException;
}
