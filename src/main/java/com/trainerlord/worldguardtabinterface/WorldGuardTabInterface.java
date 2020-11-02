package com.trainerlord.worldguardtabinterface;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import kr.entree.spigradle.annotations.PluginMain;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.swing.plaf.synth.Region;
import java.io.File;
import java.util.ArrayList;

@PluginMain
public class WorldGuardTabInterface extends JavaPlugin {

  public static StringFlag DISPLAY_NAME;


  public WorldGuardTabInterface() {}

  public WorldGuardTabInterface(
      JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
    super(loader, description, dataFolder, file);
  }

  @Override
  public void onEnable() {
      new RegionExpansions().register();
  }

  @Override
  public void onLoad() {
    super.onLoad();

    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
    try {
      // create a flag with the name "my-custom-flag", defaulting to true
      StringFlag flag = new StringFlag("display-name", "Unnamed");
      registry.register(flag);
      DISPLAY_NAME = flag; // only set our field if there was no error
    } catch (FlagConflictException e) {
      // some other plugin registered a flag by the same name already.
      // you can use the existing flag, but this may cause conflicts - be sure to check type
      Flag<?> existing = registry.get("my-custom-flag");
      if (existing instanceof StateFlag) {
        DISPLAY_NAME = (StringFlag) existing;
      } else {
        // types don't match - this is bad news! some other plugin conflicts with you
        // hopefully this never actually happens
      }
    }
  }
}

