package ru.job4j.payment.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIPaymentClient {

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        PaymentService stub = (PaymentService) registry.lookup("PaymentService");
        stub.findAll().forEach(e -> System.out.println(e.getValue()));
    }
}

