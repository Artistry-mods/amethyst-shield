package chaos.amyshield.debug_renderer;

import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Locale;

public class ChargeF3DebugRenderer implements DebugScreenEntry {
    @Override
    public void display(@NonNull DebugScreenDisplayer lines, @Nullable Level world, @Nullable LevelChunk clientChunk, @Nullable LevelChunk chunk) {
        if (Minecraft.getInstance().player != null) {
            lines.addLine(String.format(Locale.ROOT, "Amethyst shield charge %d%%", Math.round(AmethystShieldItem.getCharge((IEntityDataSaver) Minecraft.getInstance().player))));
        }
    }

    @Override
    public boolean isAllowed(boolean reducedDebugInfo) {
        return true;
    }

    @Override
    public @NonNull DebugEntryCategory category() {
        return DebugScreenEntry.super.category();
    }
}
