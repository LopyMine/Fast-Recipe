package net.lopymine.fs.mixin;

import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.fs.slot.*;

@Mixin(Slot.class)
public class SlotMixin implements ListenableSlot {

	@Unique
	private SlotListener listener;

	@Override
	public void fastRecipe$setListener(SlotListener listener) {
		this.listener = listener;
	}

	@Inject(at = @At("TAIL"), method = "setStackNoCallbacks")
	private void setStackNoCallbacks(CallbackInfo ci) {
		if (this.listener == null) {
			return;
		}
		this.listener.trigger((Slot) (Object) this);
	}

}
