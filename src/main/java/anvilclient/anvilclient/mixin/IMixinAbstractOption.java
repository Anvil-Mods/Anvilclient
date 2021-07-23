package anvilclient.anvilclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.util.text.ITextComponent;

@Mixin(AbstractOption.class)
public interface IMixinAbstractOption {
	
	@Mutable
	@Accessor(value="NARRATOR")
	static void setNarrator(IteratableOption option) {
	}
	
	@Invoker(value="genericValueLabel")
	ITextComponent callGenericValueLabel(ITextComponent p_243222_1_);

}
