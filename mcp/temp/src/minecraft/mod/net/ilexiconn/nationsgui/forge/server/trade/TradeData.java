package net.ilexiconn.nationsgui.forge.server.trade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TradeData implements IExtendedEntityProperties {

   private long ignoredTime = 30000L;
   public EntityPlayer tradePlayer;
   public EntityPlayer player;
   public Map ignored = new HashMap();
   public EnumTradeState state;
   private int money;
   private long startTradeTime;


   public TradeData(EntityPlayer player) {
      this.state = EnumTradeState.NONE;
      this.player = player;
   }

   public void saveNBTData(NBTTagCompound nope) {
      NBTTagCompound compound = new NBTTagCompound();
      NBTTagList list = new NBTTagList();
      Iterator var4 = this.ignored.entrySet().iterator();

      while(var4.hasNext()) {
         Entry entry = (Entry)var4.next();
         NBTTagCompound c = new NBTTagCompound();
         c.func_74778_a("Key", (String)entry.getKey());
         c.func_74772_a("Value", ((Long)entry.getValue()).longValue());
         list.func_74742_a(c);
      }

      compound.func_74782_a("Ignored", list);
      this.player.getEntityData().func_74782_a("NGTradeData", compound);
   }

   public void loadNBTData(NBTTagCompound nope) {
      NBTTagCompound compound = this.player.getEntityData().func_74775_l("NGTradeData");
      NBTTagList list = compound.func_74761_m("Ignored");
      HashMap ignored = new HashMap();

      for(int i = 0; i < list.func_74745_c(); ++i) {
         NBTTagCompound c = (NBTTagCompound)list.func_74743_b(i);
         ignored.put(c.func_74779_i("Key"), Long.valueOf(c.func_74763_f("Value")));
      }

      this.ignored = ignored;
   }

   public void init(Entity entity, World world) {}

   public static TradeData get(EntityPlayer player) {
      TradeData data = (TradeData)player.getExtendedProperties("NGTradeData");
      if(data == null) {
         player.registerExtendedProperties("NGTradeData", data = new TradeData(player));
         data.loadNBTData((NBTTagCompound)null);
      }

      return data;
   }

   public boolean isIgnored(EntityPlayer sender) {
      String name = sender.func_70005_c_().toLowerCase();
      if(!this.ignored.containsKey(name)) {
         return false;
      } else {
         long time = ((Long)this.ignored.get(name)).longValue();
         if(time + this.ignoredTime > System.currentTimeMillis()) {
            return true;
         } else {
            this.ignored.remove(name);
            return false;
         }
      }
   }

   public static boolean playersClose(EntityPlayer player, EntityPlayer sender) {
      double x = player.field_70165_t - sender.field_70165_t;
      double z = player.field_70161_v - sender.field_70161_v;
      double y = player.field_70163_u - sender.field_70163_u;
      return player.field_70170_p.equals(sender.field_70170_p) && x * x < 25.0D && z * z < 25.0D && y * y < 25.0D;
   }

   public boolean isTrading() {
      return this.tradePlayer == null?false:this.isTradingWith(this.tradePlayer) && !this.tradePlayer.field_70128_L || this.state == EnumTradeState.DONE;
   }

   public void closeTrade() {
      if(this.tradePlayer != null) {
         TradeData data = get(this.tradePlayer);
         if(data.isTradingWith(this.player) && data.state != EnumTradeState.DONE) {
            data.tradePlayer = null;
            data.state = EnumTradeState.NONE;
            TradeManager.sendClose(this.tradePlayer);
         }

         this.tradePlayer = null;
         this.state = EnumTradeState.NONE;
         TradeManager.sendClose(this.player);
      }

   }

   public boolean isTradingWith(EntityPlayer player) {
      if(this.tradePlayer != null && player != null && playersClose(this.player, player)) {
         TradeData data = get(this.tradePlayer);
         return this.tradePlayer.func_70005_c_().equals(player.func_70005_c_()) && data.tradePlayer != null && data.tradePlayer.func_70005_c_().equals(this.player.func_70005_c_());
      } else {
         return false;
      }
   }

   public void setStartTradeTime(long time) {
      this.startTradeTime = time;
   }

   public long getStartTradeTime() {
      return this.startTradeTime;
   }

   public void setState(EnumTradeState state) {
      this.state = state;
      if(this.tradePlayer != null) {
         TradeManager.sendState(this.player, this.tradePlayer.func_70005_c_(), state);
      }

   }

   public void setMoney(int money) {
      this.money = money;
   }

   public int getMoney() {
      return this.money;
   }
}
