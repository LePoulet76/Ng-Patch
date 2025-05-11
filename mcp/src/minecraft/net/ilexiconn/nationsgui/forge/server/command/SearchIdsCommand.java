/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.item.Item
 */
package net.ilexiconn.nationsgui.forge.server.command;

import java.util.ArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;

public class SearchIdsCommand
extends CommandBase {
    public String func_71517_b() {
        return "searchids";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] args) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Item item : Item.field_77698_e) {
            if (item == null) continue;
            ids.add(item.field_77779_bT);
        }
        for (int i = 1; i <= 4096; ++i) {
            if (ids.contains(i)) continue;
            System.out.println(i);
        }
    }

    public int compareTo(Object o) {
        return 0;
    }
}

