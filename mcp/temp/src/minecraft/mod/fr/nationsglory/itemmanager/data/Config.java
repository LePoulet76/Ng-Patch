package fr.nationsglory.itemmanager.data;

import fr.nationsglory.itemmanager.data.RecipeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

   private Map<String, List<Integer>> itemBlacklist = new HashMap();
   private List<String> creativeTabBlacklist = new ArrayList();
   private Map<String, Map<String, RecipeData>> crafts = new HashMap();


   public Map<String, List<Integer>> getItemBlacklist() {
      return this.itemBlacklist;
   }

   public List<String> getCreativeTabBlacklist() {
      return this.creativeTabBlacklist;
   }

   public Map<String, Map<String, RecipeData>> getCrafts() {
      return this.crafts;
   }
}
