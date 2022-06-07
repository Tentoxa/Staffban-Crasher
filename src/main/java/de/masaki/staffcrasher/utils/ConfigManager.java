package de.masaki.staffcrasher.utils;

import net.minecraft.client.Minecraft;
import org.bspfsystems.yamlconfiguration.file.FileConfiguration;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static File file = new File(Minecraft.getMinecraft().mcDataDir+"/config/staff_ban_config.yml");
    private static final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void setFile(File filepath){
        file = filepath;
    }

    public static void saveCfg(){
        try {
            cfg.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void setEnabled(boolean enabled){
        cfg.set("enabled",enabled);
        saveCfg();
    }

    public static boolean getEnabled(){
        return cfg.getBoolean("enabled");
    }

    public static int getStaffbanMax(){
        return cfg.getInt("staffbanMax");
    }

    public static void defaultConfig(){
        cfg.set("staffbanMax",1000);
        cfg.set("enabled",true);
        cfg.set("created",true);
        saveCfg();
    }

    public static void setStaffbanMax(int staffbanMax){
        cfg.set("staffbanMax",staffbanMax);
        saveCfg();
    }

    public static boolean isCreated(){
        return cfg.contains("created");
    }

}
