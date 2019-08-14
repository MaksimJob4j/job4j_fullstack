package ru.job4j.payment;

import ru.job4j.shop.Item;
import ru.job4j.shop.ShopService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ShopClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:7779/ws/shop?wsdl");
        QName qname = new QName("http://shop.job4j.ru/", "ShopServiceImplService");
        Service service = Service.create(url, qname);
        ShopService shop = service.getPort(ShopService.class);
        shop.list().getItem().forEach(i -> System.out.printf("%s - %s%n", i.getId(), i.getName()));
        Item item = new Item();
        item.setName("test_2");
        shop.create(item);
        shop.list().getItem().forEach(i -> System.out.printf("%s - %s%n", i.getId(), i.getName()));
    }
}