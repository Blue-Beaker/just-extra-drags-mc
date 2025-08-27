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
    @Nullable
    public final Class<? extends Container> clazzContainer;
    public final Class<? extends Slot> clazzSlot;
    public final boolean checkFit;
    public final Set<Integer> slotIDs;

    public ConfigEntry(@Nullable Class<?> clazzContainerGui, @Nullable Class<? extends Container> clazzContainer, Class<? extends Slot> clazzSlot, boolean checkFit){
        this(clazzContainerGui,clazzContainer,clazzSlot, checkFit,new HashSet<>());
    }

    public ConfigEntry(@Nullable Class<?> clazzContainerGui, @Nullable Class<? extends Container> clazzContainer, Class<? extends Slot> clazzSlot, boolean checkFit, Set<Integer> slotIDs) {
        this.clazzContainerGui = clazzContainerGui;
        this.clazzContainer = clazzContainer;
        this.clazzSlot = clazzSlot;
        this.checkFit = checkFit;
        this.slotIDs = slotIDs;
    }

    @Nullable
    public static ConfigEntry getFromConfigLine(String configLine){
        try {
            String[] splitted=configLine.split(":");
            if(splitted.length<3) return null;
            Class clazzGui = null;

            int indexParamIgnoreFit = 3;

            Class<? extends Container> containerClass = null;
            Class<? extends Slot> slotClass = null;
            // Only try to load GUI class on client
            if(FMLCommonHandler.instance().getSide()== Side.CLIENT){
                try {
                    clazzGui = Class.forName(splitted[0]);
                } catch (ClassNotFoundException e) {
                    JustExtraDrags.getLogger().error("Container GUI class '{}' not found",splitted[0]);
                }
            }
            Class param1;
            try {
                param1 = Class.forName(splitted[1]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Container class '"+splitted[1]+"' not found");
            }

            if(!Container.class.isAssignableFrom(param1)){
                if(Slot.class.isAssignableFrom(param1)){
                    indexParamIgnoreFit=indexParamIgnoreFit-1;
                    slotClass=param1;
                    JustExtraDrags.getLogger().warn("Old config entry detected: \n{}\n This will work but server-side anticheat check will be disabled.",configLine);
                }else {
                    throw new RuntimeException("Container class '"+splitted[1]+"' isn't applicable");
                }
            }else {
                containerClass=param1;
            }

            if(slotClass==null){
                Class param2;
                try {
                    param2 = Class.forName(splitted[2]);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Slot class '"+splitted[2]+"' not found");
                }

                if(!Slot.class.isAssignableFrom(param2)){
                    throw new RuntimeException("Slot class '"+splitted[2]+"' isn't applicable");
                }else {
                    slotClass=param2;
                }
            }

            boolean ignoreFit = false;
            if(splitted.length>indexParamIgnoreFit){
                ignoreFit=Boolean.parseBoolean(splitted[indexParamIgnoreFit]);
            }
            Set<Integer> slotIDs = new HashSet<>();
            if(splitted.length>indexParamIgnoreFit+1){
                slotIDs.addAll(Utils.getIntsFromCommaSeparatedString(splitted[indexParamIgnoreFit+1]));
            }

            return new ConfigEntry(clazzGui,containerClass,slotClass,!ignoreFit,slotIDs);
        }catch (RuntimeException e){
            JustExtraDrags.getLogger().error("Error when loading config line {} : {}",configLine,e.getMessage());
            return null;
        }
    }

    @Override
    public String toString(){
        return this.getClass().getName()+";"+this.clazzContainerGui+":"+this.clazzContainer+":"+this.clazzSlot+":"+this.checkFit+":"+this.slotIDs;
    }
}
