package anvilclient.features.components;

import anvilclient.features.Feature;
import anvilclient.settings.BooleanSetting;
import anvilclient.settings.Setting;

import java.util.function.Consumer;

public class ToggleComponent extends BaseComponent {

    @Setting
    public final BooleanSetting enabled;

    private final Consumer<Boolean> updateHook;

    public ToggleComponent(Feature parentFeature, String toggleName, String description, boolean defaultValue, Consumer<Boolean> updateHook) {
        super(parentFeature);
        enabled = new BooleanSetting(parentFeature.getName() + ".toggle." + toggleName, description, defaultValue);
        this.updateHook = updateHook;
    }

    public ToggleComponent(Feature parentFeature, String toggleName, String description, boolean defaultValue) {
        this(parentFeature, toggleName, description, defaultValue, (b) -> {});
    }

    public void setEnabled(Boolean newEnabled) {
        this.enabled.setValue(newEnabled);
    }

    public boolean isEnabled() {
        return this.enabled.getValue();
    }

    public void toggleEnabled() {
        this.enabled.toggle();
        updateHook.accept(this.enabled.getValue());
    }

    public void enable() {
        this.enabled.enable();
        updateHook.accept(this.enabled.getValue());
    }

    public void disable() {
        this.enabled.disable();
        updateHook.accept(this.enabled.getValue());
    }

}
