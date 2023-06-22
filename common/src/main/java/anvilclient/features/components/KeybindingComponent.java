package anvilclient.features.components;

import anvilclient.features.Feature;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeybindingComponent extends BaseComponent {

    public KeybindingComponent(Feature parentFeature, KeyMapping keyMapping, Runnable keyAction) {
        super(parentFeature);
        this.keyMapping = keyMapping;
        this.keyAction = keyAction;
    }

    private final KeyMapping keyMapping;
    private final Runnable keyAction;

    @Override
    public void register() {
        ClientTickEvent.CLIENT_POST.register(this::tick);
    }

    private void tick(Minecraft minecraft) {
        while (keyMapping.consumeClick()) {
            keyAction.run();
        }
    }
}
