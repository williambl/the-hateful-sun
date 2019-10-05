package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.TheHatefulSun;
import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
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
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   @Nullable
   @Override
   protected ResourceLocation getEntityTexture(AmalgamationEntity entity) {
      return TEXTURE;
   }

}