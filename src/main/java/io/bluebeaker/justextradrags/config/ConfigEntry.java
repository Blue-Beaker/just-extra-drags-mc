package io.bluebeaker.justextradrags.config;

import io.bluebeaker.justextradrags.JustExtraDrags;
import io.bluebeaker.justextradrags.Utils;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ConfigEntry {
    @Nullable
    public final Class<?> clazzContainerGui;

    public final Class<? extends Container> clazzContainer;
    public final Class<? extends Slot> clazzSlot;
    public final boolean ignoreFit;
    public final Set<Integer> slotIDs;

    public ConfigEntry(@Nullable Class<?> clazzContainerGui, @Nullable Class<? extends Container> clazzContainer, @Nullable Class<? extends Slot> clazzSlot, boolean ignoreFit){
        this(clazzContainerGui,clazzContainer,clazzSlot,ignoreFit,new HashSet<>());
    }

    public ConfigEntry(@Nullable Class<?> clazzContainerGui, @Nullable Class<? extends Container> clazzContainer, @Nullable Class<? extends Slot> clazzSlot, boolean ignoreFit, Set<Integer> slotIDs) {
        this.clazzContainerGui = clazzContainerGui;
        this.clazzContainer = clazzContainer;
        this.clazzSlot = clazzSlot;
        this.ignoreFit=ignoreFit;
        this.slotIDs = slotIDs;
    }

    @Nullable
    public static ConfigEntry getFromConfigLine(String configLine){
        try {
            String[] splitted=configLine.split(":");
            if(splitted.length<3) return null;
            boolean ignoreFit = false;
            if(splitted.length>=4){
                ignoreFit=Boolean.parseBoolean(splitted[3]);
            }
            Set<Integer> slotIDs = new HashSet<>();
            if(splitted.length>=5){
                slotIDs.addAll(Utils.getIntsFromCommaSeparatedString(splitted[4]));
            }
            Class clazzGui = null;
            // Only try to load GUI class on client
            if(FMLCommonHandler.instance().getSide()== Side.CLIENT){
                try {
                    clazzGui = Class.forName(splitted[0]);
                } catch (ClassNotFoundException e) {
                    JustExtraDrags.getLogger().error("Container GUI class '{}' not found",splitted[0]);
                }
            }
            Class clazzContainer;
            try {
                clazzContainer = Class.forName(splitted[1]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Container class '"+splitted[1]+"' not found");
            }
            Class clazzSlot;
            try {
                clazzSlot = Class.forName(splitted[2]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Slot class '"+splitted[2]+"' not found");
            }
            if(!Container.class.isAssignableFrom(clazzContainer)){
                throw new RuntimeException("Container class '"+splitted[1]+"' isn't applicable");
            }
            if(!Slot.class.isAssignableFrom(clazzSlot)){
                throw new RuntimeException("Slot class '"+splitted[2]+"' isn't applicable");
            }
            return new ConfigEntry(clazzGui,clazzContainer,clazzSlot,ignoreFit,slotIDs);
        }catch (RuntimeException e){
            JustExtraDrags.getLogger().error("Error when loading config line {} : {}",configLine,e.getMessage());
            return null;
        }
    }
}
