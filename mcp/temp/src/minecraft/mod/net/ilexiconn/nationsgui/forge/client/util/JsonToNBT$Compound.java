package net.ilexiconn.nationsgui.forge.client.util;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$NBTParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class JsonToNBT$Compound extends JsonToNBT$NBTParser {

   protected List<JsonToNBT$NBTParser> parserList = Lists.newArrayList();


   public JsonToNBT$Compound(String name) {
      this.name = name;
   }

   public NBTBase parse() {
      NBTTagCompound compound = new NBTTagCompound();
      Iterator var2 = this.parserList.iterator();

      while(var2.hasNext()) {
         JsonToNBT$NBTParser nbtParser = (JsonToNBT$NBTParser)var2.next();
         NBTBase base = nbtParser.parse();
         compound.func_74782_a(nbtParser.name, base);
      }

      return compound;
   }
}
