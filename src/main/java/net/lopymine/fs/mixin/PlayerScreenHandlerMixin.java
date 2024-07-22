package net.lopymine.fs.mixin;

import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.fs.client.FastRecipeClient;
import net.lopymine.fs.slot.ListenableSlot;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 0), method = "<init>")
	private Slot startListeningResultSlot(Slot originalSlot) {
		((ListenableSlot) originalSlot).fastRecipe$setListener(FastRecipeClient.getSlotListener());
		return originalSlot;
	}
}
