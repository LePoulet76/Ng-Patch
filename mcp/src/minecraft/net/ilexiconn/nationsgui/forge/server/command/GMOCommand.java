/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommand
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.world.EnumGameType
 *  net.minecraft.world.WorldSettings
 *  net.minecraft.world.storage.SaveHandler
 */
package net.ilexiconn.nationsgui.forge.server.command;

import java.io.IOException;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.SaveHandler;

public class GMOCommand
extends CommandBase {
    public String func_71517_b() {
        return "gmo";
    }

    public String func_71518_a(ICommandSender sender) {
        return "/gmo <gamemode> <player>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length != 2) {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
        EnumGameType gameType = this.getGameModeFromCommand(sender, args[0]);
        String username = args[1];
        if (CommandUtils.isPlayerOnline(username)) {
            EntityPlayerMP player = CommandBase.func_82359_c((ICommandSender)sender, (String)username);
            player.func_71033_a(gameType);
            player.field_70143_R = 0.0f;
        } else {
            if (!(sender.func_130014_f_().func_72860_G() instanceof SaveHandler)) {
                throw new CommandException("Using deprecated save system", new Object[0]);
            }
            SaveHandler saveHandler = (SaveHandler)sender.func_130014_f_().func_72860_G();
            NBTTagCompound playerData = saveHandler.func_75764_a(username);
            if (playerData == null) {
                throw new CommandException("Player not found", new Object[0]);
            }
            playerData.func_74768_a("playerGameType", gameType.func_77148_a());
            try {
                CommandUtils.writePlayerData(saveHandler, username, playerData);
            }
            catch (IOException e) {
                throw new CommandException("Unable to write player data", new Object[0]);
            }
        }
        CommandBase.func_71524_a((ICommandSender)sender, (int)1, (String)"commands.gamemode.success.other", (Object[])new Object[]{username, ChatMessageComponent.func_111077_e((String)("gameMode." + gameType.func_77149_b()))});
    }

    private EnumGameType getGameModeFromCommand(ICommandSender sender, String arg) {
        return !arg.equalsIgnoreCase(EnumGameType.SURVIVAL.func_77149_b()) && !arg.equalsIgnoreCase("s") ? (!arg.equalsIgnoreCase(EnumGameType.CREATIVE.func_77149_b()) && !arg.equalsIgnoreCase("c") ? (!arg.equalsIgnoreCase(EnumGameType.ADVENTURE.func_77149_b()) && !arg.equalsIgnoreCase("a") ? WorldSettings.func_77161_a((int)CommandBase.func_71532_a((ICommandSender)sender, (String)arg, (int)0, (int)(EnumGameType.values().length - 2))) : EnumGameType.ADVENTURE) : EnumGameType.CREATIVE) : EnumGameType.SURVIVAL;
    }

    public List func_71516_a(ICommandSender sender, String[] args) {
        return args.length == 1 ? CommandBase.func_71530_a((String[])args, (String[])new String[]{"survival", "creative", "adventure"}) : null;
    }

    public int compareTo(Object o) {
        return this.func_71525_a((ICommand)o);
    }
}

