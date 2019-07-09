/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.Ruangan;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Aditya
 */
public interface IRuanganDao extends Remote {

    String GenerateIDRuangan(Ruangan R) throws RemoteException;

    String GenerateIDSarpras(Ruangan R) throws RemoteException;

    public String GenerateKDRuangan(Ruangan R) throws RemoteException;

    int saveDataRuanganInti(Ruangan R) throws RemoteException;

    int saveDataDetilRuangan(Ruangan R) throws RemoteException;

    int saveDataDetilSapras(Ruangan R) throws RemoteException;

    List<Ruangan> getAll() throws RemoteException;

    List<Ruangan> getAllRuangan() throws RemoteException;

    List<Ruangan> getAllSarpras() throws RemoteException;

    List<Ruangan> getByKDSarpras(String kd_sarpras) throws RemoteException;

    int updateRuangan(Ruangan R) throws RemoteException;

    int updateDetilRuangan(Ruangan R) throws RemoteException;

    int updateDetilSapras(Ruangan R) throws RemoteException;

    int deleteSapras(Ruangan R) throws RemoteException;

    int deleteDetilRuangan(Ruangan R) throws RemoteException;

    int deleteRuangan(Ruangan R) throws RemoteException;

}
