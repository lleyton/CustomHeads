package com.innatical.HeadPicker;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

abstract class View implements InventoryHolder, Listener {
    protected final Inventory inv;
    protected final Plugin plugin;
    protected final Server server;

    public View(int size, String title, Plugin plugin, Server server) {
        inv = Bukkit.createInventory(this, size, title);
        this.plugin = plugin;
        this.server = server;
    }

    protected ItemStack createGuiItem(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<>();

        Collections.addAll(metaLore, lore);

        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack createFancyGuiItem(String name, String uuid, String texture) {
        final NBTItem item = new NBTItem(new ItemStack(Material.PLAYER_HEAD, 1));
        item.addCompound("display").setString("Name", "{\"text\":\"" + name + "\"}");

        final NBTCompound skull = item.addCompound("SkullOwner");
        skull.setString("Id", uuid);
        skull.addCompound("Properties").getCompoundList("textures").addCompound().setString("Value",  texture);

        return item.getItem();
    }

    public void openInventory(HumanEntity p) {
        server.getPluginManager().registerEvents(this, plugin);
        p.openInventory(inv);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){ // Uhh, so this seems redundant but this was from a snippet from the Spigot wiki, so I'm going to assume its needed.
            e.setCancelled(true);
        }
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR || !inv.contains(clickedItem)) return;

        onClick(e);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }

        HandlerList.unregisterAll(this);
    }

    abstract protected void onClick(InventoryClickEvent e);
}
