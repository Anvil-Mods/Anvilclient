/*******************************************************************************
 * Copyright (C) 2021, 2022 Anvil-Mods
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package anvilclient.features;

import anvilclient.features.components.BaseComponent;
import anvilclient.features.components.FeatureToggleComponent;
import anvilclient.features.components.ToggleComponent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class Feature {

    protected List<BaseComponent> components = new ArrayList<>();

    public FeatureToggleComponent getFeatureToggle() {
        return featureToggle;
    }

    protected FeatureToggleComponent featureToggle;

    public abstract String getName();

    public abstract FeatureCategory getCategory();

    protected void addComponents(BaseComponent... components) {
        for (BaseComponent component : components) {
            this.components.add(component);
            if (component instanceof FeatureToggleComponent featureToggleComponent) {
                if (featureToggle != null) {
                    throw new UnsupportedOperationException("Can't register more than one toggle");
                }
                featureToggle = featureToggleComponent;
            }
        }
    }

    public List<BaseComponent> getComponents() {
        return components;
    }

    public void register() {
        ClientTickEvent.CLIENT_POST.register(this::onClientTick);
        for (BaseComponent component : components) {
            component.register();
        }
    }

    protected void onClientTick(Minecraft minecraft) {
    }

}
