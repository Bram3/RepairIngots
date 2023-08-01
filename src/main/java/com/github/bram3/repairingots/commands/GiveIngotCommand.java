package com.github.bram3.repairingots.commands;

import com.github.bram3.repairingots.RepairIngots;
import com.github.bram3.repairingots.config.Config;
import com.github.bram3.repairingots.items.Ingot;
import com.github.bram3.repairingots.managers.IngotManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveIngotCommand extends AbstractCommand {
    private final IngotManager ingotManager;

    public GiveIngotCommand(RepairIngots repairIngots, Config config, IngotManager ingotManager) {
        super("giveingot", "Gives a repair ingot to a player.", "/<command> [player] [ingot_type] <amount>", new ArrayList<>(), "repairingots.giveingot", config, repairIngots);
        this.ingotManager = ingotManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(config.getColoredMessage("error_messages.no_player_specified"));
            return;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(config.getColoredMessage("error_messages.not_a_valid_player"));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(config.getColoredMessage("error_messages.no_ingot_specified"));
            return;
        }
        Ingot ingot = ingotManager.getIngot(args[1]);

        if (ingot == null) {
            sender.sendMessage(config.getColoredMessage("error_messages.not_a_valid_ingot"));
            return;
        }
        int amount = 1;
        if (args.length == 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(config.getColoredMessage("error_messages.not_a_valid_number"));
                return;
            }
        }
        ItemStack item = ingot.getItemStack();
        item.setAmount(amount);
        player.getInventory().addItem(item);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2) {
            completions.addAll(ingotManager.getIngotNames());
        } else if (args.length == 3) {
            completions.add("1");
            completions.add("64");
        }
        return completions;
    }
}
