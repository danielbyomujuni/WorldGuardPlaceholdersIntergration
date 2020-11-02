package com.trainerlord.worldguardtabinterface;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;

public class RegionExpansions extends PlaceholderExpansion {

    public int Priotity = -1;
    public String region;

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "worldguard";
    }

    @Override
    public String getAuthor() {
        return "Trainerlord";
    }

    @Override
    public String getVersion() {
        return "0.0.2";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        // %worldguard_region%
        if(identifier.equals("region")){
            WorldGuardPlugin worldGuardplugin = WorldGuardPlugin.inst();
            WorldGuard worldGuard = WorldGuard.getInstance();
            LocalPlayer Player = worldGuardplugin.wrapPlayer(Bukkit.getPlayer(player.getUniqueId()));

            RegionManager rm = worldGuard.getPlatform().getRegionContainer().get(Player.getWorld());

            BlockVector3 loc = BlockVector3.at(Player.getLocation().getBlockX(),Player.getLocation().getBlockY(),Player.getLocation().getBlockZ());
            ApplicableRegionSet set = rm.getApplicableRegions(loc);
            int size = set.size();
            //System.out.println(size);

            if (size != 0) {
                set.getRegions().forEach((r) -> {
                    if (r.getPriority() >= Priotity) {
                        Priotity = r.getPriority();
                        region = r.getFlag(WorldGuardTabInterface.DISPLAY_NAME);
                    }
                });
                //System.out.println(region);
                return region;

            } else {
                //System.out.println("Unknown");
                //System.out.println("Unknown");
                return "Unknown";
            }
        }

        return null;
    }
}
