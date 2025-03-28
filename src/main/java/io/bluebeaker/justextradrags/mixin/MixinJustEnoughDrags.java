package io.bluebeaker.justextradrags.mixin;

import com.warmthdawn.justenoughdrags.JustEnoughDrags;
import io.bluebeaker.justextradrags.JustExtraDragsConfig;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JustEnoughDrags.class,remap = false)
public class MixinJustEnoughDrags {
    @Inject(method = "preInit(Lnet/minecraftforge/fml/common/event/FMLPreInitializationEvent;)V", at = @At(value = "INVOKE", target = "Lcom/warmthdawn/justenoughdrags/compact/bm2/BMReflectionHelper;init()V"), cancellable = true)
    public void cancelServerChecks(FMLPreInitializationEvent event, CallbackInfo ci){
        if(JustExtraDragsConfig.serverSidePatches)
            ci.cancel();
    }
}
