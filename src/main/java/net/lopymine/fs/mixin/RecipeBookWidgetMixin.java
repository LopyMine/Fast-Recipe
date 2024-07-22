package net.lopymine.fs.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.fs.client.FastRecipeClient;

@Mixin(RecipeBookWidget.class)
public class RecipeBookWidgetMixin {

	@Shadow @Final private RecipeBookResults recipesArea;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;isWide()Z"), method = "mouseClicked")
	private void init(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
		RecipeResultCollection lastClickedResults = this.recipesArea.getLastClickedResults();
		/*? >=1.20.2 {*/ net.minecraft.recipe.RecipeEntry<?> /*?} else {*/ /*net.minecraft.recipe.Recipe<?> *//*?}*/
		lastClickedRecipe = this.recipesArea.getLastClickedRecipe();

		if (lastClickedResults == null) {
			return;
		}
		if (lastClickedRecipe == null) {
			return;
		}
		//? >=1.20.2 {
		ItemStack result = lastClickedRecipe.value().getResult(lastClickedResults.getRegistryManager());
		//?} else {
		/*ItemStack result = lastClickedRecipe.getOutput(/^? >=1.19.4 {^/ /^lastClickedResults.getRegistryManager() ^//^?}^/);
		*///?}

		if (result == null || result.isEmpty()) {
			return;
		}
		Item item = result.getItem();
		if (!Screen.hasControlDown() || !FastRecipeClient.canStartWaiting(item)) {
			return;
		}
		FastRecipeClient.setWaitingResult(item);
	}
}