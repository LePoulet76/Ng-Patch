package fr.nationsglory.itemmanager;

import com.google.common.io.Files;
import com.google.gson.Gson;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.nationsglory.itemmanager.SkippedShapedRecipes;
import fr.nationsglory.itemmanager.data.LocalConfig;
import fr.nationsglory.itemmanager.data.RecipeData;
import fr.nationsglory.itemmanager.data.RecipeData$ItemStack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
      if(file.exists()) {
         try {
            localConfig = (LocalConfig)gson.fromJson(new InputStreamReader(new FileInputStream(file)), LocalConfig.class);
            System.out.println("DEBUG FOUND LOCALCONFIG");
         } catch (FileNotFoundException var6) {
            var6.printStackTrace();
         }
      } else {
         try {
            localConfig = new LocalConfig();
            Files.write(gson.toJson(localConfig).getBytes(), file);
            System.out.println("DEBUG NO FOUND LOCALCONFIG");
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

   }

   public void init() {
      if(NationsGUITransformer.config.getCrafts().size() > 0) {
         Iterator var1 = NationsGUITransformer.config.getCrafts().entrySet().iterator();

         while(var1.hasNext()) {
            Entry entry = (Entry)var1.next();
            List stringList = Arrays.asList(((String)entry.getKey()).split(";"));
            if(stringList.contains(localConfig.getServerName()) || stringList.contains("all")) {
               Iterator var4 = ((Map)entry.getValue()).values().iterator();

               while(var4.hasNext()) {
                  RecipeData recipeData = (RecipeData)var4.next();
                  int w = 0;
                  int h = recipeData.getRecipe().size();
                  Iterator itemStackList = recipeData.getRecipe().iterator();

                  while(itemStackList.hasNext()) {
                     List out = (List)itemStackList.next();
                     if(out.size() > w) {
                        w = out.size();
                     }
                  }

                  ArrayList var14 = new ArrayList();
                  Iterator var15 = recipeData.getRecipe().iterator();

                  while(var15.hasNext()) {
                     List line = (List)var15.next();

                     for(int i = 0; i < w; ++i) {
                        try {
                           RecipeData$ItemStack e = (RecipeData$ItemStack)line.get(i);
                           var14.add(new ItemStack(e.getId(), e.getCount(), e.getMetadata()));
                        } catch (IndexOutOfBoundsException var13) {
                           var14.add((Object)null);
                        }
                     }
                  }

                  RecipeData$ItemStack var16 = recipeData.getOutput();
                  GameRegistry.addRecipe(new SkippedShapedRecipes(w, h, (ItemStack[])var14.toArray(new ItemStack[w * h]), new ItemStack(var16.getId(), var16.getCount(), var16.getMetadata())));
               }
            }
         }
      }

   }

   public void postInit() {
      Iterator iterator = CraftingManager.func_77594_a().func_77592_b().iterator();

      while(iterator.hasNext()) {
         IRecipe recipe = (IRecipe)iterator.next();
         if(recipe != null && !(recipe instanceof SkippedShapedRecipes)) {
            Iterator integer = NationsGUITransformer.config.getItemBlacklist().values().iterator();

            while(integer.hasNext()) {
               List list = (List)integer.next();
               if(recipe.func_77571_b() != null && list.contains(Integer.valueOf(recipe.func_77571_b().field_77993_c))) {
                  iterator.remove();
               }
            }
         }
      }

      if(NationsGUITransformer.config.getItemBlacklist().containsKey("")) {
         Iterator recipe1 = ((List)NationsGUITransformer.config.getItemBlacklist().get("")).iterator();

         while(recipe1.hasNext()) {
            Integer integer1 = (Integer)recipe1.next();
            Item.field_77698_e[integer1.intValue()] = null;
            if(integer1.intValue() < 4096) {
               Block.field_71973_m[integer1.intValue()] = null;
            }
         }
      }

   }
}
