package net.kami.godmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class boatfly implements ClientModInitializer {

    private static KeyBinding upKey;
    private static KeyBinding downKey;

    @Override
    public void onInitializeClient() {
        upKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.boatfly.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.boatfly"
        ));
        downKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.boatfly.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.boatfly"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null
                    && client.player.hasVehicle()
                    && client.player.getVehicle() instanceof BoatEntity boat) {

                boat.setNoGravity(true);

                double x = 0, y = 0, z = 0;

                if (upKey.isPressed()) y = 0.5;
                else if (downKey.isPressed()) y = -0.5;

                Input input = client.player.input;
                if (input != null && input.hasForwardMovement()) {
                    double yaw = Math.toRadians(client.player.getYaw());
                    x = -Math.sin(yaw) * 0.5;
                    z = Math.cos(yaw) * 0.5;
                }

                boat.setVelocity(new Vec3d(x, y, z));
            }
        });
    }
}
