package com.tom.createores.jei;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import com.tom.createores.Registration;
import com.tom.createores.recipe.ExtractorRecipe;

import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;

public class ExtractingCategory extends ExcavatingCategory<ExtractorRecipe> {

	public ExtractingCategory() {
		block = new AnimatedBlock(Registration.EXTRACTOR_BLOCK.getDefaultState(), 11);
		icon = new DoubleItemIcon(Registration.EXTRACTOR_BLOCK::asStack, () -> new ItemStack(Items.BUCKET));
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("jei.coe.recipe.extracting");
	}

	@Override
	public RecipeType<ExtractorRecipe> getRecipeType() {
		return JEIHandler.EXTRACTING;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ExtractorRecipe recipe, IFocusGroup focuses) {
		super.setRecipe(builder, recipe, focuses);

		int xOffset = getBackground().getWidth() / 2;
		int yOffset = 86;

		builder
		.addSlot(RecipeIngredientRole.OUTPUT, xOffset - 8, yOffset - 8)
		.setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
		.addIngredient(ForgeTypes.FLUID_STACK, CreateRecipeCategory.withImprovedVisibility(recipe.getOutput()))
		.addTooltipCallback(CreateRecipeCategory.addFluidTooltip(recipe.getOutput().getAmount()));

	}
}
