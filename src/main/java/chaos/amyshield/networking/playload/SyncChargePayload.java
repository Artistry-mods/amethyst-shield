package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SyncChargePayload(float chargeAmount) implements CustomPayload {
    public static final CustomPayload.Id<SyncChargePayload> ID = new CustomPayload.Id<>(ModPackets.SYNC_CHARGE_S2C);
    public static final PacketCodec<RegistryByteBuf, SyncChargePayload> CODEC = PacketCodec.tuple(PacketCodecs.FLOAT, SyncChargePayload::chargeAmount, SyncChargePayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
