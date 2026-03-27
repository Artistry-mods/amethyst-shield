package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.util.IEntityDataSaver;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public class EntityDataSaverMixin implements IEntityDataSaver {
    @Unique
    private AmethystShieldData persistentData;

    @Override
    public AmethystShieldData amethyst_shield$getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new AmethystShieldData();
        }
        return this.persistentData;
    }

    @WrapMethod(method = "addAdditionalSaveData")
    protected void injectWriteMethod(ValueOutput writeView, Operation<Void> original) {
        if (this.persistentData != null) {
            writeView.store(AmethystShield.MOD_ID, IEntityDataSaver.AMETHYST_SHIELD_DATA_CODEC, this.persistentData);
        }

        original.call(writeView);
    }

    @WrapMethod(method = "readAdditionalSaveData")
    protected void injectReadMethod(ValueInput readView, Operation<Void> original) {
        this.persistentData = readView.read(AmethystShield.MOD_ID, IEntityDataSaver.AMETHYST_SHIELD_DATA_CODEC).orElse(new AmethystShieldData());

        original.call(readView);
    }
}