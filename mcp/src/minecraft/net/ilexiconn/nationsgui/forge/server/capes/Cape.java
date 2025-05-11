/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.capes;

import java.util.HashMap;
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
        this.displayName = this.displayNames.get("fr");
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public static Cape getCapeFromJSon(final JSONObject obj) {
        if (obj.containsKey("displayNames")) {
            return new Cape((String)obj.get("name"), new HashMap<String, String>(){
                {
                    this.put("fr", (String)((JSONObject)obj.get("displayNames")).get("fr"));
                    this.put("en", (String)((JSONObject)obj.get("displayNames")).get("en"));
                }
            }, new HashMap<String, String>(){
                {
                    this.put("fr", (String)((JSONObject)obj.get("descriptions")).get("fr"));
                    this.put("en", (String)((JSONObject)obj.get("descriptions")).get("en"));
                }
            }, (String)obj.get("rarity"));
        }
        return new Cape((String)obj.get("name"), (String)obj.get("displayName"));
    }

    public String getRarity() {
        return this.rarity != null ? this.rarity : "common";
    }

    public String getDisplayName() {
        return this.displayNames.containsKey(System.getProperty("java.lang")) ? this.displayNames.get(System.getProperty("java.lang")) : this.displayName;
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
        return this.descriptions.containsKey(System.getProperty("java.lang")) ? this.descriptions.get(System.getProperty("java.lang")) : "";
    }
}

