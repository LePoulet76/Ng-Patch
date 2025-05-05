package net.ilexiconn.nationsgui.forge.server.json;

import com.google.gson.annotations.SerializedName;

public class CategoryItemJSON {

   public int id;
   public String name;
   public int metadata;
   public double price;
   public String description = "";
   @SerializedName("max_amount")
   public int maxAmount = 64;
   public String command;
   @SerializedName("image_preview")
   public String imagePreview;
   public String image;
   @SerializedName("nbt")
   public String nbt;
   @SerializedName("nbt_item")
   public String nbtItem;


   public CategoryItemJSON() {
      this.image = this.imagePreview;
   }
}
