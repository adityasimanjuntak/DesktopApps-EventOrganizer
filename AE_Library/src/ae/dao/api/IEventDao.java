/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.Event;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aditya
 */
public interface IEventDao extends Remote {

    String GenerateKDEvent(Event E) throws RemoteException;

    String GenerateKDTambahAlat(Event E) throws RemoteException;

    int saveDataEvent(Event E) throws RemoteException;

    int saveDataDetilTambahAlat(Event E) throws RemoteException;

    int updateRuangan(Event E) throws RemoteException;
}
