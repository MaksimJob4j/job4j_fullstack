package ru.job4j.shop;

import javax.xml.ws.Endpoint;

public class ShopPublish {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:7779/ws/shop", new ShopServiceImpl());
    }
}