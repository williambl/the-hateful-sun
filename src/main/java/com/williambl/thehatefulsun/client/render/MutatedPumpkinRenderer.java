package com.williambl.thehatefulsun.client.render;

import com.williambl.thehatefulsun.TheHatefulSun;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MutatedPumpkinRenderer extends MobRenderer<MutatedPumpkinEntity, MutatedPumpkinModel> {
   private static final ResourceLocation TEXTURE = new ResourceLocation(TheHatefulSun.MODID, "textures/entity/mutated_pumpkin/mutated_pumpkin.png");

   public MutatedPumpkinRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new MutatedPumpkinModel(), 0.25F);
   }

   public void doRender(MutatedPumpkinEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.shadowSize = 0.25F * (float)entity.getSlimeSize();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   @Nullable
   @Override
   protected ResourceLocation getEntityTexture(MutatedPumpkinEntity entity) {
      return TEXTURE;
   }

}