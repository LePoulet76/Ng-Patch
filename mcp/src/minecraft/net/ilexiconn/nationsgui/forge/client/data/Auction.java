package net.ilexiconn.nationsgui.forge.client.data;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.math.BigInteger;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Auction
{
    private String uuid;
    private String creator;
    private boolean isStaffAuction;
    private long startingTime;
    private long duration;
    private long superexpiry;
    private String lastBidder;
    private String bidders;
    private int currentAuction;
    private int itemId;
    private int quantity;
    private String nbt;
    private ItemStack stack;

    public String getUuid()
    {
        return this.uuid;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public boolean isStaffAuction()
    {
        return this.isStaffAuction;
    }

    public long getStartingTime()
    {
        return this.startingTime;
    }

    public long getDuration()
    {
        return this.duration;
    }

    public long getSuperExpiry()
    {
        return this.superexpiry;
    }

    public String getLastBidder()
    {
        return this.lastBidder;
    }

    public String getBidders()
    {
        return this.bidders;
    }

    public int getCurrentAuction()
    {
        return this.currentAuction;
    }

    public int getItemId()
    {
        return this.itemId;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public String getNbt()
    {
        return this.nbt;
    }

    public boolean equals(Object obj)
    {
        return obj instanceof Auction ? this.getUuid().equals(((Auction)obj).getUuid()) : false;
    }

    public void replace(Auction origin)
    {
        this.uuid = origin.uuid;
        this.creator = origin.creator;
        this.isStaffAuction = origin.isStaffAuction;
        this.startingTime = origin.startingTime;
        this.duration = origin.duration;
        this.superexpiry = origin.superexpiry;
        this.lastBidder = origin.lastBidder;
        this.bidders = origin.bidders;
        this.currentAuction = origin.currentAuction;
        this.itemId = origin.itemId;
        this.quantity = origin.quantity;
        this.nbt = origin.nbt;
        this.setItemStack();
    }

    public void setItemStack()
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream((new BigInteger(this.nbt, 32)).toByteArray());
        NBTTagCompound nbt = (NBTTagCompound)NBTTagCompound.readNamedTag(new DataInputStream(inputStream));
        this.stack = ItemStack.loadItemStackFromNBT(nbt);
        this.stack.stackSize = this.getQuantity();
    }

    public ItemStack getItemStack()
    {
        return this.stack;
    }

    public long getExpiry()
    {
        return this.getStartingTime() + this.getDuration();
    }
}
