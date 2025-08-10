package chaos.amyshield.util;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

public interface IEntityDataSaver {
    AmethystShieldData amethyst_shield$getPersistentData();

    class AmethystShieldData {
        float charge;
        boolean slashing;

        public AmethystShieldData() {
            this.charge = 0;
            this.slashing = false;
        }

        public void setCharge(float charge) {
            this.charge = charge;
        }

        public void setSlashing(boolean slashing) {
            this.slashing = slashing;
        }

        public float getCharge() {
            return charge;
        }

        public boolean isSlashing() {
            return slashing;
        }
    }

    PrimitiveCodec<AmethystShieldData> AMETHYST_SHIELD_DATA_CODEC = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<AmethystShieldData> read(final DynamicOps<T> ops, final T input) {
            AmethystShieldData data = new AmethystShieldData();

            data.setCharge(ops.getNumberValue(input, 0f).floatValue());

            DataResult<Boolean> result = ops.getBooleanValue(input);

            if (result.isError()) {
                return DataResult.error(() -> "Failed to read slashing state from input: " + result.error().get().message());
            }

            data.setSlashing(result.result().orElse(false));

            return DataResult.success(data);
        }

        @Override
        public <T> T write(final DynamicOps<T> ops, final AmethystShieldData value) {
            ops.createNumeric(value.getCharge());
            return ops.createBoolean(value.isSlashing());
        }

        @Override
        public String toString() {
            return "AmethystShieldData";
        }
    };
}
