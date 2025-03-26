package net.lopymine.fs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.fs.client.FastRecipeClient;

import java.util.*;

@Mixin(RecipeBookWidget.class)
public class RecipeBookWidgetMixin {

	@Shadow @Final private RecipeBookResults recipesArea;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;isWide()Z"), method = "mouseClicked")
	private void init(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {

		RecipeResultCollection lastClickedResults = this.recipesArea.getLastClickedResults();

		//? if >=1.21.2 {
		net.minecraft.recipe.NetworkRecipeId
		//?} elif >=1.20.2 {
		/*net.minecraft.recipe.RecipeEntry
		*///?} else {
		/*net.minecraft.recipe.Recipe<?>
		*///?}
				lastClickedRecipe = this.recipesArea.getLastClickedRecipe();

		if (lastClickedResults == null) {
			return;
		}
		if (lastClickedRecipe == null) {
			return;
		}

		//? if >=1.21.2 {
		net.minecraft.util.context.ContextParameterMap parameters = net.minecraft.recipe.display.SlotDisplayContexts.createParameters(Objects.requireNonNull(MinecraftClient.getInstance().world));
		List<Item> result = lastClickedResults.filter(net.minecraft.client.gui.screen.recipebook.RecipeResultCollection.RecipeFilterMode.CRAFTABLE)
				.stream()
				.map(net.minecraft.recipe.RecipeDisplayEntry::display)
				.map(net.minecraft.recipe.display.RecipeDisplay::result)
				.map((slotDisplay) -> slotDisplay.getStacks(parameters))
				.flatMap(List::stream)
				.map(ItemStack::getItem)
				.toList();
		//?} elif >=1.20.2 {
		/*List<Item> result = List.of(lastClickedRecipe.value().getResult(lastClickedResults.getRegistryManager()).getItem());
		*///?} else {
		/*List<Item> result = List.of(lastClickedRecipe.getOutput(/^? >=1.19.4 {^/ /^lastClickedResults.getRegistryManager() ^//^?}^/).getItem());
		*///?}

		if (result.isEmpty()) {
			return;
		}
		if (!Screen.hasControlDown() || !FastRecipeClient.canStartWaiting(result)) {
			return;
		}
		FastRecipeClient.setWaitingResult(result);
	}
}