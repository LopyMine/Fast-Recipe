package net.lopymine.fs.client;

import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.fs.FastRecipe;
import net.lopymine.fs.slot.SlotListener;

import org.jetbrains.annotations.Nullable;

public class FastRecipeClient implements ClientModInitializer {

	@Setter
	@Getter
	@Nullable
	private static Item waitingResult;

	public static SlotListener getSlotListener() {
		return (slot) -> {
			Item waitingResult = FastRecipeClient.getWaitingResult();
			if (waitingResult == null) {
				return;
			}
			ItemStack stack = slot.getStack();
			if (stack == null || stack.isEmpty() || stack.getItem() != waitingResult) {
				return;
			}
			MinecraftClient client = MinecraftClient.getInstance();
			ClientPlayerEntity player = client.player;
			if (player == null) {
				FastRecipeClient.setWaitingResult(null);
				return;
			}
			ClientPlayerInteractionManager manager = client.interactionManager;
			if (manager == null) {
				FastRecipeClient.setWaitingResult(null);
				return;
			}
			if (!(player.currentScreenHandler instanceof AbstractRecipeScreenHandler)) {
				FastRecipeClient.setWaitingResult(null);
				return;
			}
			manager.clickSlot(player.currentScreenHandler.syncId, 0, 0, SlotActionType.QUICK_MOVE, player);
			FastRecipeClient.setWaitingResult(null);
		};
	}

	public static boolean canStartWaiting(Item waitingResult) {
		Screen currentScreen = MinecraftClient.getInstance().currentScreen;
		if (!(currentScreen instanceof InventoryScreen || currentScreen instanceof CraftingScreen)) {
			return false;
		}
		HandledScreen<?> handledScreen = (HandledScreen<?>) currentScreen;
		ScreenHandler screenHandler = handledScreen.getScreenHandler();
		Slot slot = screenHandler.getSlot(0);
		return slot != null && slot.getStack().getItem() != waitingResult;
	}

	@Override
	public void onInitializeClient() {
		System.out.println(FastRecipe.MOD_NAME + " Client Initialized");
	}
}
