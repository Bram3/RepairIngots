package com.github.bram3.repairingots.events;

import com.github.bram3.repairingots.items.Ingot;
import com.github.bram3.repairingots.managers.IngotManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class InventoryClickEvent implements Listener {
    private final IngotManager ingotManager;

    public InventoryClickEvent(IngotManager ingotManager) {
        this.ingotManager = ingotManager;
    }

    @EventHandler
    public void inventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (!(event.getCurrentItem().getItemMeta() instanceof Damageable)) return;
        if ((((Damageable) event.getCurrentItem().getItemMeta()).getDamage()) == 0) return;
        if (event.getCursor() == null) return;
        if (!ingotManager.isIngot(event.getCursor())) return;
        Ingot ingot = ingotManager.getIngot(event.getCursor());
        if (ingot == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ingot.repairItem(event.getCurrentItem());
        if (event.getCursor().getAmount() == 1) {
            player.setItemOnCursor(null);
        } else {
            ItemStack newIngot = ingot.getItemStack();
            newIngot.setAmount(event.getCursor().getAmount() - 1);
            player.setItemOnCursor(newIngot);
        }
        player.updateInventory();
        event.setCancelled(true);

    }

}
