package cursedflames.enderclam.integration;

//@JEIPlugin
//public class JeiIntegration implements IModPlugin {
//	@Override
//	public void register(IModRegistry registry) {
//		List<IRecipeWrapper> recipes = new ArrayList<>();
//		for (Pair<Item, Item> ingredients : AnvilRecipes.simpleRecipes.keySet()) {
//			Pair<Integer, ItemStack> result = AnvilRecipes.simpleRecipes.get(ingredients);
//			recipes.add(registry.getJeiHelpers().getVanillaRecipeFactory().createAnvilRecipe(
//					new ItemStack(ingredients.getLeft()),
//					Arrays.asList(new ItemStack(ingredients.getRight())),
//					Arrays.asList(result.getRight())));
//		}
//		recipes.add(registry.getJeiHelpers().getVanillaRecipeFactory().createAnvilRecipe(
//				new ItemStack(ModItems.shieldCobalt),
//				Arrays.asList(new ItemStack(ModItems.trinketObsidianSkull)),
//				Arrays.asList(new ItemStack(ModItems.shieldObsidian))));
//		recipes.add(registry.getJeiHelpers().getVanillaRecipeFactory().createAnvilRecipe(
//				new ItemStack(ModItems.shieldObsidian),
//				Arrays.asList(new ItemStack(ModItems.trinketAnkhCharm)),
//				Arrays.asList(new ItemStack(ModItems.shieldAnkh))));
//
//		registry.addRecipes(recipes, VanillaRecipeCategoryUid.ANVIL);
//	}
//}
