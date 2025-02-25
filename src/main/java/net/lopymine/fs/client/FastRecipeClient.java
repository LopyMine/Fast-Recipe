package net.lopymine.fs.client;

import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import org.slf4j.*;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.fs.FastRecipe;
import net.lopymine.fs.slot.SlotListener;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class FastRecipeClient implements ClientModInitializer {

	@Setter
	@Getter
	@Nullable
	private static List<Item> waitingResult;

	public static final Logger LOGGER = LoggerFactory.getLogger("Fast Recipe Client");

	public static SlotListener getSlotListener() {
		return (slot) -> {
			List<Item> waitingResult = FastRecipeClient.getWaitingResult();
			if (waitingResult == null) {
				return;
			}
			ItemStack stack = slot.getStack();
			if (stack == null || stack.isEmpty() || !waitingResult.contains(stack.getItem())) {
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

	public static boolean canStartWaiting(List<Item> waitingResult) {
		Screen currentScreen = MinecraftClient.getInstance().currentScreen;
		if (!(currentScreen instanceof InventoryScreen || currentScreen instanceof CraftingScreen)) {
			return false;
		}
		HandledScreen<?> handledScreen = (HandledScreen<?>) currentScreen;
		ScreenHandler screenHandler = handledScreen.getScreenHandler();
		if (!(screenHandler instanceof AbstractRecipeScreenHandler)) {
			return false;
		}
		Slot slot = screenHandler.getSlot(0);
		return slot != null && !waitingResult.contains(slot.getStack().getItem());
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info(FastRecipe.MOD_NAME + " Client Initialized");
	}
}
