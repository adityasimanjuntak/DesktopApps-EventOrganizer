/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.Login;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aditya
 */
public interface ILoginDao extends Remote {

    int loginapk(Login log) throws RemoteException;

    int loginadm(Login log) throws RemoteException;
}
