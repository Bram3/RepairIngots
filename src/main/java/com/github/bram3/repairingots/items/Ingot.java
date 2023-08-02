package com.github.bram3.repairingots.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Ingot {
    private final ItemStack item;
    private final int durability;
    private final boolean percentage;
    private final Sound sound;

    public Ingot(String name, List<String> lore, Material material, int durability, boolean percentage, Sound sound) {
        item = new ItemStack(material);
        this.sound = sound;
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.durability = durability;
        this.percentage = percentage;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public void repairItem(ItemStack item) throws Exception {
        int repairDamage = durability;
        int maxDurability = item.getType().getMaxDurability();
        if (percentage) {
            repairDamage = maxDurability * durability / 100;
        }

        Damageable itemMeta = (Damageable) item.getItemMeta();
        if (itemMeta == null) {
            throw new Exception("Invalid item");
        }
        int newDamage = itemMeta.getDamage() - repairDamage;
        itemMeta.setDamage(Math.max(newDamage, 0));
        item.setItemMeta(itemMeta);
    }

    public Sound getSound() {
        return sound;
    }
}
