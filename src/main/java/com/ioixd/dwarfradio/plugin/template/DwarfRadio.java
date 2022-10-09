package com.ioixd.dwarfradio.plugin.template;

import com.ioixd.dwarfradio.plugin.template.event.ItemListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DwarfRadio extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ItemListener(this), this);
        //this.getCommand("enrich").setExecutor(new EnrichCommand());
        //getLogger().info("Added the 'enrich' command.");
    }
}