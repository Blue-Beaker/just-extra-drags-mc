package io.bluebeaker.justextradrags.mixin;

import com.warmthdawn.justenoughdrags.jei.JEIPlugin;
import io.bluebeaker.justextradrags.JustExtraDragsConfig;
import mezz.jei.api.IModRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JEIPlugin.class,remap = false)
public class MixinJustEnoughDragsJEIPlugin {
    @Inject(method = "register", at = @At("HEAD"),cancellable = true)
    public void cancelServerChecks(IModRegistry registry, CallbackInfo ci){
        if(JustExtraDragsConfig.serverSidePatches)
            ci.cancel();
    }
}
