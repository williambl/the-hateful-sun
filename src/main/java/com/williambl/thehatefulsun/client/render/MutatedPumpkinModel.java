package com.williambl.thehatefulsun.client.render;
//Made with Blockbench

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MutatedPumpkinModel extends ModelBase {
	private final ModelRenderer Root;
	private final ModelRenderer Head;
	private final ModelRenderer Head2;
	private final ModelRenderer Head3;
	private final ModelRenderer Outside;

	public MutatedPumpkinModel() {
		textureWidth = 128;
		textureHeight = 128;

		Root = new ModelRenderer(this);
		Root.setRotationPoint(0.0F, 24.0F, 0.0F);
		Root.cubeList.add(new ModelBox(Root, 0, 0, -4.0F, -20.0F, -4.0F, 8, 8, 8, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(-11.0F, -32.0F, -14.0F);
		setRotationAngle(Head, -0.5236F, 0.6109F, 0.0F);
		Root.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 0, 64, -8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F, false));

		Head2 = new ModelRenderer(this);
		Head2.setRotationPoint(14.0F, -28.0F, -9.0F);
		setRotationAngle(Head2, -0.5236F, -0.6109F, 0.0F);
		Root.addChild(Head2);
		Head2.cubeList.add(new ModelBox(Head2, 64, 64, -8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F, false));

		Head3 = new ModelRenderer(this);
		Head3.setRotationPoint(-2.0F, -11.0F, -14.0F);
		setRotationAngle(Head3, 0.4363F, 0.1745F, 0.0F);
		Root.addChild(Head3);
		Head3.cubeList.add(new ModelBox(Head3, 0, 64, -8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F, false));

		Outside = new ModelRenderer(this);
		Outside.setRotationPoint(0.0F, 24.0F, 0.0F);
		Outside.cubeList.add(new ModelBox(Root, 0, 0, -16.0F, -32.0F, -16.0F, 32, 32, 32, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Root.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableNormalize();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		Outside.render(f5);
		GlStateManager.disableBlend();
		GlStateManager.disableNormalize();
		GlStateManager.popMatrix();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}