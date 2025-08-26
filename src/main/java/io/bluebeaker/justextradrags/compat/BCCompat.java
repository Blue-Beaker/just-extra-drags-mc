package io.bluebeaker.justextradrags.compat;

import buildcraft.lib.gui.slot.SlotPhantom;
import buildcraft.silicon.container.ContainerGate;
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
import net.minecraftforge.fml.common.Loader;

public class BCCompat {
    public static void addBuildcraftCompat(){
        if (JustExtraDragsConfig.BCTransport&& Loader.isModLoaded("buildcrafttransport"))
        {
            JXDConfigManager.addEntry(new ConfigEntry(GuiDiamondPipe.class, ContainerDiamondPipe.class,SlotPhantom.class,true));

            JXDConfigManager.addEntry(new ConfigEntry(GuiDiamondWoodPipe.class, ContainerDiamondWoodPipe.class,SlotPhantom.class,true));

            JXDConfigManager.addEntry(new ConfigEntry(GuiEmzuliPipe_BC8.class, ContainerEmzuliPipe_BC8.class,SlotPhantom.class,true));
        }
        if (JustExtraDragsConfig.BCSilicon&&Loader.isModLoaded("buildcraftsilicon")) {
            JXDConfigManager.addEntry(new ConfigEntry(GuiGate.class, ContainerGate.class,SlotPhantom.class,true));
        }
    }
}
