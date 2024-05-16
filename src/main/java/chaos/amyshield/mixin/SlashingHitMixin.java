package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public class SlashingHitMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    protected void tickInject(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (player != null && AmethystShieldItem.getSlashing(((IEntityDataSaver) player))) {
            List<Entity> entityList = new ArrayList<>(player.getWorld().getOtherEntities(player, player.getBoundingBox().expand(AmethystShield.SPARKLING_SLASH_RADIUS)));
            Objects.requireNonNull(player.getServer()).execute(() -> {
                for (Entity entity : entityList) {
                    if (entity instanceof LivingEntity && !((LivingEntity) entity).isDead() && !entity.isRemoved()) {
                        if (entity.damage(player.getDamageSources().playerAttack(player), AmethystShield.SPARKLING_SLASH_DAMAGE)) {
                            AmethystShieldItem.addCharge(((IEntityDataSaver) player), AmethystShield.SPARKLING_SLASH_CHARGE_RETURN);
                            AmethystShieldItem.syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                        }
                    }
                }
            });
        }
    }
}
