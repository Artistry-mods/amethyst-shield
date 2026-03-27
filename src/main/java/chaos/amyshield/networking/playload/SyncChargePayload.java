package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncChargePayload(Float chargeAmount) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncChargePayload> ID = new CustomPacketPayload.Type<>(ModPackets.SYNC_CHARGE_S2C);
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncChargePayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.FLOAT,
                    SyncChargePayload::chargeAmount,
                    SyncChargePayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
