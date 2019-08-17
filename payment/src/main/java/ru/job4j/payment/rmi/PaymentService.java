package ru.job4j.payment.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PaymentService extends Remote  {
    List<Payment> findAll() throws RemoteException;
    Payment create(Payment payment) throws RemoteException;
}