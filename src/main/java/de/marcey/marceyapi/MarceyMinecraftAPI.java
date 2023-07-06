package de.marcey.marceyapi;

import de.marcey.marceyapi.actionbar.ActionBarBuilder;
import de.marcey.marceyapi.actionbar.ActionBarUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class MarceyMinecraftAPI extends JavaPlugin {

    private static Plugin plugin;
    private static String version = "1.2.1";
    public static void init(Plugin pl){
        plugin = pl;
        sendStartupMessage();
    }

    public static void disable(){
        sendStopMessage();
    }

    public static BukkitTask runTaskTimer(Runnable runnable, int delay, int period) {
        return Bukkit.getScheduler().runTaskTimer(getPlugin(), runnable, delay, period);
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static void sendStartupMessage(){
        plugin.getLogger().info("\n  __  __                                   _____ _____ \n" +
                " |  \\/  |                            /\\   |  __ \\_   _|\n" +
                " | \\  / | __ _ _ __ ___ ___ _   _   /  \\  | |__) || |  \n" +
                " | |\\/| |/ _` | '__/ __/ _ \\ | | | / /\\ \\ |  ___/ | |  \n" +
                " | |  | | (_| | | | (_|  __/ |_| |/ ____ \\| |    _| |_ \n" +
                " |_|  |_|\\__,_|_|  \\___\\___|\\__, /_/    \\_\\_|   |_____|\n" +
                "                             __/ |                     \n" +
                "                            |___/                     ");
        plugin.getLogger().info("Dieser Server nutzt die MarceyAPI v" + version + ", gecodet von Marcey.");
        plugin.getLogger().info("Bei Fragen und weiteres, schreibt mich gerne auf Discord an.");
        plugin.getLogger().info("Discord => marc3y");
    }
    public static void sendStopMessage(){
        plugin.getLogger().info("\n  __  __                                   _____ _____ \n" +
                " |  \\/  |                            /\\   |  __ \\_   _|\n" +
                " | \\  / | __ _ _ __ ___ ___ _   _   /  \\  | |__) || |  \n" +
                " | |\\/| |/ _` | '__/ __/ _ \\ | | | / /\\ \\ |  ___/ | |  \n" +
                " | |  | | (_| | | | (_|  __/ |_| |/ ____ \\| |    _| |_ \n" +
                " |_|  |_|\\__,_|_|  \\___\\___|\\__, /_/    \\_\\_|   |_____|\n" +
                "                             __/ |                     \n" +
                "                            |___/                     ");
        plugin.getLogger().info("Die MarceyAPI v" + version + ", gecodet von Marcey, wird nun gestoppt.");
    }
}
