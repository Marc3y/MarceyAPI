package de.marcey.marceyapi.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class InventoryManager {

    public static String[] toBase64(Inventory inv){
        String[] values = invToBase64(inv);
        return values;
    }

    public static Inventory toFullInventory(String[] values){
        ItemStack[][] items = base64ToInv(values);
        Inventory inv = Bukkit.createInventory(null, 54);
        inv.setContents(items[0]);
        return inv;
    }

    public static String[] toBase64(PlayerInventory inv){
        String[] values = invToBase64(inv);
        return values;
    }

    public static ItemStack[][] toInventory(Player p, String[] values){
        ItemStack[][] items = base64ToInv(values);
        p.getInventory().setContents(items[0]);
        p.getInventory().setArmorContents(items[1]);
        return items;
    }

    public static ItemStack[][] toInventory(String[] values){
        ItemStack[][] items = base64ToInv(values);
        return items;
    }

    private static String[] invToBase64(PlayerInventory inv){
        String content = toBase64(inv.getContents());
        String armor = toBase64(inv.getArmorContents());
        return new String[] {content, armor};
    }

    private static String[] invToBase64(Inventory inv){
        String content = toBase64(inv.getContents());
        return new String[] {content};
    }

    private static ItemStack[][] base64ToInv(String[] values){
        ItemStack[] content = fromBase64(values[0]);
        ItemStack[] armor = fromBase64(values[1]);
        return new ItemStack[][]{content, armor};
    }

    private static ItemStack[] fromBase64(String data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataIn = new BukkitObjectInputStream(in);

            ItemStack[] items = new ItemStack[dataIn.readInt()];

            for(int i = 0; i < items.length; i++){
                items[i] = (ItemStack) dataIn.readObject();
            }
            dataIn.close();
            return items;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String toBase64(ItemStack[] items){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOut = new BukkitObjectOutputStream(out);
            dataOut.writeInt(items.length);
            for(ItemStack item : items){
                dataOut.writeObject(item);
            }
            dataOut.close();
            return Base64Coder.encodeLines(out.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
