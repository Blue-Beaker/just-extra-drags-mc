package io.bluebeaker.justextradrags;

import java.util.HashMap;

import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
@SideOnly(Side.CLIENT)
public class JEDDebug {
    public static JEDDebug INSTANCE = null;
    public static String lastContainerName = "";

    public JEDDebug() {
        INSTANCE = this;
    }

    public static JEDDebug getInstance() {
        if (INSTANCE != null)
            return INSTANCE;
        else
            return new JEDDebug();
    }

    @SubscribeEvent
    public static void onGuiEvent(GuiContainerEvent.DrawForeground event) {
        if (!JustExtraDragsConfig.debug)
            return;
        String containerName = event.getGuiContainer().getClass().getName();
        if (containerName == lastContainerName)
            return;
        lastContainerName = containerName;
        JustExtraDrags.getLogger().info("Opened Container: " + containerName);
        HashMap<String, Integer> slots = new HashMap<String, Integer>();
        for (Slot slot : event.getGuiContainer().inventorySlots.inventorySlots) {
            String name = slot.getClass().getName();
            if (!slots.containsKey(name))
                slots.put(name, 0);
            slots.put(name, slots.get(name) + 1);
        }
        for (String name : slots.keySet()) {
            JustExtraDrags.getLogger().info(name + " x " + slots.get(name));
        }
    }
}
