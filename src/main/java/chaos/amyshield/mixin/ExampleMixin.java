package chaos.amyshield.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "tickMovement")
	private void tickMovement(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
}