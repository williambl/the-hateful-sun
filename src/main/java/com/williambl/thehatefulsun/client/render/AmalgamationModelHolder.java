package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.entity.AmalgamationEntity;

public class AmalgamationModelHolder extends AmalgamationModel {

    AmalgamationModel[] models = {
            new AmalgamationQuadrupedModel(),
            new AmalgamationBlobModel(),
            new AmalgamationBigModel(),
            new AmalgamationBlobModel()
    };

    public AmalgamationModel getActualModel(AmalgamationEntity entity) {
        return models[entity.getAmalgamationType()];
    }

    @Override
    public void render(AmalgamationEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        getActualModel(entityIn).render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
