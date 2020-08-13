package dzwdz.quickswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class QuickSwap implements ClientModInitializer {
    private KeyBinding[] hotbarBinds;
    private boolean[] lastHotbarState = {false, false, false, false, false, false, false, false, false};
    private int lastSlot;

    private KeyBinding[] camBinds;
    private boolean[] lastCamState = {false, false, false};
    private int lastCam;

    @Override
    public void onInitializeClient() {
        hotbarBinds = new KeyBinding[9];
        for (int i = 0; i < 9; i++) {
            hotbarBinds[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.dzwdz.quickswap.qs."+Integer.toString(i+1),
                                                                                InputUtil.UNKNOWN_KEY.getCode(),
                                                                                "key.categories.dzwdz.quickswap"));
        }

        camBinds = new KeyBinding[3];
        for (int i = 0; i < 3; i++) {
            camBinds[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.dzwdz.quickswap.cam."+Integer.toString(i+1),
                                                                            InputUtil.UNKNOWN_KEY.getCode(),
                                                                            "key.categories.dzwdz.quickswap"));
        }

        ClientTickCallback.EVENT.register(client -> {
            // handle hotbar swaps
            for (int i = 0; i < 9; i++) {
                if (this.hotbarBinds[i].isPressed() != lastHotbarState[i]) {
                    if (this.hotbarBinds[i].isPressed()) {
                        lastSlot = client.player.inventory.selectedSlot;
                        client.player.inventory.selectedSlot = i;
                    } else {
                        client.player.inventory.selectedSlot = lastSlot;
                    }
                    lastHotbarState[i] ^= true;
                }
            }

            // handle perspective swaps
            for (int i = 0; i < 3; i++) {
                if (this.camBinds[i].isPressed() != lastCamState[i]) {
                    if (this.camBinds[i].isPressed()) {
                        lastCam = client.options.perspective;
                        client.options.perspective = i;
                    } else {
                        client.options.perspective = lastCam;
                    }
                    lastCamState[i] ^= true;
                }
            }
        });
    }
}
