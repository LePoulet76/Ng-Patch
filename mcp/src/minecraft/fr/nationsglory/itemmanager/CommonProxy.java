/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 */
package fr.nationsglory.itemmanager;

import com.google.common.io.Files;
import com.google.gson.Gson;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.nationsglory.itemmanager.SkippedShapedRecipes;
import fr.nationsglory.itemmanager.data.LocalConfig;
import fr.nationsglory.itemmanager.data.RecipeData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class CommonProxy {
    public static LocalConfig localConfig;

    public void preInit(FMLPreInitializationEvent event) {
        File file = new File(event.getModConfigurationDirectory(), "nationsgui.json");
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                localConfig = (LocalConfig)gson.fromJson((Reader)new InputStreamReader(new FileInputStream(file)), LocalConfig.class);
                System.out.println("DEBUG FOUND LOCALCONFIG");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                localConfig = new LocalConfig();
                Files.write((byte[])gson.toJson((Object)localConfig).getBytes(), (File)file);
                System.out.println("DEBUG NO FOUND LOCALCONFIG");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        if (NationsGUITransformer.config.getCrafts().size() > 0) {
            for (Map.Entry<String, Map<String, RecipeData>> entry : NationsGUITransformer.config.getCrafts().entrySet()) {
                List<String> stringList = Arrays.asList(entry.getKey().split(";"));
                if (!stringList.contains(localConfig.getServerName()) && !stringList.contains("all")) continue;
                for (RecipeData recipeData : entry.getValue().values()) {
                    List<RecipeData.ItemStack> line2;
                    int w = 0;
                    int h = recipeData.getRecipe().size();
                    for (List<RecipeData.ItemStack> line2 : recipeData.getRecipe()) {
                        if (line2.size() <= w) continue;
                        w = line2.size();
                    }
                    ArrayList<ItemStack> itemStackList = new ArrayList<ItemStack>();
                    line2 = recipeData.getRecipe().iterator();
                    while (line2.hasNext()) {
                        List line3 = (List)line2.next();
                        for (int i = 0; i < w; ++i) {
                            try {
                                RecipeData.ItemStack item = (RecipeData.ItemStack)line3.get(i);
                                itemStackList.add(new ItemStack(item.getId(), item.getCount(), item.getMetadata()));
                                continue;
                            }
                            catch (IndexOutOfBoundsException e) {
                                itemStackList.add(null);
                            }
                        }
                    }
                    RecipeData.ItemStack out = recipeData.getOutput();
                    GameRegistry.addRecipe((IRecipe)new SkippedShapedRecipes(w, h, itemStackList.toArray(new ItemStack[w * h]), new ItemStack(out.getId(), out.getCount(), out.getMetadata())));
                }
            }
        }
    }

    public void postInit() {
        Iterator iterator = CraftingManager.func_77594_a().func_77592_b().iterator();
        while (iterator.hasNext()) {
            IRecipe recipe = (IRecipe)iterator.next();
            if (recipe == null || recipe instanceof SkippedShapedRecipes) continue;
            for (List<Integer> list : NationsGUITransformer.config.getItemBlacklist().values()) {
                if (recipe.func_77571_b() == null || !list.contains(recipe.func_77571_b().field_77993_c)) continue;
                iterator.remove();
            }
        }
        if (NationsGUITransformer.config.getItemBlacklist().containsKey("")) {
            for (Integer integer : NationsGUITransformer.config.getItemBlacklist().get("")) {
                Item.field_77698_e[integer.intValue()] = null;
                if (integer >= 4096) continue;
                Block.field_71973_m[integer.intValue()] = null;
            }
        }
    }
}

