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
public class AmalgamationRenderer extends MobRenderer<AmalgamationEntity, AmalgamationModel> {
   private static final ResourceLocation TEXTURE = new ResourceLocation(TheHatefulSun.MODID, "textures/entity/amalgamation/amalgamation.png");

   public AmalgamationRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new AmalgamationModel(), 0.25F);
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

   private void setRotationAngles(AmalgamationEntity entityIn, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch)  {
      this.entityModel.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
      this.entityModel.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
      this.entityModel.Head2.rotateAngleX = headPitch * ((float)Math.PI / 180F);
      this.entityModel.Head2.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
      this.entityModel.Leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.entityModel.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.entityModel.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.entityModel.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
   }

   @Nullable
   @Override
   protected ResourceLocation getEntityTexture(AmalgamationEntity entity) {
      return TEXTURE;
   }

}