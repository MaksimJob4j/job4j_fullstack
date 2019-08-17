package ru.job4j.payment.rmi;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaymentServiceImpl implements PaymentService {

    private final CopyOnWriteArrayList<Payment> payments = new CopyOnWriteArrayList<>();

    {
        Payment payment = new Payment();
        payment.setValue(new BigDecimal(100.0));
        payments.add(payment);
    }

    @Override
    public List<Payment> findAll() throws RemoteException {
        return payments;
    }

    @Override
    public Payment create(Payment payment) throws RemoteException {
        return null;
    }
}