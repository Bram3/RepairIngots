package com.github.bram3.repairingots.managers;

import com.github.bram3.repairingots.RepairIngots;
import com.github.bram3.repairingots.config.Config;
import com.github.bram3.repairingots.items.Ingot;
import com.github.justadeni.HexColorLib;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngotManager {
    private final Config config;
    private final RepairIngots repairIngots;

    private final HashMap<String, Ingot> ingots = new HashMap<>();

    public IngotManager(Config config, RepairIngots repairIngots) {
        this.config = config;
        this.repairIngots = repairIngots;

        Section section = config.configDocument.getSection("ingots");
        for (Object key : section.getKeys()) {
            List<String> lore = new ArrayList<>();
            for (String item : section.getStringList(key + ".lore")) {
                lore.add(HexColorLib.INSTANCE.color(item));
            }

            Ingot ingot = new Ingot(HexColorLib.INSTANCE.color(section.getString(key + ".name")),
                    lore,
                    Material.getMaterial(section.getString(key + ".item")),
                    section.getInt(key + ".durability"),
                    section.getBoolean(key + ".percentage"));
            ingots.put(key.toString(), ingot);
        }
    }

    public List<String> getIngotNames() {
        return new ArrayList<>(ingots.keySet());
    }

    public Ingot getIngot(String name) {
        return ingots.get(name);
    }

    public boolean isIngot(ItemStack item) {
        for (String ingotKey : ingots.keySet()) {
            Ingot ingot = ingots.get(ingotKey);
            if (ingot.getItemStack().isSimilar(item)) {
                return true;
            }
        }
        return false;
    }

    public Ingot getIngot(ItemStack item) {
        for (String ingotKey : ingots.keySet()) {
            Ingot ingot = ingots.get(ingotKey);
            if (ingot.getItemStack().isSimilar(item)) {
                return ingot;
            }
        }
        return null;
    }


}

