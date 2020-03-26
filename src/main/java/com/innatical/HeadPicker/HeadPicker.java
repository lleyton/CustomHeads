package com.innatical.HeadPicker;

import org.bukkit.plugin.java.JavaPlugin;

public class HeadPicker extends JavaPlugin {
    private HeadDatabase db = new HeadDatabase(this);

    @Override
    public void onEnable() {
        db.syncDatabase((error) -> {
            if (error == null) {
                getLogger().info("Loaded DB!");
            } else {
                getLogger().severe(error.getMessage());
            }
        });
        this.getCommand("heads").setExecutor(new HeadsCommand(db, this, getServer()));
    }
}

