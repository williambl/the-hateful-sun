package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.TheHatefulSun;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class MutatedPumpkinRenderer extends RenderLiving<MutatedPumpkinEntity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation(TheHatefulSun.MODID, "textures/entity/mutated_pumpkin/mutated_pumpkin.png");

   public MutatedPumpkinRenderer(RenderManager renderManagerIn) {
      super(renderManagerIn, new MutatedPumpkinModel(), 0.25F);
   }

   public void doRender(MutatedPumpkinEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.shadowSize = 1f;
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   @Nullable
   @Override
   protected ResourceLocation getEntityTexture(MutatedPumpkinEntity entity) {
      return TEXTURE;
   }

}