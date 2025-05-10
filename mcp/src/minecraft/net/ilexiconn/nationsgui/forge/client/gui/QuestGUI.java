package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.client.gui.player.GuiQuestLog;

public class QuestGUI extends GuiQuestLog
{
    public QuestGUI(EntityPlayer player)
    {
        super(player);
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int i, int i1, int i2)
    {
        super.mouseClicked(i, i1, i2);
    }
}
