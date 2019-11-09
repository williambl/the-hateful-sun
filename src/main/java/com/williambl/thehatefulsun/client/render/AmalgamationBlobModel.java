package com.williambl.thehatefulsun.client.render;
//Made with Blockbench

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AmalgamationBlobModel extends AmalgamationModel {
	private final ModelRenderer Root;
	final ModelRenderer Head;
	final ModelRenderer Head2;

	public AmalgamationBlobModel() {
		textureWidth = 128;
		textureHeight = 128;

		Root = new ModelRenderer(this);
		Root.setRotationPoint(0.0F, 34.0F, 0.0F);
		Root.cubeList.add(new ModelBox(Root, 0, 0, -16.0F, -26.0F, -8.0F, 32, 16, 16, 0.0F, false));
		Root.cubeList.add(new ModelBox(Root, 0, 32, -7.0F, -19.0F, 8.0F, 14, 9, 11, 0.0F, false));
		Root.cubeList.add(new ModelBox(Root, 43, 45, -5.0F, -15.0F, 19.0F, 9, 5, 7, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(-7.0F, -23.0F, -7.5F);
		Root.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 26, 57, -3.0F, -4.0F, -6.5F, 6, 8, 7, 0.0F, false));

		Head2 = new ModelRenderer(this);
		Head2.setRotationPoint(6.0F, -19.0F, -7.5F);
		Root.addChild(Head2);
		Head2.cubeList.add(new ModelBox(Head2, 0, 52, -2.0F, -4.0F, -4.5F, 6, 8, 7, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Root.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}