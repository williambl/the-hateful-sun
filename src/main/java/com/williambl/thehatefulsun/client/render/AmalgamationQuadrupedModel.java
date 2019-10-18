package com.williambl.thehatefulsun.client.render;
//Made with Blockbench

import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class AmalgamationQuadrupedModel extends AmalgamationModel {
	final RendererModel Root;
	final RendererModel HeadAndNeck;
	final RendererModel Head;
	final RendererModel HeadAndNeck2;
	final RendererModel Head2;
	final RendererModel Leg;
	final RendererModel Leg2;
	final RendererModel Leg3;
	final RendererModel Leg4;

	public AmalgamationQuadrupedModel() {
		textureWidth = 128;
		textureHeight = 128;

		Root = new RendererModel(this);
		Root.setRotationPoint(0.0F, 18.5F, 0.0F);
		Root.cubeList.add(new ModelBox(Root, 0, 0, -8.0F, -12.5F, -10.0F, 16, 9, 18, 0.0F, false));

		HeadAndNeck = new RendererModel(this);
		HeadAndNeck.setRotationPoint(6.0F, -9.5F, -10.0F);
		setRotationAngle(HeadAndNeck, -0.2618F, -0.3491F, 0.0F);
		Root.addChild(HeadAndNeck);
		HeadAndNeck.cubeList.add(new ModelBox(HeadAndNeck, 40, 41, -1.0864F, -1.1818F, -5.0534F, 2, 3, 5, 0.0F, false));

		Head = new RendererModel(this);
		Head.setRotationPoint(-0.0864F, 0.8182F, -3.0534F);
		setRotationAngle(Head, 0.3491F, 0.5236F, 0.0F);
		HeadAndNeck.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 18, 27, -3.0F, -5.0F, -4.0F, 5, 6, 4, 0.0F, false));

		HeadAndNeck2 = new RendererModel(this);
		HeadAndNeck2.setRotationPoint(-6.0F, -9.5F, -10.0F);
		setRotationAngle(HeadAndNeck2, -0.2618F, 0.3491F, 0.0F);
		Root.addChild(HeadAndNeck2);
		HeadAndNeck2.cubeList.add(new ModelBox(HeadAndNeck2, 36, 27, 0.0864F, -1.1818F, -4.0534F, 2, 3, 4, 0.0F, false));

		Head2 = new RendererModel(this);
		Head2.setRotationPoint(0.0864F, 0.8182F, -3.0534F);
		setRotationAngle(Head2, 0.3491F, -0.5236F, 0.0F);
		HeadAndNeck2.addChild(Head2);
		Head2.cubeList.add(new ModelBox(Head2, 0, 27, -1.0F, -6.0F, -4.0F, 5, 7, 4, 0.0F, false));

		Leg = new RendererModel(this);
		Leg.setRotationPoint(5.0F, 2.5F, -5.0F);
		Root.addChild(Leg);
		Leg.cubeList.add(new ModelBox(Leg, 33, 34, -1.0F, -6.0F, -2.0F, 3, 9, 3, 0.0F, false));

		Leg2 = new RendererModel(this);
		Leg2.setRotationPoint(-3.0F, 2.5F, -4.0F);
		Root.addChild(Leg2);
		Leg2.cubeList.add(new ModelBox(Leg2, 0, 38, -6.0F, -5.0F, -4.0F, 3, 8, 3, 0.0F, false));
		Leg2.cubeList.add(new ModelBox(Leg2, 45, 31, -6.0F, -8.0F, -4.0F, 3, 3, 3, 0.0F, false));

		Leg3 = new RendererModel(this);
		Leg3.setRotationPoint(5.3126F, -0.234F, 3.6428F);
		setRotationAngle(Leg3, 0.4363F, 0.0F, -0.4363F);
		Root.addChild(Leg3);
		Leg3.cubeList.add(new ModelBox(Leg3, 15, 37, 0.5611F, -3.0516F, -0.0383F, 2, 10, 3, 0.0F, false));

		Leg4 = new RendererModel(this);
		Leg4.setRotationPoint(-6.0F, 2.5F, 4.0F);
		Root.addChild(Leg4);
		Leg4.cubeList.add(new ModelBox(Leg4, 0, 0, -1.0F, -6.0F, -1.0F, 4, 9, 4, 0.0F, false));
	}

	@Override
	public void render(AmalgamationEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Root.render(f5);
	}

	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}