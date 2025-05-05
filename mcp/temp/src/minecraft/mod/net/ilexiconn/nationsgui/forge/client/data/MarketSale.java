package net.ilexiconn.nationsgui.forge.client.data;

import net.minecraft.item.ItemStack;

public class MarketSale {

   private String uuid;
   private String pseudo;
   private int quantity;
   private long expiry;
   private long superexpiry;
   private int sold;
   private ItemStack itemStack;
   private int price;
   private int itemstackId;


   public String getUuid() {
      return this.uuid;
   }

   public String getPseudo() {
      return this.pseudo;
   }

   public int getQuantity() {
      return this.quantity;
   }

   public long getExpiry() {
      return this.expiry;
   }

   public int getSold() {
      return this.sold;
   }

   public int getItemstackId() {
      return this.itemstackId;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public int getPrice() {
      return this.price;
   }

   public void setSold(int sold) {
      this.sold = sold;
   }

   public int getRest() {
      return this.quantity - this.sold;
   }

   public boolean equals(Object obj) {
      return obj instanceof MarketSale?this.getUuid().equals(((MarketSale)obj).getUuid()):false;
   }

   public void replace(MarketSale origin) {
      this.uuid = origin.uuid;
      this.pseudo = origin.pseudo;
      this.quantity = origin.quantity;
      this.expiry = origin.expiry;
      this.sold = origin.sold;
      this.itemStack = origin.itemStack;
      this.price = origin.price;
   }

   public long getSuperexpiry() {
      return this.superexpiry;
   }

   public void setItemStack(ItemStack itemStack) {
      this.itemStack = itemStack;
   }
}
