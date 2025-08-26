package io.bluebeaker.justextradrags.config;

import io.bluebeaker.justextradrags.JustExtraDragsConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JXDConfigManager {
    private static final Map<Class<? extends Container>, List<ConfigEntry>> entries = new HashMap<>();
    private static final List<ConfigEntry> legacyEntries = new ArrayList<>();

    public static List<ConfigEntry> getAllEntries(){
        List<ConfigEntry> configEntries = new ArrayList<>();
        for (List<ConfigEntry> value : entries.values()) {
            configEntries.addAll(value);
        }
        configEntries.addAll(legacyEntries);
        return configEntries;
    }

    public static void updateEntriesFromConfig(){
        for(String entry: JustExtraDragsConfig.customEntries){
            registerEntry(entry);
        }
    }

    private static void registerEntry(String line){
        ConfigEntry configEntry = ConfigEntry.getFromConfigLine(line);
        if(configEntry!=null){
            addEntry(configEntry);
        }
    }

    public static void addEntry(ConfigEntry entry){
        if(entry.clazzContainer==null){
            legacyEntries.add(entry);
            return;
        }
        if(!entries.containsKey(entry.clazzContainer)){
            entries.put(entry.clazzContainer,new ArrayList<>());
        }
        entries.get(entry.clazzContainer).add(entry);
    }

    // Prevent cheating items by sending modified packet
    public static boolean isAllowedToPut(Container container, Slot slot){
        if(!legacyEntries.isEmpty()) return true;

        Class<?> clazz = container.getClass();
        while (!entries.containsKey(clazz)){
            if(clazz==null || clazz==GuiScreen.class) return false;
            clazz=clazz.getSuperclass();
        }
        for (ConfigEntry handler : entries.get(clazz)) {
            if(handler.clazzSlot.isInstance(slot) && (handler.slotIDs.isEmpty() || handler.slotIDs.contains(slot.slotNumber))) {
                return true;
            }
        }
        return false;
    }
}
