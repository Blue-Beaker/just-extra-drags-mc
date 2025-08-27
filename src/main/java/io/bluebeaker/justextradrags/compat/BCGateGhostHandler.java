package io.bluebeaker.justextradrags.compat;

import buildcraft.api.statements.StatementParameterItemStack;
import buildcraft.lib.gui.IGuiElement;
import buildcraft.lib.gui.elem.GuiElementContainerResizing;
import buildcraft.lib.gui.statement.GuiElementStatementParam;
import buildcraft.silicon.gui.GuiGate;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BCGateGhostHandler implements IGhostIngredientHandler<GuiGate> {

    @Override
    public <I> List<Target<I>> getTargets(GuiGate guiGate, I i, boolean b) {
        List<Target<I>> targets = new ArrayList<>();
        for (IGuiElement element1 : guiGate.mainGui.shownElements) {
            if(element1 instanceof GuiElementContainerResizing){
                for (IGuiElement element2 : ((GuiElementContainerResizing)element1).getChildElements()) {
                    if(element2 instanceof GuiElementContainerResizing){
                        for (IGuiElement element3 : ((GuiElementContainerResizing) element2).getChildElements()) {
                            if(element3 instanceof GuiElementStatementParam){
                                GuiElementStatementParam statementParam = (GuiElementStatementParam) element3;
                                if(statementParam.get() instanceof StatementParameterItemStack)
                                    targets.add(new BCGhostTarget<>(statementParam,0,0));
                            }
                        }

                    }
                }
            }
        }

        return targets;
    }

    @Override
    public void onComplete() {

    }

    protected static class BCGhostTarget<I> implements Target<I> {
        protected final Rectangle rectangle;
        protected final IGuiElement element;

        public BCGhostTarget(IGuiElement element, int xoff, int yoff) {
            this.rectangle = new Rectangle((int) (Math.round(element.getX()) + xoff), (int) (Math.round(element.getY()) + yoff), (int) Math.round(element.getWidth()), (int) Math.round(element.getHeight()));
            this.element = element;
        }

        @Override
        public Rectangle getArea() {
            return rectangle;
        }

        @Override
        public void accept(I ingredient) {
            GuiElementStatementParam param = (GuiElementStatementParam) element;
            BCCompat.lastDraggedStack = ((ItemStack) ingredient).copy();
            param.onMouseClicked(0);
        }
    }
}
