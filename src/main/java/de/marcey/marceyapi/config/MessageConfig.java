package de.marcey.marceyapi.config;

public class MessageConfig {

    private MarceyConfig config;

    public MessageConfig(String configName){
        this.config = new MarceyConfig(configName);
    }

    public void register(String key, String message){
        this.config.set(key, message);
    }

    public String get(String key){
        return this.config.getString(key);
    }

    public boolean contains(String key){
        return this.config.contains(key);
    }

}
