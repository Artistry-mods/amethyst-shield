package chaos.amyshield.debug_renderer;

import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.debug.DebugHudEntry;
import net.minecraft.client.gui.hud.debug.DebugHudEntryCategory;
import net.minecraft.client.gui.hud.debug.DebugHudLines;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ChargeF3DebugRenderer implements DebugHudEntry {
    @Override
    public void render(DebugHudLines lines, @Nullable World world, @Nullable WorldChunk clientChunk, @Nullable WorldChunk chunk) {
        lines.addLine(String.format(Locale.ROOT, "Amethyst shield charge %d%%", Math.round(AmethystShieldItem.getCharge((IEntityDataSaver) MinecraftClient.getInstance().player))));
    }

    @Override
    public boolean canShow(boolean reducedDebugInfo) {
        return true;
    }

    @Override
    public DebugHudEntryCategory getCategory() {
        return DebugHudEntry.super.getCategory();
    }
}
