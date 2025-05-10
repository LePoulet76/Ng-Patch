package net.ilexiconn.nationsgui.forge.client.voices.sound;

import net.ilexiconn.nationsgui.forge.client.voices.debug.MovingAverage;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;

public class PlayableStream
{
    public final int id;
    public int voiceMode;
    public Entity entity;
    public boolean end;
    public long lastUpdated;
    public long lastFlushed;
    private MovingAverage jitterAverage = new MovingAverage(5);
    public JitterBuffer buffer;
    private SoundManager manager;

    public PlayableStream(SoundManager manager, int id, int voiceMode)
    {
        this.manager = manager;
        this.id = id;
        this.voiceMode = voiceMode;
        this.lastUpdated = System.currentTimeMillis();
        this.lastFlushed = System.currentTimeMillis();
        this.buffer = new JitterBuffer(SoundManager.universalAudioFormat, 0);
    }

    public int getJitterRate()
    {
        return this.jitterAverage.getAverage().intValue();
    }

    public String getEntityName()
    {
        return "missingno" + this.id;
    }

    public boolean isEntityPlayer()
    {
        return this.entity instanceof AbstractClientPlayer;
    }

    public void update(int l)
    {
        this.jitterAverage.add(l);
    }

    public EntityVector getCustomEntityVector()
    {
        EntityVector vector = this.getEntityVector();
        return vector.entityName != null ? vector : (this.entity != null ? new EntityVector(this.id, "", this.entity.posX, this.entity.posY, this.entity.posZ, this.entity.motionX, this.entity.motionY, this.entity.motionZ) : EntityVector.NULL);
    }

    public EntityVector getEntityVector()
    {
        return this.manager.entityData.containsKey(Integer.valueOf(this.id)) ? (EntityVector)this.manager.entityData.get(Integer.valueOf(this.id)) : null;
    }

    public AbstractClientPlayer getPlayer()
    {
        return (AbstractClientPlayer)this.entity;
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }
}
