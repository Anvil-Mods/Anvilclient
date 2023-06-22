package anvilclient.features.components;

import anvilclient.features.Feature;

import java.util.function.Consumer;

public class FeatureToggleComponent extends ToggleComponent {
    public FeatureToggleComponent(Feature parentFeature, String description, boolean defaultValue, Consumer<Boolean> updateHook) {
        super(parentFeature, "featureEnabled", description, defaultValue, updateHook);
    }

    public FeatureToggleComponent(Feature parentFeature, String description, boolean defaultValue) {
        super(parentFeature, "featureEnabled", description, defaultValue);
    }
}
