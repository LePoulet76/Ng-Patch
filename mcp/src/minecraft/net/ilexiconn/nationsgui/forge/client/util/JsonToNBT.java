package net.ilexiconn.nationsgui.forge.client.util;

import java.util.Stack;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$Compound;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$List;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$NBTParser;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT$Primitive;
import net.minecraft.nbt.NBTTagCompound;

public class JsonToNBT
{
    private static final Pattern PATTERN = Pattern.compile("\\[[-+\\d|,\\s]+\\]");

    public static NBTTagCompound getTagFromJson(String jsonString)
    {
        jsonString = jsonString.trim();
        return (NBTTagCompound)getParser("tag", jsonString).parse();
    }

    static JsonToNBT$NBTParser getParserFor(String ... json)
    {
        return getParser(json[0], json[1]);
    }

    static JsonToNBT$NBTParser getParser(String name, String json)
    {
        json = json.trim();
        String string;
        char c;

        if (json.startsWith("{"))
        {
            json = json.substring(1, json.length() - 1);
            JsonToNBT$Compound list1;

            for (list1 = new JsonToNBT$Compound(name); json.length() > 0; json = json.substring(string.length() + 1))
            {
                string = func_150314_a(json, true);

                if (string.length() > 0)
                {
                    list1.parserList.add(func_179270_a(string, false));
                }

                if (json.length() < string.length() + 1)
                {
                    break;
                }

                c = json.charAt(string.length());

                if (c != 44 && c != 123 && c != 125 && c != 91 && c != 93)
                {
                    throw new RuntimeException("Unexpected token \'" + c + "\' at: " + json.substring(string.length()));
                }
            }

            return list1;
        }
        else if (json.startsWith("[") && !PATTERN.matcher(json).matches())
        {
            json = json.substring(1, json.length() - 1);
            JsonToNBT$List list;

            for (list = new JsonToNBT$List(name); json.length() > 0; json = json.substring(string.length() + 1))
            {
                string = func_150314_a(json, false);

                if (string.length() > 0)
                {
                    list.parserList.add(func_179270_a(string, true));
                }

                if (json.length() < string.length() + 1)
                {
                    break;
                }

                c = json.charAt(string.length());

                if (c != 44 && c != 123 && c != 125 && c != 91 && c != 93)
                {
                    throw new RuntimeException("Unexpected token \'" + c + "\' at: " + json.substring(string.length()));
                }
            }

            return list;
        }
        else
        {
            return new JsonToNBT$Primitive(name, json);
        }
    }

    private static JsonToNBT$NBTParser func_179270_a(String p_179270_0_, boolean p_179270_1_)
    {
        String s = func_150313_b(p_179270_0_, p_179270_1_);
        String s1 = func_150311_c(p_179270_0_, p_179270_1_);
        return getParserFor(new String[] {s, s1});
    }

    private static String func_150314_a(String p_150314_0_, boolean p_150314_1_)
    {
        int i = func_150312_a(p_150314_0_, ':');
        int j = func_150312_a(p_150314_0_, ',');

        if (p_150314_1_)
        {
            if (i == -1)
            {
                throw new RuntimeException("Unable to locate name/value separator for string: " + p_150314_0_);
            }

            if (j != -1 && j < i)
            {
                throw new RuntimeException("Name error at: " + p_150314_0_);
            }
        }
        else if (i == -1 || i > j)
        {
            i = -1;
        }

        return func_179269_a(p_150314_0_, i);
    }

    private static String func_179269_a(String p_179269_0_, int p_179269_1_)
    {
        Stack stack = new Stack();
        int i = p_179269_1_ + 1;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;

        for (int j = 0; i < p_179269_0_.length(); ++i)
        {
            char c0 = p_179269_0_.charAt(i);

            if (c0 == 34)
            {
                if (func_179271_b(p_179269_0_, i))
                {
                    if (!flag)
                    {
                        throw new RuntimeException("Illegal use of \\\": " + p_179269_0_);
                    }
                }
                else
                {
                    flag = !flag;

                    if (flag && !flag2)
                    {
                        flag1 = true;
                    }

                    if (!flag)
                    {
                        j = i;
                    }
                }
            }
            else if (!flag)
            {
                if (c0 != 123 && c0 != 91)
                {
                    if (c0 == 125 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 123))
                    {
                        throw new RuntimeException("Unbalanced curly brackets {}: " + p_179269_0_);
                    }

                    if (c0 == 93 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 91))
                    {
                        throw new RuntimeException("Unbalanced square brackets []: " + p_179269_0_);
                    }

                    if (c0 == 44 && stack.isEmpty())
                    {
                        return p_179269_0_.substring(0, i);
                    }
                }
                else
                {
                    stack.push(Character.valueOf(c0));
                }
            }

            if (!Character.isWhitespace(c0))
            {
                if (!flag && flag1 && j != i)
                {
                    return p_179269_0_.substring(0, j + 1);
                }

                flag2 = true;
            }
        }

        return p_179269_0_.substring(0, i);
    }

    private static String func_150313_b(String p_150313_0_, boolean p_150313_1_)
    {
        if (p_150313_1_)
        {
            p_150313_0_ = p_150313_0_.trim();

            if (p_150313_0_.startsWith("{") || p_150313_0_.startsWith("["))
            {
                return "";
            }
        }

        int i = func_150312_a(p_150313_0_, ':');

        if (i == -1)
        {
            if (p_150313_1_)
            {
                return "";
            }
            else
            {
                throw new RuntimeException("Unable to locate name/value separator for string: " + p_150313_0_);
            }
        }
        else
        {
            return p_150313_0_.substring(0, i).trim();
        }
    }

    private static String func_150311_c(String p_150311_0_, boolean p_150311_1_)
    {
        if (p_150311_1_)
        {
            p_150311_0_ = p_150311_0_.trim();

            if (p_150311_0_.startsWith("{") || p_150311_0_.startsWith("["))
            {
                return p_150311_0_;
            }
        }

        int i = func_150312_a(p_150311_0_, ':');

        if (i == -1)
        {
            if (p_150311_1_)
            {
                return p_150311_0_;
            }
            else
            {
                throw new RuntimeException("Unable to locate name/value separator for string: " + p_150311_0_);
            }
        }
        else
        {
            return p_150311_0_.substring(i + 1).trim();
        }
    }

    private static int func_150312_a(String p_150312_0_, char p_150312_1_)
    {
        int i = 0;

        for (boolean flag = true; i < p_150312_0_.length(); ++i)
        {
            char c0 = p_150312_0_.charAt(i);

            if (c0 == 34)
            {
                if (!func_179271_b(p_150312_0_, i))
                {
                    flag = !flag;
                }
            }
            else if (flag)
            {
                if (c0 == p_150312_1_)
                {
                    return i;
                }

                if (c0 == 123 || c0 == 91)
                {
                    return -1;
                }
            }
        }

        return -1;
    }

    private static boolean func_179271_b(String p_179271_0_, int p_179271_1_)
    {
        return p_179271_1_ > 0 && p_179271_0_.charAt(p_179271_1_ - 1) == 92 && !func_179271_b(p_179271_0_, p_179271_1_ - 1);
    }
}
