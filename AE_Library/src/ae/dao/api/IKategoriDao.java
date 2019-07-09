/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.Kategori;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Aditya
 */
public interface IKategoriDao extends Remote {

    int saveDataKategori(Kategori K) throws RemoteException;

    int deletekategori(Kategori K) throws RemoteException;

    int updatekategori(Kategori K) throws RemoteException;

    String GenerateIDKategori(Kategori K) throws RemoteException;

    List<Kategori> getAll() throws RemoteException;

}
