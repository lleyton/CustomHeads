package com.innatical.CustomHeads;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public class CustomHeads extends JavaPlugin {
    private final HeadDatabase db = new HeadDatabase(this);

    @Override
    public void onEnable() {
        db.syncDatabase((error) -> {
            if (error == null) {
                getLogger().info("Loaded DB!");
            } else {
                getLogger().severe(error.getMessage());
            }
        });
        Objects.requireNonNull(this.getCommand("heads")).setExecutor(new HeadsCommand(db, this, getServer()));
    }

    public static int[] convertStringUUID(String uuid) {
        final UUID oldUUID = UUID.fromString(uuid);

        final long mostSignificant = oldUUID.getMostSignificantBits();
        final long leastSignificant = oldUUID.getLeastSignificantBits();

        return new int[]{(int) (mostSignificant >> 32), (int) mostSignificant, (int) (leastSignificant >> 32), (int) leastSignificant};
    }
}

