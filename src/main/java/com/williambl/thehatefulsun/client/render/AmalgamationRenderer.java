package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.TheHatefulSun;
import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class AmalgamationRenderer extends MobRenderer<AmalgamationEntity, AmalgamationModelHolder>{
   private static final ResourceLocation[] TEXTURES = {
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/quadruped.png"),
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/blob.png"),
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/big.png"),
           new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/blob.png")
   };

   public AmalgamationRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new AmalgamationModelHolder(), 0.25F);
   }

   @Override
   public void doRender(AmalgamationEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.shadowSize = 1f;
      this.setRotationAngles(
              entity,
              entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks),
              MathHelper.lerp(partialTicks, entity.prevLimbSwingAmount, entity.limbSwingAmount),
              MathHelper.func_219805_h(partialTicks, entity.prevRenderYawOffset, entity.renderYawOffset)-MathHelper.func_219805_h(partialTicks, entity.prevRotationYawHead, entity.rotationYawHead),
              MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch)
      );
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   private void setRotationAngles(AmalgamationEntity entity, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
      switch (entity.getAmalgamationType()) {
         case 0:
            AmalgamationQuadrupedModel quadrupedModel = (AmalgamationQuadrupedModel) this.entityModel.getActualModel(entity);
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
            AmalgamationBlobModel blobModel = (AmalgamationBlobModel) this.entityModel.getActualModel(entity);
            blobModel.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            blobModel.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            blobModel.Head2.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            blobModel.Head2.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            break;
         case 2:
            AmalgamationBigModel bigModel = (AmalgamationBigModel) this.entityModel.getActualModel(entity);
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