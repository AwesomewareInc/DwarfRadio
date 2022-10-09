package com.ioixd.dwarfradio.plugin.template.event;

import com.ioixd.dwarfradio.plugin.template.DwarfRadio;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ItemListener implements Listener {

    public void BukkitRunnableExtension(PlayerItemHeldEvent event) {
        URL url;
        try {
            url = new URL("https://gba.ioi-xd.net/stream");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        InputStreamReader stream;
        try {
            stream = new InputStreamReader(url.openStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Player player = event.getPlayer();
        try (BufferedReader reader = new BufferedReader(stream)) {
            for (int line; (line = reader.read()) != -1;) {
                String loc = String.format("byte.%s",Integer.toString(line, 2));
                ItemStack item = player.getInventory().getItemInMainHand();
                if(item == null) {
                    return;
                }
                if(item.getType() != Material.WOODEN_PICKAXE) {
                    return;
                }
                player.playSound(player.getLocation(), loc, SoundCategory.MASTER, 1, 1);
                player.sendTitle(" ", loc, 0, 1, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private final Logger logger;
    private final Plugin plugin;

    public ItemListener(DwarfRadio plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.logger = plugin.getLogger();
        this.plugin = Bukkit.getPluginManager().getPlugin("DwarfRadio");
    }

    @EventHandler
    public void itemhold(PlayerItemHeldEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null) {
            System.out.println(item);
        } else {
            return;
        }
        if(item.getType() != Material.WOODEN_PICKAXE) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            BukkitRunnableExtension(event);
        });
    };
}
