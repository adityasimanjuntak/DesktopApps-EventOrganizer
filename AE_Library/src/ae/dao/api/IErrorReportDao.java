/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.api;

import ae.model.ErrorReport;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Aditya
 */
public interface IErrorReportDao extends Remote {

    String GenerateIDError(ErrorReport er) throws RemoteException;

    int saveDataError(ErrorReport er) throws RemoteException;

    List<ErrorReport> getAll() throws RemoteException;

    int updateError(ErrorReport ER) throws RemoteException;
}
