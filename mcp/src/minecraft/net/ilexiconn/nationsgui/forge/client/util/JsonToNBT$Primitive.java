package net.ilexiconn.nationsgui.forge.client.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$NBTParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

public class JsonToNBT$Primitive extends JsonToNBT$NBTParser
{
    private static final Pattern DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
    private static final Pattern FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
    private static final Pattern BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
    private static final Pattern LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
    private static final Pattern SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
    private static final Pattern INTEGER = Pattern.compile("[-+]?[0-9]+");
    private static final Pattern DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
    private static final Splitter SPLITTER = Splitter.on(',').omitEmptyStrings();
    protected String value;

    public JsonToNBT$Primitive(String json, String value)
    {
        this.name = json;
        this.value = value;
    }

    public NBTBase parse()
    {
        try
        {
            if (DOUBLE.matcher(this.value).matches())
            {
                return new NBTTagDouble(this.name, Double.parseDouble(this.value.substring(0, this.value.length() - 1)));
            }

            if (FLOAT.matcher(this.value).matches())
            {
                return new NBTTagFloat(this.name, Float.parseFloat(this.value.substring(0, this.value.length() - 1)));
            }

            if (BYTE.matcher(this.value).matches())
            {
                return new NBTTagByte(this.name, Byte.parseByte(this.value.substring(0, this.value.length() - 1)));
            }

            if (LONG.matcher(this.value).matches())
            {
                return new NBTTagLong(this.name, Long.parseLong(this.value.substring(0, this.value.length() - 1)));
            }

            if (SHORT.matcher(this.value).matches())
            {
                return new NBTTagShort(this.name, Short.parseShort(this.value.substring(0, this.value.length() - 1)));
            }

            if (INTEGER.matcher(this.value).matches())
            {
                return new NBTTagInt(this.name, Integer.parseInt(this.value));
            }

            if (DOUBLE_UNTYPED.matcher(this.value).matches())
            {
                return new NBTTagDouble(this.name, Double.parseDouble(this.value));
            }

            if (this.value.equalsIgnoreCase("true") || this.value.equalsIgnoreCase("false"))
            {
                return new NBTTagByte(this.name, (byte)(Boolean.parseBoolean(this.value) ? 1 : 0));
            }
        }
        catch (NumberFormatException var6)
        {
            this.value = this.value.replaceAll("\\\\\"", "\"");
            return new NBTTagString(this.name, this.value);
        }

        if (this.value.startsWith("[") && this.value.endsWith("]"))
        {
            String var7 = this.value.substring(1, this.value.length() - 1);
            String[] var8 = (String[])Iterables.toArray(SPLITTER.split(var7), String.class);

            try
            {
                int[] var5 = new int[var8.length];

                for (int j = 0; j < var8.length; ++j)
                {
                    var5[j] = Integer.parseInt(var8[j].trim());
                }

                return new NBTTagIntArray(this.name, var5);
            }
            catch (NumberFormatException var51)
            {
                return new NBTTagString(this.name, this.value);
            }
        }
        else
        {
            if (this.value.startsWith("\"") && this.value.endsWith("\""))
            {
                this.value = this.value.substring(1, this.value.length() - 1);
            }

            this.value = this.value.replaceAll("\\\\\"", "\"");
            StringBuilder stringbuilder = new StringBuilder();

            for (int i = 0; i < this.value.length(); ++i)
            {
                if (i < this.value.length() - 1 && this.value.charAt(i) == 92 && this.value.charAt(i + 1) == 92)
                {
                    stringbuilder.append('\\');
                    ++i;
                }
                else
                {
                    stringbuilder.append(this.value.charAt(i));
                }
            }

            return new NBTTagString(this.name, stringbuilder.toString());
        }
    }
}
