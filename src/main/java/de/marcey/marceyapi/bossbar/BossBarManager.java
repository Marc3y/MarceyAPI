package de.marcey.marceyapi.bossbar;

import de.marcey.marceyapi.objects.RandomPicker;
import org.bukkit.boss.BossBar;

import java.util.ArrayList;
import java.util.List;

public class BossBarManager {

    public List<BossBar> getAllBossBars(){
        List<BossBar> toReturn = new ArrayList<>();
        for(BossBarBuilder b : BossBarBuilder.getBossbars()){
            toReturn.add(b.getBossBar());
        }
        return toReturn;
    }

    public List<BossBarBuilder> getAllBossBarBuilders(){
        return new ArrayList<>(BossBarBuilder.getBossbars());
    }

}
