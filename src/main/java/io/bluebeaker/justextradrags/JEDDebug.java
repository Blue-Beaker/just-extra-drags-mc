package io.bluebeaker.justextradrags;

import java.util.HashMap;

import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JEDDebug {
    public static final JEDDebug INSTANCE = new JEDDebug();
    public static String lastContainerName = "";
    private JEDDebug(){
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onGuiEvent(GuiContainerEvent.DrawForeground event){
        if(JustExtraDragsConfig.debug){
            String containerName = event.getGuiContainer().getClass().getName();
            if(containerName==lastContainerName) return;
            lastContainerName=containerName;
            JustExtraDrags.getInstance().logInfo("Opened Container: "+containerName);
            HashMap<String,Integer> slots = new HashMap<String,Integer>();
            for(Slot slot:event.getGuiContainer().inventorySlots.inventorySlots){
                String name = slot.getClass().getName();
                if(!slots.containsKey(name))
                slots.put(name, 0);
                slots.put(name, slots.get(name)+1);
            }
            for(String name:slots.keySet()){
                JustExtraDrags.getInstance().logInfo(name+" x "+slots.get(name));
            }
        }
    }
}
