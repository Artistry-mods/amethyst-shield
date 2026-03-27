package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record SyncSlashPayload(Boolean isSlashing) implements CustomPacketPayload {
    public static final Type<SyncSlashPayload> ID = new Type<>(ModPackets.SYNC_SLASHING_S2C);
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncSlashPayload> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, SyncSlashPayload::isSlashing, SyncSlashPayload::new);
    // should you need to send more data, add the appropriate record parameters and change your codec:
    // public static final PacketCodec<RegistryByteBuf, BlockHighlightPayload> CODEC = PacketCodec.tuple(
    //         BlockPos.PACKET_CODEC, BlockHighlightPayload::blockPos,
    //         PacketCodecs.INTEGER, BlockHighlightPayload::myInt,
    //         Uuids.PACKET_CODEC, BlockHighlightPayload::myUuid,
    //         BlockHighlightPayload::new
    // );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
