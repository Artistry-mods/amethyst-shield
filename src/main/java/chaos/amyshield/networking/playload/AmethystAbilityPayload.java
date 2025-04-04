package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record AmethystAbilityPayload(Float chargeAmount, Boolean flatParticles, Boolean notFlatParticles, Boolean sound) implements CustomPayload {
    public static final Id<AmethystAbilityPayload> ID = new Id<>(ModPackets.AMETHYST_ABILITY_C2S);
    public static final PacketCodec<RegistryByteBuf, AmethystAbilityPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, AmethystAbilityPayload::chargeAmount,
            PacketCodecs.BOOLEAN, AmethystAbilityPayload::flatParticles,
            PacketCodecs.BOOLEAN, AmethystAbilityPayload::notFlatParticles,
            PacketCodecs.BOOLEAN, AmethystAbilityPayload::sound,
            AmethystAbilityPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
