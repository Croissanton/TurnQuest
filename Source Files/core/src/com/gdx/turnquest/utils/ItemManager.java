package com.gdx.turnquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.entities.Item;
import java.io.IOException;

public class ItemManager {
    private static final String ITEMS_PATH = "../Data/items.json";
    private final FileHandle file = Gdx.files.local(ITEMS_PATH);
    private final Json json = new Json();
    private final ObjectMap<String, Item> itemsData;

    @SuppressWarnings("unchecked")
    public ItemManager() {
        json.setOutputType(JsonWriter.OutputType.json);
        if(file.exists()) {
            itemsData = json.fromJson(ObjectMap.class, Item.class, file.readString());
        } else {
            try {
               file.file().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            itemsData = new ObjectMap<>();
        }
    }
    public Item getItem(String name) {
        if (!itemsData.containsKey(name)) {
            throw new IllegalArgumentException("Item does not exist.");
        }
        return itemsData.get(name);
    }


    public void addItem(Item item) {
        if (itemsData.containsKey(item.getName())) {
            throw new IllegalArgumentException("Item already exists.");
        }
        itemsData.put(item.getName(), item);
        file.writeString(json.prettyPrint(itemsData), false);
    }

    public void removeItem(String name) {
        if (!itemsData.containsKey(name)) {
            throw new IllegalArgumentException("Item does not exist.");
        }
        itemsData.remove(name);
        file.writeString(json.prettyPrint(itemsData), false);
    }
}
