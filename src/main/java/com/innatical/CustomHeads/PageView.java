package com.innatical.CustomHeads;

import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.function.Consumer;

class PageView extends View {
    final private ArrayList<ItemStack> items;
    final private Consumer<HumanEntity> previousPage;
    final private Consumer<HumanEntity> nextPage;
    final private int pageNumber;
    final private int maxPages;

    public PageView(Plugin plugin, Server server, HeadCategories category, int pageNumber, int maxPages, Consumer<HumanEntity> previousPage, Consumer<HumanEntity> nextPage, ArrayList<ItemStack> items) {
        super(54, "HeadPicker - " + category.getName() + " #" + pageNumber, plugin, server);

        this.items = items;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.pageNumber = pageNumber;
        this.maxPages = maxPages;

        initializeItems();
    }

    private void initializeItems() {
        for (ItemStack item : items) {
            inv.addItem(item);
        }

        if (pageNumber != 1) inv.setItem(45, createFancyGuiItem("§rPrevious Page", "ca6160ab-3d3a-4cd2-a4d3-a55a4f022d85", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE2ZWEzNGE2YTZlYzVjMDUxZTY5MzJmMWM0NzFiNzAxMmIyOThkMzhkMTc5ZjFiNDg3YzQxM2Y1MTk1OWNkNCJ9fX0="));
        if (pageNumber != maxPages) inv.setItem(53, createFancyGuiItem("§rNext Page", "ed45ca04-c349-4954-992b-f1983218d461", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5ZWM3MWMxMDY4ZWM2ZTAzZDJjOTI4N2Y5ZGE5MTkzNjM5ZjNhNjM1ZTJmYmQ1ZDg3YzJmYWJlNjQ5OSJ9fX0="));
    }

    @Override
    protected void onClick(InventoryClickEvent e) {
        if (e.getRawSlot() < 45) {
            e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
        } else if (e.getRawSlot() == 45 && pageNumber != 1) {
            previousPage.accept(e.getWhoClicked());
        } else if (e.getRawSlot() == 53 && pageNumber != maxPages) {
            nextPage.accept(e.getWhoClicked());
        }
    }
}
