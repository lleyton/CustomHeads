package com.innatical.HeadPicker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class HeadDatabase {
    final private HashMap<HeadCategories, ArrayList<ItemStack>> heads = new HashMap<>();
    final private Plugin plugin;
    private boolean ready = false;

    public HeadDatabase(Plugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<ItemStack> category(HeadCategories category) {
        return heads.get(category);
    }

    public void syncDatabase(Consumer<IOException> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final HashMap<HeadCategories, JsonArray> response = new HashMap<>();

            try {
                for (HeadCategories category : HeadCategories.values()) {
                    final InputStream stream = new URL("https://minecraft-heads.com/scripts/api.php?cat=" + category.getId()).openStream();
                    response.put(category, new JsonParser().parse(new InputStreamReader(stream)).getAsJsonArray());
                }
            } catch (IOException e) {
                callback.accept(e);
                return;
            }

            Bukkit.getScheduler().runTask(plugin, () -> {
                for (Map.Entry<HeadCategories, JsonArray> entry : response.entrySet()) {
                    final ArrayList<ItemStack> items = new ArrayList<>();

                    for (JsonElement element : entry.getValue()) {
                        final JsonObject object = element.getAsJsonObject();

                        final NBTItem item = new NBTItem(new ItemStack(Material.PLAYER_HEAD, 1));
                        item.addCompound("display").setString("Name", "{\"text\":\"§r" + object.get("name").getAsString() + "\"}");

                        final NBTCompound skull = item.addCompound("SkullOwner");
                        skull.setString("Id", object.get("uuid").getAsString());
                        skull.addCompound("Properties").getCompoundList("textures").addCompound().setString("Value",  object.get("value").getAsString());

                        items.add(item.getItem());
                    }

                    heads.put(entry.getKey(), items);
                }
                ready = true;
                callback.accept(null);
            });
        });
    }

    public boolean isReady() {
        return ready;
    }
}
