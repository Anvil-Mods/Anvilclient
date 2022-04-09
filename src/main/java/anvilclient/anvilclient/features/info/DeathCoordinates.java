package anvilclient.anvilclient.features.info;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.util.utils.LocalPlayerUtils;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathCoordinates extends Feature {

	@Override
	public String getName() {
		return "deathCoordinates";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}
	
	public void onLivingDeath(LivingDeathEvent event) {
		if (event.getEntityLiving().equals(LocalPlayerUtils.getLocalPlayer())) {
			
		}
	}

}
