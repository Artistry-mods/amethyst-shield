package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;

public record IgnoreFallDamagePayload(Vec3d till) implements CustomPayload {
    public static final Id<IgnoreFallDamagePayload> ID = new Id<>(ModPackets.IGNORE_FALL_DAMAGE_ABILITY_C2S);
    public static final PacketCodec<RegistryByteBuf, IgnoreFallDamagePayload> CODEC = PacketCodec.tuple(Vec3d.PACKET_CODEC, IgnoreFallDamagePayload::till, IgnoreFallDamagePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
