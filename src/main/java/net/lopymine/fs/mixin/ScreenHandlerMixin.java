package net.lopymine.fs.mixin;

import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.fs.client.FastRecipeClient;
import net.lopymine.fs.slot.ListenableSlot;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {

	@Inject(at = @At("HEAD"), method = "addSlot")
	private void startListening(Slot slot, CallbackInfoReturnable<Slot> cir) {
		ScreenHandler o = (ScreenHandler) (Object) (this);
		boolean bl = slot.id == 0;
		boolean bl2 = o instanceof AbstractRecipeScreenHandler;
		if (!bl || !bl2) {
			return;
		}
		((ListenableSlot) slot).fastRecipe$setListener(FastRecipeClient.getSlotListener());
	}

}
