package de.marcey.marceyapi.runnable;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class MarceyRunnable {

    public static BukkitTask runTaskTimer(Runnable runnable, int delay, int period) {
        return Bukkit.getScheduler().runTaskTimer(MarceyMinecraftAPI.getPlugin(), runnable, delay, period);
    }

}
