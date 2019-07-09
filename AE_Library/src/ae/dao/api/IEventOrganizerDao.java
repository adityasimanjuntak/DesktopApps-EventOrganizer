package ae.dao.api;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import ae.model.EventOrganizer;

/**
 *
 * @author Aditya
 */
public interface IEventOrganizerDao extends Remote {

    int saveDataEo(EventOrganizer eo) throws RemoteException;

    int update(EventOrganizer eo) throws RemoteException;

    int delete(EventOrganizer eo) throws RemoteException;

    String GenerateIDEO(EventOrganizer eo) throws RemoteException;

    List<EventOrganizer> getAll() throws RemoteException;

    List<EventOrganizer> getByName(String namaEo) throws RemoteException;

}
