/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommand
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagString
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.EnumChatFormatting
 */
package net.ilexiconn.nationsgui.forge.server.command;

import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class AnimCommand
extends CommandBase {
    public String func_71517_b() {
        return "anim";
    }

    public String func_71518_a(ICommandSender sender) {
        return "/anim <give/remove/reset> <username> [anim]";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
        String command = args[0];
        if (args.length < 2) {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
        String username = args[1];
        if (!(command.equals("give") || command.equals("remove") || command.equals("reset"))) {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
        if (command.equals("give")) {
            this.handleGive(sender, username, args[2]);
        } else if (command.equals("remove")) {
            this.handleRemove(sender, username, args[2]);
        } else if (command.equals("reset")) {
            this.handleReset(sender, username);
        }
    }

    public void handleGive(ICommandSender sender, String to, String anim) {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Anims");
        NBTTagCompound user = compound.func_74775_l(to);
        if (!user.func_74764_b("AvailableAnims")) {
            user.func_74782_a("AvailableAnims", (NBTBase)new NBTTagString(""));
        } else if (user.func_74779_i("AvailableAnims").contains(anim.toLowerCase())) {
            sender.func_70006_a(ChatMessageComponent.func_111082_b((String)"nationsgui.command.anim.has_already", (Object[])new Object[]{to, anim}).func_111059_a(EnumChatFormatting.RED));
            return;
        }
        String availableBadges = user.func_74781_a("AvailableAnims") + "," + anim;
        user.func_74778_a("AvailableAnims", availableBadges.replaceAll("^,|,$", ""));
        compound.func_74766_a(to, user);
        sender.func_70006_a(ChatMessageComponent.func_111082_b((String)"nationsgui.command.anim.gave", (Object[])new Object[]{anim, to}));
        NBTConfig.CONFIG.save();
    }

    public void handleRemove(ICommandSender sender, String to, String badge) {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Anims");
        NBTTagCompound user = compound.func_74775_l(to);
        if (user.func_74779_i("AvailableAnims") != null) {
            if (!user.func_74779_i("AvailableAnims").contains(badge)) {
                sender.func_70006_a(ChatMessageComponent.func_111082_b((String)"nationsgui.command.anim.not_found", (Object[])new Object[]{to, badge}).func_111059_a(EnumChatFormatting.RED));
                return;
            }
        } else {
            sender.func_70006_a(ChatMessageComponent.func_111082_b((String)"nationsgui.command.anim.not_found", (Object[])new Object[]{to, badge}).func_111059_a(EnumChatFormatting.RED));
            return;
        }
        String regex = "[,]?" + badge;
        user.func_74778_a("AvailableAnims", user.func_74779_i("AvailableAnims").replaceAll(regex, "").replaceAll("null", "").replaceAll("^,|,$", ""));
        compound.func_74766_a(to, user);
        sender.func_70006_a(ChatMessageComponent.func_111082_b((String)"nationsgui.command.anim.removed", (Object[])new Object[]{badge, to}));
        NBTConfig.CONFIG.save();
    }

    public void handleReset(ICommandSender sender, String to) {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Anims");
        NBTTagCompound user = compound.func_74775_l(to);
        if (user.func_74779_i("AvailableAnims") != null) {
            user.func_74778_a("AvailableAnims", "");
        }
        compound.func_74766_a(to, user);
        NBTConfig.CONFIG.save();
    }

    public int compareTo(Object o) {
        return this.func_71525_a((ICommand)o);
    }
}

