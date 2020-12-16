package com.innatical.CustomHeads;

import org.bukkit.Server;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

class MainView extends View {
    private final HeadDatabase db;

    public MainView(HeadDatabase db, Plugin plugin, Server server) {
        super((int) (Math.ceil(HeadCategories.values().length / 9f) * 9), "HeadPicker - Main Menu", plugin, server);
        this.db = db;

        initializeItems();
    }

    private void initializeItems() {
        for (HeadCategories category : HeadCategories.values()) {
            inv.addItem(createGuiItem(category.getSymbol(), "§r" + category.getName()));
        }
    }

    @Override
    protected void onClick(InventoryClickEvent e) {
        switch (e.getRawSlot()) {
            case 0:
                new PageController(db, plugin, server, HeadCategories.ALPHABET, e.getWhoClicked());
                break;
            case 1:
                new PageController(db, plugin, server, HeadCategories.ANIMALS, e.getWhoClicked());
                break;
            case 2:
                new PageController(db, plugin, server, HeadCategories.BLOCKS, e.getWhoClicked());
                break;
            case 3:
                new PageController(db, plugin, server, HeadCategories.DECORATION, e.getWhoClicked());
                break;
            case 4:
                new PageController(db, plugin, server, HeadCategories.FOOD_DRINKS, e.getWhoClicked());
                break;
            case 5:
                new PageController(db, plugin, server, HeadCategories.HUMANS, e.getWhoClicked());
                break;
            case 6:
                new PageController(db, plugin, server, HeadCategories.HUMANOID, e.getWhoClicked());
                break;
            case 7:
                new PageController(db, plugin, server, HeadCategories.MISCELLANEOUS, e.getWhoClicked());
                break;
            case 8:
                new PageController(db, plugin, server, HeadCategories.MONSTERS, e.getWhoClicked());
                break;
            case 9:
                new PageController(db, plugin, server, HeadCategories.PLANTS, e.getWhoClicked());
                break;
        }
    }
}
