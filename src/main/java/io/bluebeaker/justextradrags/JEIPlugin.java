package io.bluebeaker.justextradrags;

import buildcraft.lib.gui.slot.SlotPhantom;
import buildcraft.silicon.gui.GuiGate;
import buildcraft.transport.gui.GuiDiamondPipe;
import buildcraft.transport.gui.GuiDiamondWoodPipe;
import buildcraft.transport.gui.GuiEmzuliPipe_BC8;
import io.bluebeaker.justextradrags.compat.AltGhostHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraftforge.fml.common.Loader;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        if (JustExtraDragsConfig.BCTransport&& Loader.isModLoaded("buildcrafttransport"))
        {
            registry.addGhostIngredientHandler(GuiDiamondPipe.class, new AltGhostHandler<GuiDiamondPipe>(SlotPhantom.class));
            registry.addGhostIngredientHandler(GuiDiamondWoodPipe.class, new AltGhostHandler<GuiDiamondWoodPipe>(SlotPhantom.class));
            registry.addGhostIngredientHandler(GuiEmzuliPipe_BC8.class, new AltGhostHandler<GuiEmzuliPipe_BC8>(SlotPhantom.class));
        }
        if (JustExtraDragsConfig.BCSilicon&&Loader.isModLoaded("buildcraftsilicon"))
        registry.addGhostIngredientHandler(GuiGate.class, new AltGhostHandler<GuiGate>(SlotPhantom.class));
        if(JustExtraDragsConfig.customEntries.length>0){
            CustomEntries.register(registry);
        }
    }
}