package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import net.minecraft.client.model.ModelBiped;

public class EmoteState
{
    float[] states = new float[0];
    EmoteBase emote;

    public EmoteState(EmoteBase emote)
    {
        this.emote = emote;
    }

    public void save(ModelBiped model)
    {
        float[] values = new float[1];

        for (int i = 0; i < 42; ++i)
        {
            ModelAccessor.INSTANCE.getValues(model, i, values);
            this.states[i] = values[0];
        }
    }

    public void load(ModelBiped model)
    {
        if (this.states.length == 0)
        {
            this.states = new float[42];
        }
        else
        {
            float[] values = new float[1];

            for (int i = 0; i < 42; ++i)
            {
                values[0] = this.states[i];
                int part = i / 3 * 3;

                if (this.emote.usesBodyPart(part))
                {
                    ModelAccessor.INSTANCE.setValues(model, i, values);
                }
            }
        }
    }
}
