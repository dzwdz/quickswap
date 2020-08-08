package dzwdz.quickswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class QuickSwap implements ClientModInitializer {
	private KeyBinding[] keysQS;
	private boolean[] lastState = {false, false, false, false, false, false, false, false, false};
	private int lastSlot;

	@Override
	public void onInitializeClient() {
		keysQS = new KeyBinding[9];

		for (int i = 0; i < 9; i++) {
			keysQS[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.dzwdz.quickswap.qs."+Integer.toString(i+1), InputUtil.UNKNOWN_KEY.getCode(), "key.categories.inventory"));
		}

		ClientTickCallback.EVENT.register(client -> {
			for(int i = 0; i < 9; i++) {
				if (this.keysQS[i].isPressed() != lastState[i]) {
					if (this.keysQS[i].isPressed()) {
						lastSlot = client.player.inventory.selectedSlot;
						client.player.inventory.selectedSlot = i;
					} else {
						client.player.inventory.selectedSlot = lastSlot;
					}
					lastState[i] ^= true;
				}
			}
		});
	}
}
