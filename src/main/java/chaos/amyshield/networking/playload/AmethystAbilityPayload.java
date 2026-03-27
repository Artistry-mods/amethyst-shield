package chaos.amyshield.networking.playload;

import chaos.amyshield.networking.ModPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record AmethystAbilityPayload(String abilityIdentifier) implements CustomPacketPayload {
    public static final Type<AmethystAbilityPayload> ID = new Type<>(ModPackets.AMETHYST_ABILITY_C2S);
    public static final StreamCodec<RegistryFriendlyByteBuf, AmethystAbilityPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, AmethystAbilityPayload::abilityIdentifier,
            AmethystAbilityPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
