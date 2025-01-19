package chaos.amyshield.mixin.client;

import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Locale;

@Mixin(DebugHud.class)
public abstract class F3ChargePercentageMixin {
    @Shadow @Final private MinecraftClient client;


    @Inject(at = @At("RETURN"), method = "getLeftText")
    protected void getLeftText(CallbackInfoReturnable<List<String>> info) {
        info.getReturnValue().add(String.format(Locale.ROOT, "Amethyst shield charge %d%%", Math.round(AmethystShieldItem.getCharge((IEntityDataSaver) this.client.player))));
    }
}
