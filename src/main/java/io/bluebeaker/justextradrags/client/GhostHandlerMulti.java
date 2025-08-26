package io.bluebeaker.justextradrags.client;

import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GhostHandlerMulti <T extends GuiContainer> implements IGhostIngredientHandler<T> {
    public final List<IGhostIngredientHandler<T>> handlers = new ArrayList<>();

    @Override
    public <I> List<Target<I>> getTargets(T t, I i, boolean b) {
        List<Target<I>> targets = new ArrayList<>();
        for (IGhostIngredientHandler<T> handler : handlers) {
            targets.addAll(handler.getTargets(t,i,b));
        }
        return targets;
    }

    @Override
    public void onComplete() {
        for (IGhostIngredientHandler<T> handler : handlers) {
            handler.onComplete();
        }
    }
}
