package de.marcey.marceyapi.config;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import jdk.javadoc.internal.doclets.toolkit.taglets.SeeTaglet;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MarceyConfig {

    private final String file;
    private final File folder;
    private FileConfiguration cfg;
    private File cfgFile;

    public MarceyConfig(String configName){
        this.file = !configName.contains("//") ? configName + ".yml" : configName.split("//")[configName.split("//").length-1] + ".yml";
        this.folder = new File(MarceyMinecraftAPI.getPlugin().getDataFolder() + "//settings//" + (file.contains("//") ? file.split("//")[0] + "//" : ""));
        cfg = null;
        cfgFile = null;
        reload();
    }

    public void reload(){
        if(!folder.exists()) folder.mkdirs();
        cfgFile = new File(folder, file);
        if(!cfgFile.exists()){
            try {
                cfgFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        cfg = YamlConfiguration.loadConfiguration(cfgFile);
    }

    public FileConfiguration getConfig(){
        if(cfg == null){
            reload();
        }
        return cfg;
    }

    public File getFolder() {
        return folder;
    }

    public File getConfigFile() {
        return cfgFile;
    }

    public void save(){
        if(cfg == null || cfgFile == null) return;
        try {
            getConfig().save(cfgFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean contains(String path){
        if(getConfig().contains(path)) return getConfig().get(path) != null;
        return getConfig().contains(path);
    }
    public void set(String path, Object value){
        getConfig().set(path, value);
        save();
    }
    public int getInt(String path){
        return getConfig().getInt(path);
    }
    public double getDouble(String path){
        return getConfig().getDouble(path);
    }
    public String getString(String path){
        return getConfig().getString(path);
    }
    public boolean getBoolean(String path){
        return getConfig().getBoolean(path);
    }
    public Location getLocation(String path){
        return getConfig().getLocation(path);
    }
    public List<String> getStringList(String path){
        return getConfig().getStringList(path);
    }
    public Set<String> getKeys(boolean deep){
        return getConfig().getKeys(deep);
    }
}
