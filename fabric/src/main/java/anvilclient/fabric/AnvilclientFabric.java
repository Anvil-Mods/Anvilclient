package anvilclient.fabric;

import anvilclient.AnvilclientCommon;
import net.fabricmc.api.ClientModInitializer;

public class AnvilclientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AnvilclientCommon.init();
    }
}
