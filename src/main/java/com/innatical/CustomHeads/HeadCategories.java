package com.innatical.CustomHeads;

import org.bukkit.Material;

enum HeadCategories {
    ALPHABET ("alphabet", "Alphabet", Material.WHITE_BANNER),
    ANIMALS ("animals", "Animals", Material.SKELETON_SPAWN_EGG),
    BLOCKS ("blocks", "Blocks", Material.BRICKS),
    DECORATION ("decoration", "Decoration", Material.PEONY),
    FOOD_DRINKS ("food-drinks", "Food/Drinks", Material.POTION),
    HUMANS ("humans", "Humans", Material.PLAYER_HEAD),
    HUMANOID ("humanoid", "Humanoid", Material.ARMOR_STAND),
    MISCELLANEOUS ("miscellaneous", "Miscellaneous", Material.LAVA_BUCKET),
    MONSTERS ("monsters", "Monsters", Material.ZOMBIE_HEAD),
    PLANTS ("plants", "Plants", Material.TALL_GRASS);

    private final String Id;
    private final String name;
    private final Material symbol;

    HeadCategories(String Id, String name, Material symbol) {
        this.Id = Id;
        this.name = name;
        this.symbol = symbol;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Material getSymbol() {
        return symbol;
    }
}
