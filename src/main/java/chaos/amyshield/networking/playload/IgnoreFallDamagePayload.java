package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.phys.Vec3;

public record IgnoreFallDamagePayload(Vec3 till) implements CustomPacketPayload {
    public static final Type<IgnoreFallDamagePayload> ID = new Type<>(ModPackets.IGNORE_FALL_DAMAGE_ABILITY_C2S);
    public static final StreamCodec<RegistryFriendlyByteBuf, IgnoreFallDamagePayload> CODEC = StreamCodec.composite(Vec3.STREAM_CODEC, IgnoreFallDamagePayload::till, IgnoreFallDamagePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
