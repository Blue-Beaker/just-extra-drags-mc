package io.bluebeaker.justextradrags;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;

import io.bluebeaker.justextradrags.compat.AltGhostHandler;
import mezz.jei.api.IModRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

public class CustomEntries {
    
    public static void register(IModRegistry registry) {
        for(String entry:JustExtraDragsConfig.customEntries){
            registerEntry(entry, registry);
        }
    }
    private static void registerEntry(String entry,IModRegistry registry){
        String[] splitted=entry.split(":");
        if(splitted.length<2) return;
        boolean ignoreFit = false;
        if(splitted.length>=3){
            ignoreFit=Boolean.parseBoolean(splitted[2]);
        }
        try {
            Class container = Class.forName(splitted[0]);
            Class slot = Class.forName(splitted[1]);
            boolean cancel = false;
            if(!GuiContainer.class.isAssignableFrom(container)){
                JustExtraDrags.getLogger().warn("Container class "+container.getName()+" isn't assignable!");
                cancel=true;
            }
            if(!Slot.class.isAssignableFrom(slot)){
                JustExtraDrags.getLogger().warn("Slot class "+slot.getName()+" isn't assignable!");
                cancel=true;
            }
            if(cancel) return;
            if(ignoreFit){
                registry.addGhostIngredientHandler(container, new AltGhostHandler<>(slot));
            }else{
                registry.addGhostIngredientHandler(container, new GenericGhostHandler<>(slot));
            }
        } catch (ClassNotFoundException e) {
            JustExtraDrags.getLogger().warn("Class not found: "+e.getStackTrace().toString());
        }
    }
}
