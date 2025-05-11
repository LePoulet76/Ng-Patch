/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagByte
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagDouble
 *  net.minecraft.nbt.NBTTagFloat
 *  net.minecraft.nbt.NBTTagInt
 *  net.minecraft.nbt.NBTTagIntArray
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagLong
 *  net.minecraft.nbt.NBTTagShort
 *  net.minecraft.nbt.NBTTagString
 */
package net.ilexiconn.nationsgui.forge.client.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Stack;
import java.util.regex.Pattern;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

public class JsonToNBT {
    private static final Pattern PATTERN = Pattern.compile("\\[[-+\\d|,\\s]+\\]");

    public static NBTTagCompound getTagFromJson(String jsonString) {
        jsonString = jsonString.trim();
        return (NBTTagCompound)JsonToNBT.getParser("tag", jsonString).parse();
    }

    static NBTParser getParserFor(String ... json) {
        return JsonToNBT.getParser(json[0], json[1]);
    }

    static NBTParser getParser(String name, String json) {
        if ((json = json.trim()).startsWith("{")) {
            json = json.substring(1, json.length() - 1);
            Compound compound = new Compound(name);
            while (json.length() > 0) {
                String string = JsonToNBT.func_150314_a(json, true);
                if (string.length() > 0) {
                    compound.parserList.add(JsonToNBT.func_179270_a(string, false));
                }
                if (json.length() < string.length() + 1) break;
                char c = json.charAt(string.length());
                if (c != ',' && c != '{' && c != '}' && c != '[' && c != ']') {
                    throw new RuntimeException("Unexpected token '" + c + "' at: " + json.substring(string.length()));
                }
                json = json.substring(string.length() + 1);
            }
            return compound;
        }
        if (json.startsWith("[") && !PATTERN.matcher(json).matches()) {
            json = json.substring(1, json.length() - 1);
            List list = new List(name);
            while (json.length() > 0) {
                String string = JsonToNBT.func_150314_a(json, false);
                if (string.length() > 0) {
                    list.parserList.add(JsonToNBT.func_179270_a(string, true));
                }
                if (json.length() < string.length() + 1) break;
                char c = json.charAt(string.length());
                if (c != ',' && c != '{' && c != '}' && c != '[' && c != ']') {
                    throw new RuntimeException("Unexpected token '" + c + "' at: " + json.substring(string.length()));
                }
                json = json.substring(string.length() + 1);
            }
            return list;
        }
        return new Primitive(name, json);
    }

    private static NBTParser func_179270_a(String p_179270_0_, boolean p_179270_1_) {
        String s = JsonToNBT.func_150313_b(p_179270_0_, p_179270_1_);
        String s1 = JsonToNBT.func_150311_c(p_179270_0_, p_179270_1_);
        return JsonToNBT.getParserFor(s, s1);
    }

    private static String func_150314_a(String p_150314_0_, boolean p_150314_1_) {
        int i = JsonToNBT.func_150312_a(p_150314_0_, ':');
        int j = JsonToNBT.func_150312_a(p_150314_0_, ',');
        if (p_150314_1_) {
            if (i == -1) {
                throw new RuntimeException("Unable to locate name/value separator for string: " + p_150314_0_);
            }
            if (j != -1 && j < i) {
                throw new RuntimeException("Name error at: " + p_150314_0_);
            }
        } else if (i == -1 || i > j) {
            i = -1;
        }
        return JsonToNBT.func_179269_a(p_150314_0_, i);
    }

    private static String func_179269_a(String p_179269_0_, int p_179269_1_) {
        int i;
        Stack<Character> stack = new Stack<Character>();
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        int j = 0;
        for (i = p_179269_1_ + 1; i < p_179269_0_.length(); ++i) {
            char c0 = p_179269_0_.charAt(i);
            if (c0 == '\"') {
                if (JsonToNBT.func_179271_b(p_179269_0_, i)) {
                    if (!flag) {
                        throw new RuntimeException("Illegal use of \\\": " + p_179269_0_);
                    }
                } else {
                    boolean bl = flag = !flag;
                    if (flag && !flag2) {
                        flag1 = true;
                    }
                    if (!flag) {
                        j = i;
                    }
                }
            } else if (!flag) {
                if (c0 != '{' && c0 != '[') {
                    if (c0 == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{')) {
                        throw new RuntimeException("Unbalanced curly brackets {}: " + p_179269_0_);
                    }
                    if (c0 == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '[')) {
                        throw new RuntimeException("Unbalanced square brackets []: " + p_179269_0_);
                    }
                    if (c0 == ',' && stack.isEmpty()) {
                        return p_179269_0_.substring(0, i);
                    }
                } else {
                    stack.push(Character.valueOf(c0));
                }
            }
            if (Character.isWhitespace(c0)) continue;
            if (!flag && flag1 && j != i) {
                return p_179269_0_.substring(0, j + 1);
            }
            flag2 = true;
        }
        return p_179269_0_.substring(0, i);
    }

    private static String func_150313_b(String p_150313_0_, boolean p_150313_1_) {
        if (p_150313_1_ && ((p_150313_0_ = p_150313_0_.trim()).startsWith("{") || p_150313_0_.startsWith("["))) {
            return "";
        }
        int i = JsonToNBT.func_150312_a(p_150313_0_, ':');
        if (i == -1) {
            if (p_150313_1_) {
                return "";
            }
            throw new RuntimeException("Unable to locate name/value separator for string: " + p_150313_0_);
        }
        return p_150313_0_.substring(0, i).trim();
    }

    private static String func_150311_c(String p_150311_0_, boolean p_150311_1_) {
        if (p_150311_1_ && ((p_150311_0_ = p_150311_0_.trim()).startsWith("{") || p_150311_0_.startsWith("["))) {
            return p_150311_0_;
        }
        int i = JsonToNBT.func_150312_a(p_150311_0_, ':');
        if (i == -1) {
            if (p_150311_1_) {
                return p_150311_0_;
            }
            throw new RuntimeException("Unable to locate name/value separator for string: " + p_150311_0_);
        }
        return p_150311_0_.substring(i + 1).trim();
    }

    private static int func_150312_a(String p_150312_0_, char p_150312_1_) {
        boolean flag = true;
        for (int i = 0; i < p_150312_0_.length(); ++i) {
            char c0 = p_150312_0_.charAt(i);
            if (c0 == '\"') {
                if (JsonToNBT.func_179271_b(p_150312_0_, i)) continue;
                flag = !flag;
                continue;
            }
            if (!flag) continue;
            if (c0 == p_150312_1_) {
                return i;
            }
            if (c0 != '{' && c0 != '[') continue;
            return -1;
        }
        return -1;
    }

    private static boolean func_179271_b(String p_179271_0_, int p_179271_1_) {
        return p_179271_1_ > 0 && p_179271_0_.charAt(p_179271_1_ - 1) == '\\' && !JsonToNBT.func_179271_b(p_179271_0_, p_179271_1_ - 1);
    }

    public static class Primitive
    extends NBTParser {
        private static final Pattern DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
        private static final Pattern FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
        private static final Pattern BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
        private static final Pattern LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
        private static final Pattern SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
        private static final Pattern INTEGER = Pattern.compile("[-+]?[0-9]+");
        private static final Pattern DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        private static final Splitter SPLITTER = Splitter.on((char)',').omitEmptyStrings();
        protected String value;

        public Primitive(String json, String value) {
            this.name = json;
            this.value = value;
        }

        @Override
        public NBTBase parse() {
            try {
                if (DOUBLE.matcher(this.value).matches()) {
                    return new NBTTagDouble(this.name, Double.parseDouble(this.value.substring(0, this.value.length() - 1)));
                }
                if (FLOAT.matcher(this.value).matches()) {
                    return new NBTTagFloat(this.name, Float.parseFloat(this.value.substring(0, this.value.length() - 1)));
                }
                if (BYTE.matcher(this.value).matches()) {
                    return new NBTTagByte(this.name, Byte.parseByte(this.value.substring(0, this.value.length() - 1)));
                }
                if (LONG.matcher(this.value).matches()) {
                    return new NBTTagLong(this.name, Long.parseLong(this.value.substring(0, this.value.length() - 1)));
                }
                if (SHORT.matcher(this.value).matches()) {
                    return new NBTTagShort(this.name, Short.parseShort(this.value.substring(0, this.value.length() - 1)));
                }
                if (INTEGER.matcher(this.value).matches()) {
                    return new NBTTagInt(this.name, Integer.parseInt(this.value));
                }
                if (DOUBLE_UNTYPED.matcher(this.value).matches()) {
                    return new NBTTagDouble(this.name, Double.parseDouble(this.value));
                }
                if (this.value.equalsIgnoreCase("true") || this.value.equalsIgnoreCase("false")) {
                    return new NBTTagByte(this.name, (byte)(Boolean.parseBoolean(this.value) ? 1 : 0));
                }
            }
            catch (NumberFormatException e) {
                this.value = this.value.replaceAll("\\\\\"", "\"");
                return new NBTTagString(this.name, this.value);
            }
            if (this.value.startsWith("[") && this.value.endsWith("]")) {
                String string = this.value.substring(1, this.value.length() - 1);
                String[] stringArray = (String[])Iterables.toArray((Iterable)SPLITTER.split((CharSequence)string), String.class);
                try {
                    int[] array = new int[stringArray.length];
                    for (int j = 0; j < stringArray.length; ++j) {
                        array[j] = Integer.parseInt(stringArray[j].trim());
                    }
                    return new NBTTagIntArray(this.name, array);
                }
                catch (NumberFormatException var5) {
                    return new NBTTagString(this.name, this.value);
                }
            }
            if (this.value.startsWith("\"") && this.value.endsWith("\"")) {
                this.value = this.value.substring(1, this.value.length() - 1);
            }
            this.value = this.value.replaceAll("\\\\\"", "\"");
            StringBuilder stringbuilder = new StringBuilder();
            for (int i = 0; i < this.value.length(); ++i) {
                if (i < this.value.length() - 1 && this.value.charAt(i) == '\\' && this.value.charAt(i + 1) == '\\') {
                    stringbuilder.append('\\');
                    ++i;
                    continue;
                }
                stringbuilder.append(this.value.charAt(i));
            }
            return new NBTTagString(this.name, stringbuilder.toString());
        }
    }

    public static class List
    extends NBTParser {
        protected java.util.List<NBTParser> parserList = Lists.newArrayList();

        public List(String name) {
            this.name = name;
        }

        @Override
        public NBTBase parse() {
            NBTTagList list = new NBTTagList();
            for (NBTParser nbtParser : this.parserList) {
                list.func_74742_a(nbtParser.parse());
            }
            return list;
        }
    }

    public static class Compound
    extends NBTParser {
        protected java.util.List<NBTParser> parserList = Lists.newArrayList();

        public Compound(String name) {
            this.name = name;
        }

        @Override
        public NBTBase parse() {
            NBTTagCompound compound = new NBTTagCompound();
            for (NBTParser nbtParser : this.parserList) {
                NBTBase base = nbtParser.parse();
                compound.func_74782_a(nbtParser.name, base);
            }
            return compound;
        }
    }

    public static abstract class NBTParser {
        protected String name;

        public abstract NBTBase parse();
    }
}

