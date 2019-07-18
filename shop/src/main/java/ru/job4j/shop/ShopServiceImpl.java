package ru.job4j.shop;

import javax.jws.WebService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebService(endpointInterface = "ru.job4j.shop.ShopService")
public class ShopServiceImpl implements ShopService {
    private final Map<Integer, Item> items = new ConcurrentHashMap<Integer, Item>();
    {
        Item item = new Item();
        item.setId(1);
        item.setName("test");
        items.put(item.getId(), item);
    }

    @Override
    public Item[] list() {
        return items.values().toArray(new Item[0]);
    }

    @Override
    public Item findById(int id) {
        return null;
    }

    @Override
    public Item create(Item item) {
        return null;
    }

    @Override
    public void update(Item item) {
    }

    @Override
    public void delete(int id) {
    }
}
