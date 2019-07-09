/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.Peralatan;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Aditya
 */
public interface IPeralatanDao extends Remote {

    int saveDataPeralatan(Peralatan P) throws RemoteException;

    String GenerateIDPeralatan(Peralatan P) throws RemoteException;

    List<Peralatan> getAll() throws RemoteException;

    int updatePeralatan(Peralatan P) throws RemoteException;

    int deletePeralatan(Peralatan P) throws RemoteException;
}
