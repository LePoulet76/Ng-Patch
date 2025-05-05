package net.ilexiconn.nationsgui.forge.server.capes;

import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.capes.Cape$1;
import net.ilexiconn.nationsgui.forge.server.capes.Cape$2;
import org.json.simple.JSONObject;

public class Cape {

   private String identifier;
   private String displayName;
   private String textureURL;
   private HashMap<String, String> displayNames = new HashMap();
   private HashMap<String, String> descriptions = new HashMap();
   private String rarity;


   public Cape(String name, String displayName) {
      this.identifier = name;
      this.displayName = displayName;
      this.textureURL = "https://apiv2.nationsglory.fr/json/capes/capes/" + name + ".png";
   }

   public Cape(String name, HashMap<String, String> displayNames, HashMap<String, String> descriptions, String rarity) {
      this.identifier = name;
      this.displayNames = displayNames;
      this.descriptions = descriptions;
      this.rarity = rarity;
      this.textureURL = "https://apiv2.nationsglory.fr/json/capes/capes/" + name + ".png";
      this.displayName = (String)this.displayNames.get("fr");
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public static Cape getCapeFromJSon(JSONObject obj) {
      return obj.containsKey("displayNames")?new Cape((String)obj.get("name"), new Cape$1(obj), new Cape$2(obj), (String)obj.get("rarity")):new Cape((String)obj.get("name"), (String)obj.get("displayName"));
   }

   public String getRarity() {
      return this.rarity != null?this.rarity:"common";
   }

   public String getDisplayName() {
      return this.displayNames.containsKey(System.getProperty("java.lang"))?(String)this.displayNames.get(System.getProperty("java.lang")):this.displayName;
   }

   public void setIdentifier(String name) {
      this.identifier = name;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public String getTextureURL() {
      return this.textureURL;
   }

   public void setTextureURL(String texture) {
      this.textureURL = texture;
   }

   public String getDescription() {
      return this.descriptions.containsKey(System.getProperty("java.lang"))?(String)this.descriptions.get(System.getProperty("java.lang")):"";
   }
}
