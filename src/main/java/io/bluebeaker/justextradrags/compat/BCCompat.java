package io.bluebeaker.justextradrags.compat;

import buildcraft.lib.gui.slot.SlotPhantom;
import buildcraft.silicon.gui.GuiGate;
import buildcraft.transport.container.ContainerDiamondPipe;
import buildcraft.transport.container.ContainerDiamondWoodPipe;
import buildcraft.transport.container.ContainerEmzuliPipe_BC8;
import buildcraft.transport.gui.GuiDiamondPipe;
import buildcraft.transport.gui.GuiDiamondWoodPipe;
import buildcraft.transport.gui.GuiEmzuliPipe_BC8;
import io.bluebeaker.justextradrags.JustExtraDragsConfig;
import io.bluebeaker.justextradrags.config.ConfigEntry;
import io.bluebeaker.justextradrags.config.JXDConfigManager;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;

public class BCCompat {
    public static ItemStack lastDraggedStack = ItemStack.EMPTY;

    public static void addBuildcraftCompat(){
        Class guiDiamondPipe = null;
        Class guiDiamondWoodPipe = null;
        Class guiEmzuliPipe = null;
        Class guiGate = null;
        if (JustExtraDragsConfig.BCTransport&& Loader.isModLoaded("buildcrafttransport"))
        {
            if(FMLCommonHandler.instance().getSide().isClient()){
                guiDiamondPipe= GuiDiamondPipe.class;
                guiDiamondWoodPipe= GuiDiamondWoodPipe.class;
                guiEmzuliPipe= GuiEmzuliPipe_BC8.class;
            }
            JXDConfigManager.addEntry(new ConfigEntry(guiDiamondPipe, ContainerDiamondPipe.class,SlotPhantom.class,false));

            JXDConfigManager.addEntry(new ConfigEntry(guiDiamondWoodPipe, ContainerDiamondWoodPipe.class,SlotPhantom.class,false));

            JXDConfigManager.addEntry(new ConfigEntry(guiEmzuliPipe, ContainerEmzuliPipe_BC8.class,SlotPhantom.class,false));
        }
    }
    public static void registerToJEI(IModRegistry registry){
        if (JustExtraDragsConfig.BCSilicon&&Loader.isModLoaded("buildcraftsilicon")) {
            registry.addGhostIngredientHandler(GuiGate.class,new BCGateGhostHandler());
        }
    }
}
