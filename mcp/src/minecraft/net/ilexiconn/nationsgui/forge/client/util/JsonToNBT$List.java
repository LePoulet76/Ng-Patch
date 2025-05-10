package net.ilexiconn.nationsgui.forge.client.util;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$NBTParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

public class JsonToNBT$List extends JsonToNBT$NBTParser
{
    protected List<JsonToNBT$NBTParser> parserList = Lists.newArrayList();

    public JsonToNBT$List(String name)
    {
        this.name = name;
    }

    public NBTBase parse()
    {
        NBTTagList list = new NBTTagList();
        Iterator var2 = this.parserList.iterator();

        while (var2.hasNext())
        {
            JsonToNBT$NBTParser nbtParser = (JsonToNBT$NBTParser)var2.next();
            list.appendTag(nbtParser.parse());
        }

        return list;
    }
}
