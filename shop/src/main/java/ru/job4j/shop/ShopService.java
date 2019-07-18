package ru.job4j.shop;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ShopService {
    @WebMethod
    Item[] list();

    @WebMethod
    Item findById(int id);

    @WebMethod
    Item create(Item item);

    @WebMethod
    void update(Item item);

    @WebMethod
    void delete(int id);
}