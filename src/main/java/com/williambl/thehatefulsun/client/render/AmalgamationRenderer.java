package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.TheHatefulSun;
import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class AmalgamationRenderer extends RenderLiving<AmalgamationEntity> {
   private static final ResourceLocation[] TEXTURES = {
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/quadruped.png"),
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/blob.png"),
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/big.png")
   };

   public AmalgamationRenderer(RenderManager renderManagerIn) {
      super(renderManagerIn, new AmalgamationModelHolder(), 0.25F);
   }

   @Override
   public void doRender(AmalgamationEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.shadowSize = 1f;
      this.setRotationAngles(
              entity,
              entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks),
              interpolateRotation(entity.prevLimbSwingAmount, entity.limbSwingAmount, partialTicks),
              interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks)-interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks),
              interpolateRotation(entity.prevRotationPitch, entity.rotationPitch, partialTicks)
      );
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   private void setRotationAngles(AmalgamationEntity entity, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
      switch (entity.getAmalgamationType()) {
         case 0:
            AmalgamationQuadrupedModel quadrupedModel = (AmalgamationQuadrupedModel) ((AmalgamationModelHolder)this.mainModel).getActualModel(entity);
            quadrupedModel.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            quadrupedModel.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            quadrupedModel.Head2.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            quadrupedModel.Head2.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            quadrupedModel.Leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            quadrupedModel.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            quadrupedModel.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            quadrupedModel.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            break;
         case 1:
            AmalgamationBlobModel blobModel = (AmalgamationBlobModel) ((AmalgamationModelHolder)this.mainModel).getActualModel(entity);
            blobModel.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            blobModel.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            blobModel.Head2.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            blobModel.Head2.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            break;
         case 2:
            AmalgamationBigModel bigModel = (AmalgamationBigModel) ((AmalgamationModelHolder)this.mainModel).getActualModel(entity);
            bigModel.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            bigModel.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            bigModel.Head2.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            bigModel.Head2.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            bigModel.Head3.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            bigModel.Head3.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            bigModel.Head4.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            bigModel.Head4.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            bigModel.Leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            bigModel.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            bigModel.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            bigModel.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            bigModel.Leg5.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            bigModel.Leg6.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            bigModel.Leg7.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            bigModel.Leg8.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            break;
      }

   }

   @Nullable
   @Override
   protected ResourceLocation getEntityTexture(AmalgamationEntity entity) {
      return TEXTURES[entity.getAmalgamationType()];
   }

}