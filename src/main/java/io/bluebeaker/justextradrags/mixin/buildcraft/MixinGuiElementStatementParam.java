package io.bluebeaker.justextradrags.mixin.buildcraft;

import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.StatementMouseClick;
import buildcraft.lib.gui.statement.GuiElementStatementParam;
import io.bluebeaker.justextradrags.compat.BCCompat;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiElementStatementParam.class,remap = false)
public class MixinGuiElementStatementParam {
    @Redirect(method = "onMouseClicked(I)V",at = @At(value = "INVOKE", target = "Lbuildcraft/api/statements/IStatementParameter;onClick(Lbuildcraft/api/statements/IStatementContainer;Lbuildcraft/api/statements/IStatement;Lnet/minecraft/item/ItemStack;Lbuildcraft/api/statements/StatementMouseClick;)Lbuildcraft/api/statements/IStatementParameter;"))
    public IStatementParameter injectItemstack(IStatementParameter instance, IStatementContainer iStatementContainer, IStatement iStatement, ItemStack stack, StatementMouseClick statementMouseClick){
        if(!BCCompat.lastDraggedStack.isEmpty()){
            stack=BCCompat.lastDraggedStack;
            BCCompat.lastDraggedStack=ItemStack.EMPTY;
        }
        return instance.onClick(iStatementContainer,iStatement,stack,statementMouseClick);
    }
}
