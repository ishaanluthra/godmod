package net.kami.godmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class GoatmodClient implements ClientModInitializer {

    private static KeyBinding toggleFlyKey;
    private boolean flyEnabled = false;

    @Override
    public void onInitializeClient() {

        toggleFlyKey = new KeyBinding(
                "key.flymod.togglefly",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "category.flymod"
        );

        // Register keybind (optional in recent versions)
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(toggleFlyKey);

        // Add tick listener
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleFlyKey.wasPressed()) {
                flyEnabled = !flyEnabled;
                if (client.player != null) {
                    client.player.getAbilities().allowFlying = flyEnabled;
                    client.player.sendMessage(
                            net.minecraft.text.Text.literal("Flying: " + (flyEnabled ? "Enabled" : "Disabled")),
                            true
                    );
                }
            }

            // Optional: prevent fall damage
            if (flyEnabled && client.player != null) {
                client.player.getAbilities().flying = true;
                client.player.fallDistance = 0;
            }
        });

    }
}
