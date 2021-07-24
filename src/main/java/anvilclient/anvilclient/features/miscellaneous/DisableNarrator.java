package anvilclient.anvilclient.features.miscellaneous;

import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.mixin.IMixinAbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.text.TranslationTextComponent;

public class DisableNarrator extends TogglableFeature {

	@Override
	public String getName() {
		return "disableNarrator";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.MISCELLANEOUS;
	}

	private final IteratableOption NORMAL_NARRATOR = new IteratableOption("options.narrator",
			(p_216648_0_, p_216648_1_) -> {
				if (NarratorChatListener.INSTANCE.isActive()) {
					p_216648_0_.narratorStatus = NarratorStatus.byId(p_216648_0_.narratorStatus.getId() + p_216648_1_);
				} else {
					p_216648_0_.narratorStatus = NarratorStatus.OFF;
				}

				NarratorChatListener.INSTANCE.updateNarratorStatus(p_216648_0_.narratorStatus);
			}, (p_216632_0_, p_216632_1_) -> {
				return NarratorChatListener.INSTANCE.isActive()
						? ((IMixinAbstractOption) p_216632_1_)
								.callGenericValueLabel(p_216632_0_.narratorStatus.getName())
						: ((IMixinAbstractOption) p_216632_1_)
								.callGenericValueLabel(new TranslationTextComponent("options.narrator.notavailable"));
			});

	private final IteratableOption DUMMY_NARRATOR = new IteratableOption("options.narrator",
			(p_216648_0_, p_216648_1_) -> {
				p_216648_0_.narratorStatus = NarratorStatus.OFF;
			}, (p_216632_0_, p_216632_1_) -> {
				return ((IMixinAbstractOption) p_216632_1_)
						.callGenericValueLabel(new TranslationTextComponent("options.narrator.notavailable"));
			});

	@SuppressWarnings("resource")
	@Override
	public void update() {
		if (this.isEnabled()) {
			Minecraft.getInstance().options.narratorStatus = NarratorStatus.OFF;
			NarratorChatListener.INSTANCE.clear();
			IMixinAbstractOption.setNarrator(DUMMY_NARRATOR);
		} else {
			IMixinAbstractOption.setNarrator(NORMAL_NARRATOR);
		}
	}
	
	public void onStart() {
		if (this.isEnabled()) {
			IMixinAbstractOption.setNarrator(DUMMY_NARRATOR);
		}
	}

}
