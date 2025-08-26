package io.bluebeaker.justextradrags;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;

import io.bluebeaker.justextradrags.compat.AltGhostHandler;
import mezz.jei.api.IModRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import java.util.List;

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

            //noinspection unchecked
            AltGhostHandler<GuiContainer> handler = new AltGhostHandler<GuiContainer>(slot, ignoreFit);
            // If 4th param is present, limit the slot index
            if(splitted.length>=4){
                handler.setSlotIDs(Utils.getIntsFromCommaSeparatedString(splitted[3]));
            }

            //noinspection unchecked
            registry.addGhostIngredientHandler(container, handler);
            JustExtraDrags.getLogger().info("Adding handler {} -> {}:",container.getName(),handler.toString());
        } catch (ClassNotFoundException e) {
            JustExtraDrags.getLogger().warn("Class not found: "+e.getStackTrace().toString());
        }
    }
}
