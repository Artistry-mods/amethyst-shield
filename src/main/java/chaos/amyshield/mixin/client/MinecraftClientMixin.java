package chaos.amyshield.mixin.client;

import chaos.amyshield.util.IMinecraftClientDatasaver;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements IMinecraftClientDatasaver {
    @Unique
    int lastAttackTick = 0;

    @Override
    public int amethyst_shield$getLastAttackTick() {
        return lastAttackTick;
    }

    @Override
    public void amethyst_shield$setLastAttackTick(int tick) {
        this.lastAttackTick = tick;
    }

    @WrapMethod(method = "doAttack")
    public boolean amyshield$doAttack(Operation<Boolean> original) {
        this.amethyst_shield$setLastAttackTick(20);

        return original.call();
    }

    @WrapMethod(method = "tick")
    public void amyshield$tick(Operation<Void> original) {
        if (this.amethyst_shield$getLastAttackTick() > 0) {
            this.amethyst_shield$setLastAttackTick(this.amethyst_shield$getLastAttackTick() - 1);
        }

        original.call();
    }
}
