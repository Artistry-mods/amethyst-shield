package chaos.amyshield.networking.playload;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class SetChargePayload implements CustomPayload {
    @Override
    public Id<? extends CustomPayload> getId() {
        return new Id<>(Identifier.of("amyshield", "setcharge"));
    }
}
