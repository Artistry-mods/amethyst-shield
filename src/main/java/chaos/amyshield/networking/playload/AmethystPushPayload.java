package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record AmethystPushPayload(Boolean nothingbutthisshitcantbeempty) implements CustomPayload {
    public static final Id<AmethystPushPayload> ID = new Id<>(ModPackets.AMETHYST_PUSH_ABILITY_C2S);
    public static final PacketCodec<RegistryByteBuf, AmethystPushPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOLEAN, AmethystPushPayload::nothingbutthisshitcantbeempty, AmethystPushPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
