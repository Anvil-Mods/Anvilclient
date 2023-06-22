package anvilclient.fabric;

import anvilclient.util.utils.ScreenUtils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class AnvilclientModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ScreenUtils::getMainConfigGui;
    }
}
