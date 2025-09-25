package chaos.amyshield.util;

import com.google.common.collect.ImmutableMap;
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

            ops.getMap(input).flatMap(map ->
                            ops.getNumberValue(map.get(ops.createString("charge"))))
                    .map(Number::floatValue)
                    .result().ifPresent(data::setCharge);

            return DataResult.success(data);
        }

        @Override
        public <T> T write(final DynamicOps<T> ops, final AmethystShieldData value) {
            T chargeValue = ops.createNumeric(value.getCharge());

            return ops.createMap(ImmutableMap.of(
                    ops.createString("charge"), chargeValue
            ));
        }

        @Override
        public String toString() {
            return "AmethystShieldData";
        }
    };
}
