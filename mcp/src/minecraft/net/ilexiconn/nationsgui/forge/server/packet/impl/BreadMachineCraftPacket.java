package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.collect.UnmodifiableIterator;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.common.recipes.BreadRecipes;
import fr.nationsglory.common.recipes.BreadRecipes.BreadRecipe;
import fr.nationsglory.ngcontent.server.item.ItemCustomBread;
import fr.nationsglory.server.block.entity.GCBreadMachineBlockEntity;
import java.util.Arrays;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BreadMachineCraftPacket implements IPacket, IServerPacket
{
    private String breadName;
    private int tileX;
    private int tileY;
    private int tileZ;

    public BreadMachineCraftPacket(String breadName, int tileX, int tileY, int tileZ)
    {
        this.breadName = breadName;
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileZ = tileZ;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.breadName = data.readUTF();
        this.tileX = data.readInt();
        this.tileY = data.readInt();
        this.tileZ = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.breadName);
        data.writeInt(this.tileX);
        data.writeInt(this.tileY);
        data.writeInt(this.tileZ);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        if (BreadRecipes.RECIPES.containsKey(this.breadName) && player.inventory.getFirstEmptyStack() != -1)
        {
            TileEntity tileEntity = player.getEntityWorld().getBlockTileEntity(this.tileX, this.tileY, this.tileZ);

            if (tileEntity instanceof GCBreadMachineBlockEntity && ((GCBreadMachineBlockEntity)tileEntity).energyStored >= 10.0F)
            {
                int farmerLevel = NationsGUI.getPlayerSkillLevel(player.username, "farmer");

                if (this.breadName.equals("ginger") && farmerLevel < 4 || this.breadName.equals("tahde") && farmerLevel < 5 || this.breadName.equals("martian") && farmerLevel < 5 || this.breadName.equals("frozen") && farmerLevel < 4 || this.breadName.equals("gas") && farmerLevel < 4)
                {
                    player.addChatMessage(Translation.get("\u00a7cVous n\'avez pas le niveau requis dans la comp\u00e9tence Agriculteur pour fabriquer ce pain."));
                    return;
                }

                int researchLevelResource = ServerUtils.getCountryResearchLevel(player.username, "resource");

                if (this.breadName.equals("frozen") && researchLevelResource < 12 || this.breadName.equals("gas") && researchLevelResource < 12)
                {
                    player.addChatMessage(Translation.get("\u00a7cVotre pays n\'a pas le niveau de recherche requis pour fabriquer ce pain."));
                    return;
                }

                if (this.breadName.equals("tahde") && researchLevelResource < 10 || this.breadName.equals("martian") && researchLevelResource < 10)
                {
                    player.addChatMessage(Translation.get("\u00a7cVotre pays n\'a pas le niveau de recherche requis pour fabriquer ce pain."));
                    return;
                }

                if (this.breadName.equals("energy") && researchLevelResource < 13 || this.breadName.equals("golden") && researchLevelResource < 13)
                {
                    player.addChatMessage(Translation.get("\u00a7cVotre pays n\'a pas le niveau de recherche requis pour fabriquer ce pain."));
                    return;
                }

                BreadRecipe recipe = BreadRecipes.getRecipe(this.breadName);
                UnmodifiableIterator itemStack = recipe.getMaterials().iterator();
                ItemStack stack;

                while (itemStack.hasNext())
                {
                    stack = (ItemStack)itemStack.next();

                    if (!this.hasItemStack(player.inventory, stack))
                    {
                        return;
                    }
                }

                itemStack = recipe.getMaterials().iterator();

                while (itemStack.hasNext())
                {
                    stack = (ItemStack)itemStack.next();

                    if (!removeItemWithAmount(player.inventory, stack.copy()))
                    {
                        return;
                    }
                }

                ItemStack itemStack1 = ItemCustomBread.getBreadItemFromName(this.breadName);
                itemStack1.stackSize = 4;

                if (farmerLevel == 2)
                {
                    itemStack1.stackSize = 5;
                }
                else if (farmerLevel >= 3)
                {
                    itemStack1.stackSize = 6;
                }

                if (itemStack1 != null)
                {
                    player.inventory.addItemStackToInventory(itemStack1);
                    ((GCBreadMachineBlockEntity)tileEntity).lastBreadCraft = Long.valueOf(System.currentTimeMillis());
                    ((GCBreadMachineBlockEntity)tileEntity).energyStored -= 10.0F;
                    NationsGUI.addPlayerSkill(player.username, "farmer", 20);
                }
            }
        }
    }

    public boolean hasItemStack(InventoryPlayer inventory, ItemStack stack)
    {
        int count = 0;
        Iterator var4 = Arrays.asList(inventory.mainInventory).iterator();

        while (var4.hasNext())
        {
            ItemStack stack1 = (ItemStack)var4.next();

            if (stack1 != null && stack1.getItem() == stack.getItem())
            {
                count += stack1.stackSize;
            }
        }

        return stack.stackSize <= count;
    }

    public static boolean removeItemWithAmount(InventoryPlayer inventory, ItemStack stackToRemove)
    {
        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            ItemStack stackToCheck = inventory.getStackInSlot(i);

            if (stackToCheck != null && stackToRemove.itemID == stackToCheck.itemID && stackToRemove.getItemDamage() == stackToCheck.getItemDamage() && stackToCheck.stackSize > 0)
            {
                int oldStackSize = stackToCheck.stackSize;
                inventory.decrStackSize(i, Math.min(stackToCheck.stackSize, stackToRemove.stackSize));
                stackToRemove.stackSize -= Math.min(oldStackSize, stackToRemove.stackSize);

                if (stackToRemove.stackSize == 0)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
