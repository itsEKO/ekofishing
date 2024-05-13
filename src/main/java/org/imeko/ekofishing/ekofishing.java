package org.imeko.ekofishing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class ekofishing extends JavaPlugin implements Listener {

    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Register events
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            List<ItemStack> customLoot = getCustomFishingLoot();
            for (ItemStack item : customLoot) {
                Item drop = player.getWorld().dropItem(player.getLocation(), item);
                drop.setPickupDelay(0);
                event.getCaught().remove();
            }
        }
    }

    private List<ItemStack> getCustomFishingLoot() {
        List<ItemStack> loot = new ArrayList<>();
        float lootChance = random.nextFloat();
        // custom loot table
        if (lootChance > 0.1 & lootChance < 0.15 ){
            loot.add(getRandomItemStack(Material.DIAMOND, 1, 1));
        } else if (lootChance > 0.15 & lootChance < 0.22) {
            loot.add(getRandomItemStack(Material.EMERALD, 1, 1));
        } else if (lootChance > 0.22 & lootChance < 0.32) {
            loot.add(getRandomItemStack(Material.GOLD_INGOT, 1, 3));
        } else if (lootChance > 0.32 & lootChance < 0.37) {
            loot.add(getCoreItemStack("common"));
        } else if (lootChance > 0.37 & lootChance < 0.4) {
            loot.add(getCoreItemStack("rare"));
        } else if (lootChance > 0.4 & lootChance < 0.41) {
            loot.add(getCoreItemStack("epic"));
        }
        return loot;
    }

    private ItemStack getRandomItemStack(Material material, int minQuantity, int maxQuantity) {
        int quantity = minQuantity + random.nextInt(maxQuantity - minQuantity + 1);
        return new ItemStack(material, quantity);
    }

    private ItemStack getCoreItemStack(String itemName){
        ItemStack core = new ItemStack(Material.CLOCK);
        ItemMeta coreMeta = core.getItemMeta();
        if ( coreMeta != null ) {
            coreMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right Click to open"));
            switch (itemName) {
                case "common":
                    coreMeta.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "COMMON CORE" + ChatColor.GRAY + "]");
                    break;

                case "rare":
                    coreMeta.setDisplayName(ChatColor.GRAY + "[" + ChatColor.AQUA + "RARE CORE" + ChatColor.GRAY + "]");
                    break;

                case "epic":
                    coreMeta.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "EPIC CORE" + ChatColor.GRAY + "]");
            }
            core.setItemMeta(coreMeta);
        }

        return core;

    }
}