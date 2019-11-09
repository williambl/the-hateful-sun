package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import net.minecraft.entity.Entity;

public class AmalgamationModelHolder extends AmalgamationModel {

    AmalgamationModel[] models = {
            new AmalgamationQuadrupedModel(),
            new AmalgamationBlobModel(),
            new AmalgamationBigModel()
    };

    public AmalgamationModel getActualModel(AmalgamationEntity entity) {
        return models[entity.getAmalgamationType()];
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        getActualModel((AmalgamationEntity)entityIn).render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
