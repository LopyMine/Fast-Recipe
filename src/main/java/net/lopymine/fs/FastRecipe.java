package net.lopymine.fs;

import org.slf4j.*;

import net.fabricmc.api.ModInitializer;

public class FastRecipe implements ModInitializer {

	public static final String MOD_NAME = /*$ mod_name*/ "Fast Recipe";
	public static final Logger LOGGER = LoggerFactory.getLogger("Fast Recipe Client");

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_NAME + " Initialized");
	}
}