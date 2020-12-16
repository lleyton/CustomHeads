package com.innatical.CustomHeads;

import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

class PageController {
    private final ArrayList<PageView> pages = new ArrayList<>();
    private int page = 0;

    public PageController(HeadDatabase db, Plugin plugin, Server server, HeadCategories category, HumanEntity player) {
        ArrayList<ArrayList<ItemStack>> chunks = chunk(db.category(category));

        int i = 1;
        for (ArrayList<ItemStack> items : chunks) {
            pages.add(new PageView(plugin, server, category, i, chunks.size(), this::previousPage, this::nextPage, items));
            i++;
        }

        pages.get(0).openInventory(player);
    }

    private void previousPage(HumanEntity player) {
        page--;
        pages.get(page).openInventory(player);
    }

    private void nextPage(HumanEntity player) {
        page++;
        pages.get(page).openInventory(player);
    }

    private ArrayList<ArrayList<ItemStack>> chunk(ArrayList<ItemStack> array) {
        ArrayList<ArrayList<ItemStack>> temp = new ArrayList<>();

        for (int i = 0; i < array.size(); i += 45) {
            temp.add(new ArrayList<>(array.subList(i, Math.min(i + 45, array.size()))));
        }

        return temp;
    }
}
