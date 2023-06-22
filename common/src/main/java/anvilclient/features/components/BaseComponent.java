package anvilclient.features.components;

import anvilclient.features.Feature;

public abstract class BaseComponent {

    protected BaseComponent(Feature parentFeature) {
        this.parentFeature = parentFeature;
    }

    protected Feature parentFeature;

    public void register() {
    }
}
