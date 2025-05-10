package net.ilexiconn.nationsgui.forge.server.command;

import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class AnimCommand extends CommandBase
{
    public String getCommandName()
    {
        return "anim";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "/anim <give/remove/reset> <username> [anim]";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            throw new CommandException(this.getCommandUsage(sender), new Object[0]);
        }
        else
        {
            String command = args[0];

            if (args.length < 2)
            {
                throw new CommandException(this.getCommandUsage(sender), new Object[0]);
            }
            else
            {
                String username = args[1];

                if (!command.equals("give") && !command.equals("remove") && !command.equals("reset"))
                {
                    throw new CommandException(this.getCommandUsage(sender), new Object[0]);
                }
                else
                {
                    if (command.equals("give"))
                    {
                        this.handleGive(sender, username, args[2]);
                    }
                    else if (command.equals("remove"))
                    {
                        this.handleRemove(sender, username, args[2]);
                    }
                    else if (command.equals("reset"))
                    {
                        this.handleReset(sender, username);
                    }
                }
            }
        }
    }

    public void handleGive(ICommandSender sender, String to, String anim)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Anims");
        NBTTagCompound user = compound.getCompoundTag(to);

        if (!user.hasKey("AvailableAnims"))
        {
            user.setTag("AvailableAnims", new NBTTagString(""));
        }
        else if (user.getString("AvailableAnims").contains(anim.toLowerCase()))
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("nationsgui.command.anim.has_already", new Object[] {to, anim}).setColor(EnumChatFormatting.RED));
            return;
        }

        String availableBadges = user.getTag("AvailableAnims") + "," + anim;
        user.setString("AvailableAnims", availableBadges.replaceAll("^,|,$", ""));
        compound.setCompoundTag(to, user);
        sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("nationsgui.command.anim.gave", new Object[] {anim, to}));
        NBTConfig.CONFIG.save();
    }

    public void handleRemove(ICommandSender sender, String to, String badge)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Anims");
        NBTTagCompound user = compound.getCompoundTag(to);

        if (user.getString("AvailableAnims") != null)
        {
            if (user.getString("AvailableAnims").contains(badge))
            {
                String regex = "[,]?" + badge;
                user.setString("AvailableAnims", user.getString("AvailableAnims").replaceAll(regex, "").replaceAll("null", "").replaceAll("^,|,$", ""));
                compound.setCompoundTag(to, user);
                sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("nationsgui.command.anim.removed", new Object[] {badge, to}));
                NBTConfig.CONFIG.save();
            }
            else
            {
                sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("nationsgui.command.anim.not_found", new Object[] {to, badge}).setColor(EnumChatFormatting.RED));
            }
        }
        else
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("nationsgui.command.anim.not_found", new Object[] {to, badge}).setColor(EnumChatFormatting.RED));
        }
    }

    public void handleReset(ICommandSender sender, String to)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Anims");
        NBTTagCompound user = compound.getCompoundTag(to);

        if (user.getString("AvailableAnims") != null)
        {
            user.setString("AvailableAnims", "");
        }

        compound.setCompoundTag(to, user);
        NBTConfig.CONFIG.save();
    }

    public int compareTo(Object o)
    {
        return this.compareTo((ICommand)o);
    }
}
