package anvilclient.anvilclient.util;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybinds
{
	private final static String keyCategory = "anvilclient.key.categories.anvilclient";
	
    public static final KeyBinding openSettings =  new KeyBinding("anvilclient.key.openSettings", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, keyCategory);
    public static final KeyBinding fullbright = new KeyBinding("anvilclient.configGui.fullbright.toggle", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
    public static final KeyBinding coordinates = new KeyBinding("anvilclient.configGui.coordinates.toggle", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
    
    public static void register() {
		ClientRegistry.registerKeyBinding(openSettings);
		ClientRegistry.registerKeyBinding(fullbright);
		ClientRegistry.registerKeyBinding(coordinates);
	}
}