package de.marcey.marceyapi.worlds;

import com.google.common.io.Files;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class WorldManager {

    private String worldname;
    private World world;

    public WorldManager(String worldname) {
        this.worldname = worldname;
        this.world = Bukkit.getWorld(worldname);
    }

    public void loadWorld(int chunkRadiusToLoad) {
        if (getWorld() == null) createWorld();
        getWorld().loadChunk(getWorld().getSpawnLocation().getChunk());
        for (int x = -1; x < chunkRadiusToLoad; x++) {
            getWorld().loadChunk(getWorld().getChunkAt(getWorld().getSpawnLocation().add(x, 0, 0)));
        }
        for (int z = -1; z < chunkRadiusToLoad; z++) {
            getWorld().loadChunk(getWorld().getChunkAt(getWorld().getSpawnLocation().add(0, 0, z)));
        }
    }

    public boolean saveWorld() {
        if (getWorld() == null) return false;
        getWorld().save();
        File file1 = new File("plugins/MarceyAPI/WorldSaves/" + getWorld().getName());
        File file2 = getWorld().getWorldFolder();
        if (file1.exists()) deleteFolder(file1);
        copyWorldFolder(file2, file1);
        return true;
    }
    private void teleportPlayers(Location location) {
        if (location != null &&
                !location.getWorld().getName().equals(getWorld().getName())) {
            if (Bukkit.getOnlinePlayers() != null)
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld().getName().equals(getWorld().getName())) {
                        player.teleport(location);
                    }
                }
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.kickPlayer("§cDie Welt lädt neu.");
        });
    }
    private HashMap<String, Object[]> getCurrentPlayerLocations() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (Bukkit.getOnlinePlayers() != null)
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().getName().equals(getWorld().getName())) {
                    Location location = player.getLocation();
                    hashMap.put(player.getName(), new Object[] { location.getWorld().getName(), Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()), Float.valueOf(location.getYaw()), Float.valueOf(location.getPitch()) });
                }
            }
        return (HashMap)hashMap;
    }

    private static void teleportPlayersBack(HashMap<String, Object[]> paramHashMap) {
        if (paramHashMap != null)
            for (String str : paramHashMap.keySet()) {
                Player player = Bukkit.getPlayer(str);
                if (player != null) {
                    Object[] arrayOfObject = paramHashMap.get(str);
                    Location location = new Location(Bukkit.getWorld((String)arrayOfObject[0]), ((Double)arrayOfObject[1]).doubleValue(), ((Double)arrayOfObject[2]).doubleValue(), ((Double)arrayOfObject[3]).doubleValue(), ((Float)arrayOfObject[4]).floatValue(), ((Float)arrayOfObject[5]).floatValue());
                    if (!location.getWorld().getBlockAt(location).isEmpty() || !location.getWorld().getBlockAt(location.add(0.0D, 1.0D, 0.0D)).isEmpty())
                        location.setY(location.getWorld().getHighestBlockYAt(location) + 2.0D);
                    player.teleport(location);
                }
            }
    }

    public boolean resetWorld(Location teleportLocation, boolean kick) {
        if (getWorld() == null) return false;
        File file1 = new File("plugins/MarceyAPI/WorldSaves/" + getWorld().getName());
        File file2 = getWorld().getWorldFolder();

        HashMap<String, Object[]> hashMap = null;
        if (file1.exists() &&
                file2.exists())
            if (getWorld().getName().equals(((World) Bukkit.getServer().getWorlds().get(0)).getName()) || getWorld().getName().equals(String.valueOf(((World) Bukkit.getServer().getWorlds().get(0)).getName()) + "_nether") || getWorld().getName().equals(String.valueOf(((World) Bukkit.getServer().getWorlds().get(0)).getName()) + "_the_end")) {
                System.out.println("Konnte Welt nicht resetten, da diese Welt eine Hauptwelt war.");
                return false;
            } else {
                if (!kick) {
                    hashMap = getCurrentPlayerLocations();
                    teleportPlayers(teleportLocation);
                } else {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.kickPlayer("§cDie Welt lädt neu.");
                    });
                }
                Bukkit.getServer().unloadWorld(getWorld(), true);
                deleteFolder(file2);
                copyWorldFolder(file1, file2);
                teleportPlayersBack(hashMap);
            }
        return true;
    }


    private void copyWorldFolder(File paramFile1, File paramFile2) {
        try {
            ArrayList<String> arrayList = new ArrayList();
            arrayList.add("WorldSettings.yml");
            if (!arrayList.contains(paramFile1.getName()))
                if (paramFile1.isDirectory()) {
                    if (!paramFile2.exists())
                        paramFile2.mkdirs();
                    String[] arrayOfString1 = paramFile1.list();
                    byte b;
                    int i;
                    String[] arrayOfString2;
                    for (i = (arrayOfString2 = arrayOfString1).length, b = 0; b < i; ) {
                        String str = arrayOfString2[b];
                        File file1 = new File(paramFile1, str);
                        File file2 = new File(paramFile2, str);
                        copyWorldFolder(file1, file2);
                        b++;
                    }
                } else {
                    Files.copy(paramFile1, paramFile2);
                }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void deleteFolder(File paramFile) {
        File[] arrayOfFile = paramFile.listFiles();
        if (arrayOfFile != null) {
            byte b;
            int i;
            File[] arrayOfFile1;
            for (i = (arrayOfFile1 = arrayOfFile).length, b = 0; b < i; ) {
                File file = arrayOfFile1[b];
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
                b++;
            }
        }
        paramFile.delete();
    }

    public void unloadWorld(){
        if(getWorld() == null) return;
        for(Chunk chunk : getWorld().getLoadedChunks()){
            getWorld().unloadChunk(chunk);
        }
        Bukkit.getServer().unloadWorld(getWorld(), true);
    }

    private void createWorld(){
        this.world = new WorldCreator(worldname).createWorld();
    }

    public World getWorld() {
        return world;
    }

    public String getWorldname() {
        return worldname;
    }

    public void setSpawnLocation(Location loc){
        if(getWorld() == null) createWorld();
        getWorld().setSpawnLocation(loc);
    }

    public Location getSpawnLocation(){
        if(getWorld() == null) createWorld();
        return getWorld().getSpawnLocation();
    }

    public void sendWorldMessage(String message){
        if(getWorld() == null) createWorld();
        for(Player current : Bukkit.getOnlinePlayers()){
            if(!current.getWorld().getName().equalsIgnoreCase(getWorld().getName())) continue;
            current.sendMessage(message);
        }
    }
}
