package mods.railcraft.data.recipes.providers;

import java.util.function.Consumer;
import mods.railcraft.data.recipes.builders.CrusherRecipeBuilder;
import mods.railcraft.tags.RailcraftTags;
import mods.railcraft.world.item.RailcraftItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public class CrusherRecipeProvider extends RecipeProvider {

  private CrusherRecipeProvider(PackOutput packOutput) {
    super(packOutput);
  }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
  }

  public static void genRecipes(Consumer<FinishedRecipe> consumer) {
    buildVanilla(consumer);
    buildRailcraft(consumer);
    buildConditionalRecipe(consumer);
  }

  private static void buildVanilla(Consumer<FinishedRecipe> consumer) {
    CrusherRecipeBuilder.crush(Items.OBSIDIAN)
        .addResult(RailcraftItems.CRUSHED_OBSIDIAN.get(), 1, 1)
        .addResult(RailcraftItems.OBSIDIAN_DUST.get(), 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.COBBLESTONE)
        .addResult(Items.GRAVEL, 1, 1)
        .addResult(Items.FLINT, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.MOSSY_COBBLESTONE)
        .addResult(Items.GRAVEL, 1, 1)
        .addResult(Items.VINE, 1, 0.8)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.GRAVEL)
        .addResult(Items.SAND, 1, 1)
        .addResult(Items.GOLD_NUGGET, 1, 0.001)
        .addResult(Items.DIAMOND, 1, 0.00005)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.STONE)
        .addResult(Items.COBBLESTONE, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.SANDSTONE)
        .addResult(Items.SAND, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.BRICKS)
        .addResult(Items.BRICK, 3, 1)
        .addResult(Items.BRICK, 1, 0.50)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.CLAY)
        .addResult(Items.CLAY_BALL, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(ItemTags.STONE_BRICKS)
        .addResult(Items.COBBLESTONE, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.COBBLESTONE_STAIRS)
        .addResult(Items.GRAVEL, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.STONE_BRICK_STAIRS)
        .addResult(Items.COBBLESTONE, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.NETHER_BRICK_STAIRS)
        .addResult(Items.NETHER_BRICK, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.BRICK_STAIRS)
        .addResult(Items.BRICK, 4, 1)
        .addResult(Items.BRICK, 1, 0.5)
        .addResult(Items.BRICK, 1, 0.5)
        .save(consumer);
    CrusherRecipeBuilder.crush(Ingredient.of(Items.STONE_SLAB, Items.STONE_BRICK_SLAB))
        .addResult(Items.COBBLESTONE, 1, 0.45)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.SANDSTONE_SLAB)
        .addResult(Items.SAND, 1, 0.45)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.COBBLESTONE_SLAB)
        .addResult(Items.GRAVEL, 1, 0.45)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.BRICK_SLAB)
        .addResult(Items.BRICK, 1, 1)
        .addResult(Items.BRICK, 1, 0.75)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.ICE)
        .addResult(Items.SNOW_BLOCK, 1, 0.85)
        .addResult(Items.SNOWBALL, 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.NETHER_BRICK_FENCE)
        .addResult(Items.NETHER_BRICK, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.GLOWSTONE)
        .addResult(Items.GLOWSTONE_DUST, 3, 1)
        .addResult(Items.GLOWSTONE_DUST, 1, 0.75)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.REDSTONE_LAMP)
        .addResult(Items.GLOWSTONE_DUST, 3, 1)
        .addResult(Items.GLOWSTONE_DUST, 1, 0.75)
        .addResult(Items.REDSTONE, 3, 1)
        .addResult(Items.REDSTONE, 1, 0.75)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.BONE)
        .addResult(Items.BONE_MEAL, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.BLAZE_ROD)
        .addResult(Items.BLAZE_POWDER, 2, 1)
        .addResult(Items.BLAZE_POWDER, 1, 0.65)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 1, 0.5)
        .addResult(Items.BLAZE_POWDER, 1, 0.25)
        .addResult(Items.BLAZE_POWDER, 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_REDSTONE)
        .addResult(Items.REDSTONE, 6, 1)
        .addResult(Items.REDSTONE, 2, 0.85)
        .addResult(Items.REDSTONE, 1, 0.25)
        .addResult(Items.GLOWSTONE_DUST, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_DIAMOND)
        .addResult(Items.DIAMOND, 1, 1)
        .addResult(Items.DIAMOND, 1, 0.85)
        .addResult(Items.DIAMOND, 1, 0.25)
        .addResult(Items.COAL, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_EMERALD)
        .addResult(Items.EMERALD, 1, 1)
        .addResult(Items.EMERALD, 1, 0.85)
        .addResult(Items.EMERALD, 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_LAPIS)
        .addResult(Items.LAPIS_LAZULI, 8, 1)
        .addResult(Items.LAPIS_LAZULI, 1, 0.85)
        .addResult(Items.LAPIS_LAZULI, 1, 0.35)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 1, 0.2)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_COAL)
        .addResult(RailcraftItems.COAL_DUST.get(), 2, 1)
        .addResult(RailcraftItems.COAL_DUST.get(), 1, 0.65)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 1, 0.15)
        .addResult(Items.COAL, 1, 0.15)
        .addResult(Items.DIAMOND, 1, 0.001)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.COAL)
        .addResult(RailcraftItems.COAL_DUST.get(), 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.CHARCOAL)
        .addResult(RailcraftItems.CHARCOAL_DUST.get(), 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.COAL_BLOCK)
        .addResult(RailcraftItems.COAL_DUST.get(), 9, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.ENDER_PEARL)
        .addResult(RailcraftItems.ENDER_DUST.get(), 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_QUARTZ)
        .addResult(Items.QUARTZ, 3, 1)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.DARK_PRISMARINE)
        .addResult(Items.PRISMARINE_SHARD, 8, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.PRISMARINE_BRICKS)
        .addResult(Items.PRISMARINE_SHARD, 9, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.PRISMARINE)
        .addResult(Items.PRISMARINE_SHARD, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.AMETHYST_BLOCK)
        .addResult(Items.AMETHYST_SHARD, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.NETHER_WART_BLOCK)
        .addResult(Items.NETHER_WART, 9, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(ItemTags.WOOL)
        .addResult(Items.STRING, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.STORAGE_BLOCKS_QUARTZ)
        .addResult(Items.QUARTZ, 4, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.RAW_IRON_BLOCK)
        .addResult(Items.RAW_IRON, 9, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.STORAGE_BLOCKS_RAW_COPPER)
        .addResult(Items.RAW_COPPER, 9, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.RAW_GOLD_BLOCK)
        .addResult(Items.RAW_GOLD, 9, 1)
        .save(consumer);
  }

  private static void buildRailcraft(Consumer<FinishedRecipe> consumer) {
    CrusherRecipeBuilder.crush(RailcraftItems.COKE_OVEN_BRICKS.get())
        .addResult(Items.BRICK, 3, 1)
        .addResult(Items.BRICK, 1, 0.5)
        .addResult(Items.SAND, 1, 0.25)
        .addResult(Items.SAND, 1, 0.25)
        .addResult(Items.SAND, 1, 0.25)
        .addResult(Items.SAND, 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftItems.BLAST_FURNACE_BRICKS.get())
        .addResult(Items.NETHER_BRICK, 1, 0.75)
        .addResult(Items.SOUL_SAND, 1, 0.75)
        .addResult(Items.BLAZE_POWDER, 1, 0.05)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftItems.CRUSHED_OBSIDIAN.get())
        .addResult(RailcraftItems.OBSIDIAN_DUST.get(), 1, 1)
        .addResult(RailcraftItems.OBSIDIAN_DUST.get(), 1, 0.25)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.SULFUR_ORE)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 5, 1)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 1, 0.85)
        .addResult(RailcraftItems.SULFUR_DUST.get(), 1, 0.35)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.SALTPETER_ORE)
        .addResult(RailcraftItems.SALTPETER_DUST.get(), 3, 1)
        .addResult(RailcraftItems.SALTPETER_DUST.get(), 1, 0.85)
        .addResult(RailcraftItems.SALTPETER_DUST.get(), 1, 0.35)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftItems.FIRESTONE_ORE.get())
        .addResult(RailcraftItems.RAW_FIRESTONE.get(), 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.QUARRIED)
        .addResult(RailcraftItems.QUARRIED_COBBLESTONE.get(), 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Ingredient.of(RailcraftItems.QUARRIED_BRICK_STAIRS.get(),
            RailcraftItems.QUARRIED_PAVER_STAIRS.get()))
        .addResult(RailcraftItems.QUARRIED_COBBLESTONE.get(), 1, 0.75)
        .save(consumer);
    CrusherRecipeBuilder.crush(Ingredient.of(RailcraftItems.QUARRIED_BRICK_SLAB.get(),
            RailcraftItems.QUARRIED_PAVER_SLAB.get()))
        .addResult(RailcraftItems.QUARRIED_COBBLESTONE.get(), 1, 0.5)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.ABYSSAL)
        .addResult(RailcraftItems.ABYSSAL_COBBLESTONE.get(), 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Ingredient.of(RailcraftItems.ABYSSAL_BRICK_STAIRS.get(),
            RailcraftItems.ABYSSAL_PAVER_STAIRS.get()))
        .addResult(RailcraftItems.ABYSSAL_COBBLESTONE.get(), 1, 0.75)
        .save(consumer);
    CrusherRecipeBuilder.crush(Ingredient.of(RailcraftItems.ABYSSAL_BRICK_SLAB.get(),
            RailcraftItems.ABYSSAL_PAVER_SLAB.get()))
        .addResult(RailcraftItems.ABYSSAL_COBBLESTONE.get(), 1, 0.5)
        .save(consumer);
    CrusherRecipeBuilder.crush(Ingredient.of(RailcraftItems.ZINC_SILVER_BATTERY_EMPTY.get(),
            RailcraftItems.ZINC_CARBON_BATTERY_EMPTY.get()))
        .addResult(RailcraftItems.CHARGE_TERMINAL.get(), 2, 1)
        .addResult(RailcraftItems.CHARGE_SPOOL_MEDIUM.get(), 1, 1)
        .addResult(RailcraftItems.SLAG.get(), 4, 1)
        .addResult(RailcraftItems.SLAG.get(), 2, 0.5)
        .save(consumer);
  }

  private static void buildConditionalRecipe(Consumer<FinishedRecipe> consumer) {
    CrusherRecipeBuilder.crush(Items.NETHERITE_INGOT)
        .addResult(RailcraftTags.Items.NETHERITE_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.BRONZE_INGOT)
        .addResult(RailcraftTags.Items.BRONZE_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Items.LAPIS_LAZULI)
        .addResult(RailcraftTags.Items.LAPIS_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.GEMS_QUARTZ)
        .addResult(RailcraftTags.Items.QUARTZ_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.GEMS_EMERALD)
        .addResult(RailcraftTags.Items.EMERALD_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.GEMS_DIAMOND)
        .addResult(RailcraftTags.Items.DIAMOND_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.STEEL_INGOT)
        .addResult(RailcraftTags.Items.STEEL_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.INGOTS_IRON)
        .addResult(RailcraftTags.Items.IRON_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.STORAGE_BLOCKS_RAW_IRON)
        .addResult(RailcraftTags.Items.IRON_DUST, 12, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_IRON)
        .addResult(RailcraftTags.Items.IRON_DUST, 2, 1)
        .addResult(RailcraftTags.Items.NICKEL_DUST, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.RAW_MATERIALS_IRON)
        .addResult(RailcraftTags.Items.IRON_DUST, 1, 1)
        .addResult(RailcraftTags.Items.IRON_DUST, 1, 0.35)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.INGOTS_GOLD)
        .addResult(RailcraftTags.Items.GOLD_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_GOLD)
        .addResult(RailcraftTags.Items.GOLD_DUST, 2, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.STORAGE_BLOCKS_RAW_GOLD)
        .addResult(RailcraftTags.Items.GOLD_DUST, 12, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.INGOTS_COPPER)
        .addResult(RailcraftTags.Items.COPPER_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.RAW_MATERIALS_COPPER)
        .addResult(RailcraftTags.Items.COPPER_DUST, 1, 1)
        .addResult(RailcraftTags.Items.COPPER_DUST, 1, 0.35)
        .save(consumer);
    CrusherRecipeBuilder.crush(Tags.Items.ORES_COPPER)
        .addResult(RailcraftTags.Items.COPPER_DUST, 2, 1)
        .addResult(RailcraftTags.Items.GOLD_DUST, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.TIN_INGOT)
        .addResult(RailcraftTags.Items.TIN_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.LEAD_INGOT)
        .addResult(RailcraftTags.Items.LEAD_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.RAW_LEAD_BLOCK)
        .addResult(RailcraftTags.Items.LEAD_DUST, 12, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.LEAD_ORE)
        .addResult(RailcraftTags.Items.LEAD_DUST, 2, 1)
        .addResult(RailcraftTags.Items.SILVER_DUST, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.COAL_COKE)
        .addResult(RailcraftTags.Items.COAL_COKE_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.SILVER_INGOT)
        .addResult(RailcraftTags.Items.SILVER_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.SILVER_ORE)
        .addResult(RailcraftTags.Items.SILVER_DUST, 2, 1)
        .addResult(RailcraftTags.Items.LEAD_DUST, 1, 0.1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.SILVER_RAW)
        .addResult(RailcraftTags.Items.SILVER_DUST, 1, 1)
        .addResult(RailcraftTags.Items.SILVER_DUST, 1, 0.35)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.RAW_SILVER_BLOCK)
        .addResult(RailcraftTags.Items.SILVER_DUST, 12, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.NICKEL_INGOT)
        .addResult(RailcraftTags.Items.NICKEL_DUST, 1, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.RAW_NICKEL_BLOCK)
        .addResult(RailcraftTags.Items.NICKEL_DUST, 12, 1)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.NICKEL_RAW)
        .addResult(RailcraftTags.Items.NICKEL_DUST, 1, 1)
        .addResult(RailcraftTags.Items.NICKEL_DUST, 1, 0.35)
        .save(consumer);
    CrusherRecipeBuilder.crush(RailcraftTags.Items.NICKEL_ORE)
        .addResult(RailcraftTags.Items.NICKEL_DUST, 2, 1)
        .save(consumer);
  }
}
