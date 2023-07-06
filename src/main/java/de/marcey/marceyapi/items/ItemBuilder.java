package de.marcey.marceyapi.items;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {

    private ItemStack itemStack;
    public ItemBuilder(Material material){
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
    } 

    @Override
    public String toString() {
        if(getItemStack() == null) return "ItemStack:null";
        String lore = "";
        String enchantments = "";
        String itemflags = "";
        if(getItemStack().getItemMeta().getLore() != null) {
            for (String a : getItemStack().getItemMeta().getLore()) {
                if(lore.isEmpty()) {
                    lore = a;
                } else lore = lore + "%%" + a;
            }
        }
        for (Map.Entry<Enchantment, Integer> a : getItemStack().getItemMeta().getEnchants().entrySet()) {
            if (enchantments.isEmpty()) {
                enchantments = a.getKey().getKey().getKey() + "&&" + a.getValue();
            } else enchantments = enchantments + "%%" + a.getKey().getKey().getKey() + "&&" + a.getValue();
        }
        if(getItemStack().getItemMeta().getItemFlags() != null && !getItemStack().getItemMeta().getItemFlags().isEmpty()) {
            for (ItemFlag a : getItemStack().getItemMeta().getItemFlags()) {
                if(itemflags.isEmpty()) {
                    itemflags = a.toString();
                } else itemflags = itemflags + "%%" + a.toString();
            }
        }
        return "ItemStack:" + getItemStack().getAmount() + ";" + getItemStack().getType().name() + ";" + lore + ";" + enchantments + ";" + itemflags + ";" + getItemStack().getItemMeta().getDisplayName();
    }

    public static ItemBuilder fromString(String str){
        if(str.equalsIgnoreCase("ItemStack:null")) return null;
        str = str.replace("ItemStack:", "");
        String[] args = str.split(";");
        int amount = Integer.parseInt(args[0]);
        Material material = Material.valueOf(args[1]);
        List<String> lore = new ArrayList<>();
        if(!args[2].isEmpty()) {
            for (String l : args[2].split("%%")) {
                if(l == null || l.isEmpty()) continue;
                lore.add(l);
            }
        }
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        if(!args[3].isEmpty()) {
            for (String l : args[3].split("%%")) {
                if(l == null || l.isEmpty() || !l.contains("&&")) continue;
                String[] enchantArgs = l.split("&&");
                Enchantment enchantment = Enchantment.getByKey(new NamespacedKey(MarceyMinecraftAPI.getPlugin(), enchantArgs[0]));
                int level = Integer.parseInt(enchantArgs[1]);
                enchantments.put(enchantment, level);
            }
        }
        List<ItemFlag> itemflags = new ArrayList<>();
        if(!args[4].isEmpty()) {
            for (String l : args[4].split("%%")) {
                if(l == null || l.isEmpty()) continue;
                itemflags.add(ItemFlag.valueOf(l));
            }
        }
        String displayname = args[5];

        return new ItemBuilder(material).amount(amount).lore(lore).enchantments(enchantments).itemflags(itemflags).displayname(displayname);
    }

    public ItemBuilder amount(int amount){
        getItemStack().setAmount(amount);
        return this;
    }

    public ItemBuilder lore(String... lore){
        ItemMeta meta = getItemStack().getItemMeta();
        meta.setLore(Arrays.asList(lore));
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        ItemMeta meta = getItemStack().getItemMeta();
        meta.setLore(lore);
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemBuilder itemFlag(ItemFlag itemFlag){
        ItemMeta meta = getItemStack().getItemMeta();
        meta.addItemFlags(itemFlag);
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level){
        ItemMeta meta = getItemStack().getItemMeta();
        meta.addEnchant(enchantment, level, true);
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchantments(HashMap<Enchantment, Integer> enchants){
        ItemMeta meta = getItemStack().getItemMeta();
        for(Map.Entry<Enchantment, Integer> e : enchants.entrySet()){
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemBuilder itemflags(List<ItemFlag> itemFlags){
        ItemMeta meta = getItemStack().getItemMeta();
        for(ItemFlag e : itemFlags){
            meta.addItemFlags(e);
        }
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemBuilder displayname(String displayname){
        ItemMeta meta = getItemStack().getItemMeta();
        meta.setDisplayName(displayname);
        getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemStack build(){
        return itemStack;
    }

    private ItemStack getItemStack() {
        return itemStack;
    }
}
