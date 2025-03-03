package com.tom.createores.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;

import com.tom.createores.CreateOreExcavation;
import com.tom.createores.OreDataCapability;
import com.tom.createores.OreDataCapability.OreData;
import com.tom.createores.util.ComponentJoiner;

public class OreVeinFinderItem extends Item {

	public OreVeinFinderItem() {
		super(new Item.Properties().tab(CreateOreExcavation.MOD_TAB).stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		if(ctx.getLevel().isClientSide)return InteractionResult.SUCCESS;

		ChunkPos center = new ChunkPos(ctx.getClickedPos());
		OreData found = null;
		List<OreData> nearby = new ArrayList<>();
		List<OreData> far = new ArrayList<>();
		for(int x = -2;x <= 2;x++) {
			for(int z = -2;z <= 2;z++) {
				OreData d = OreDataCapability.getData(ctx.getLevel().getChunk(center.x + x, center.z + z));
				if(x == 0 && z == 0)found = d;
				else if(Math.abs(x) <= 1 && Math.abs(z) <= 1)nearby.add(d);
				else far.add(d);
			}
		}
		ctx.getPlayer().sendMessage(new TranslatableComponent("chat.coe.veinFinder.info"), Util.NIL_UUID);
		RecipeManager m = ctx.getLevel().getRecipeManager();
		Component f;
		Component nothing = new TranslatableComponent("chat.coe.veinFinder.nothing");
		Component comma = new TextComponent(", ");
		if(found != null && found.getRecipe(m) != null)f = found.getRecipe(m).getName();
		else f = nothing;
		ctx.getPlayer().sendMessage(new TranslatableComponent("chat.coe.veinFinder.found", f), Util.NIL_UUID);

		f = nearby.stream().map(d -> d.getRecipe(m)).filter(r -> r != null).map(r -> r.getName()).collect(ComponentJoiner.joining(nothing, comma));
		ctx.getPlayer().sendMessage(new TranslatableComponent("chat.coe.veinFinder.nearby", f), Util.NIL_UUID);

		f = far.stream().map(d -> d.getRecipe(m)).filter(r -> r != null).map(r -> r.getName()).collect(ComponentJoiner.joining(nothing, comma));
		ctx.getPlayer().sendMessage(new TranslatableComponent("chat.coe.veinFinder.far", f), Util.NIL_UUID);

		return InteractionResult.SUCCESS;
	}
}
