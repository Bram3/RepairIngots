package com.github.bram3.repairingots.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Ingot {
    private ItemStack item;
    private String name;
    private int durability;
    private boolean percentage;

    public Ingot(String name, List<String> lore, Material material, int durability, boolean percentage) {
        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.durability = durability;
        this.percentage = percentage;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public ItemStack repairItem(ItemStack item) {
        int repairDamage = durability;
        int maxDurability = item.getType().getMaxDurability();
        if (percentage) {
            repairDamage = maxDurability * durability / 100;
        }

        Damageable itemMeta = (Damageable) item.getItemMeta();
        int newDamage = itemMeta.getDamage() - repairDamage;
        itemMeta.setDamage(Math.max(newDamage, 0));
        item.setItemMeta(itemMeta);
        return item;
    }
}