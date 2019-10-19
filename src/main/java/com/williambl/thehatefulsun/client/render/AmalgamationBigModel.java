package com.williambl.thehatefulsun.client.render;
//Made with Blockbench

import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class AmalgamationBigModel extends AmalgamationModel {
	private final RendererModel Root;
	private final RendererModel Quadruped;
	private final RendererModel HeadAndNeck;
	final RendererModel Head;
	private final RendererModel HeadAndNeck2;
	final RendererModel Head2;
	final RendererModel Leg;
	final RendererModel Leg2;
	final RendererModel Leg3;
	final RendererModel Leg4;
	private final RendererModel Quadruped2;
	private final RendererModel HeadAndNeck3;
	final RendererModel Head3;
	private final RendererModel HeadAndNeck4;
	final RendererModel Head4;
	final RendererModel Leg5;
	final RendererModel Leg6;
	final RendererModel Leg7;
	final RendererModel Leg8;
	private final RendererModel HeadAndNeck5;
	final RendererModel Head5;
	private final RendererModel HeadAndNeck6;
	final RendererModel Head6;

	public AmalgamationBigModel() {
		textureWidth = 256;
		textureHeight = 256;

		Root = new RendererModel(this);
		Root.setRotationPoint(0.0F, 18.5F, 0.0F);
		Root.cubeList.add(new ModelBox(Root, 0, 0, -32.0F, -26.5F, -32.0F, 64, 32, 64, 0.0F, false));

		Quadruped = new RendererModel(this);
		Quadruped.setRotationPoint(-13.0F, -19.0F, -30.0F);
		setRotationAngle(Quadruped, -0.5236F, 0.7854F, 0.0F);
		Root.addChild(Quadruped);
		Quadruped.cubeList.add(new ModelBox(Quadruped, 68, 96, -8.0F, -12.5F, -10.0F, 16, 9, 18, 0.0F, false));

		HeadAndNeck = new RendererModel(this);
		HeadAndNeck.setRotationPoint(6.0F, -9.5F, -10.0F);
		setRotationAngle(HeadAndNeck, -0.2618F, -0.3491F, 0.0F);
		Quadruped.addChild(HeadAndNeck);
		HeadAndNeck.cubeList.add(new ModelBox(HeadAndNeck, 31, 55, -1.0864F, -1.1818F, -5.0534F, 2, 3, 5, 0.0F, false));

		Head = new RendererModel(this);
		Head.setRotationPoint(-0.0864F, 0.8182F, -3.0534F);
		setRotationAngle(Head, 0.3491F, 0.5236F, 0.0F);
		HeadAndNeck.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 18, 50, -3.0F, -5.0F, -4.0F, 5, 6, 4, 0.0F, false));

		HeadAndNeck2 = new RendererModel(this);
		HeadAndNeck2.setRotationPoint(-6.0F, -9.5F, -10.0F);
		setRotationAngle(HeadAndNeck2, -0.2618F, 0.3491F, 0.0F);
		Quadruped.addChild(HeadAndNeck2);
		HeadAndNeck2.cubeList.add(new ModelBox(HeadAndNeck2, 68, 107, 0.0864F, -1.1818F, -4.0534F, 2, 3, 4, 0.0F, false));

		Head2 = new RendererModel(this);
		Head2.setRotationPoint(0.0864F, 0.8182F, -3.0534F);
		setRotationAngle(Head2, 0.3491F, -0.5236F, 0.0F);
		HeadAndNeck2.addChild(Head2);
		Head2.cubeList.add(new ModelBox(Head2, 46, 34, -1.0F, -6.0F, -4.0F, 5, 7, 4, 0.0F, false));

		Leg = new RendererModel(this);
		Leg.setRotationPoint(5.0F, 2.5F, -5.0F);
		Quadruped.addChild(Leg);
		Leg.cubeList.add(new ModelBox(Leg, 50, 96, -1.0F, -6.0F, -2.0F, 3, 9, 3, 0.0F, false));

		Leg2 = new RendererModel(this);
		Leg2.setRotationPoint(-3.0F, 2.5F, -4.0F);
		Quadruped.addChild(Leg2);
		Leg2.cubeList.add(new ModelBox(Leg2, 31, 123, -6.0F, -5.0F, -4.0F, 3, 8, 3, 0.0F, false));
		Leg2.cubeList.add(new ModelBox(Leg2, 50, 108, -6.0F, -8.0F, -4.0F, 3, 3, 3, 0.0F, false));

		Leg3 = new RendererModel(this);
		Leg3.setRotationPoint(5.3126F, -0.234F, 3.6428F);
		setRotationAngle(Leg3, 0.4363F, 0.0F, -0.4363F);
		Quadruped.addChild(Leg3);
		Leg3.cubeList.add(new ModelBox(Leg3, 118, 96, 0.5611F, -3.0516F, -0.0383F, 2, 10, 3, 0.0F, false));

		Leg4 = new RendererModel(this);
		Leg4.setRotationPoint(-6.0F, 2.5F, 4.0F);
		Quadruped.addChild(Leg4);
		Leg4.cubeList.add(new ModelBox(Leg4, 46, 0, -1.0F, -6.0F, -1.0F, 4, 9, 4, 0.0F, false));

		Quadruped2 = new RendererModel(this);
		Quadruped2.setRotationPoint(20.0F, -8.0F, -30.0F);
		setRotationAngle(Quadruped2, 0.4363F, -0.5236F, 0.0F);
		Root.addChild(Quadruped2);
		Quadruped2.cubeList.add(new ModelBox(Quadruped2, 0, 96, -8.0F, -12.5F, -10.0F, 16, 9, 18, 0.0F, false));

		HeadAndNeck3 = new RendererModel(this);
		HeadAndNeck3.setRotationPoint(6.0F, -9.5F, -10.0F);
		setRotationAngle(HeadAndNeck3, -0.2618F, -0.3491F, 0.0F);
		Quadruped2.addChild(HeadAndNeck3);
		HeadAndNeck3.cubeList.add(new ModelBox(HeadAndNeck3, 46, 13, -1.0864F, -1.1818F, -5.0534F, 2, 3, 5, 0.0F, false));

		Head3 = new RendererModel(this);
		Head3.setRotationPoint(-0.0864F, 0.8182F, -3.0534F);
		setRotationAngle(Head3, 0.3491F, 0.5236F, 0.0F);
		HeadAndNeck3.addChild(Head3);
		Head3.cubeList.add(new ModelBox(Head3, 0, 50, -3.0F, -5.0F, -4.0F, 5, 6, 4, 0.0F, false));

		HeadAndNeck4 = new RendererModel(this);
		HeadAndNeck4.setRotationPoint(-6.0F, -9.5F, -10.0F);
		setRotationAngle(HeadAndNeck4, -0.2618F, 0.3491F, 0.0F);
		Quadruped2.addChild(HeadAndNeck4);
		HeadAndNeck4.cubeList.add(new ModelBox(HeadAndNeck4, 45, 57, 0.0864F, -1.1818F, -4.0534F, 2, 3, 4, 0.0F, false));

		Head4 = new RendererModel(this);
		Head4.setRotationPoint(0.0864F, 0.8182F, -3.0534F);
		setRotationAngle(Head4, 0.3491F, -0.5236F, 0.0F);
		HeadAndNeck4.addChild(Head4);
		Head4.cubeList.add(new ModelBox(Head4, 42, 46, -1.0F, -6.0F, -4.0F, 5, 7, 4, 0.0F, false));

		Leg5 = new RendererModel(this);
		Leg5.setRotationPoint(5.0F, 2.5F, -5.0F);
		Quadruped2.addChild(Leg5);
		Leg5.cubeList.add(new ModelBox(Leg5, 0, 96, -1.0F, -6.0F, -2.0F, 3, 9, 3, 0.0F, false));

		Leg6 = new RendererModel(this);
		Leg6.setRotationPoint(-3.0F, 2.5F, -4.0F);
		Quadruped2.addChild(Leg6);
		Leg6.cubeList.add(new ModelBox(Leg6, 72, 96, -6.0F, -5.0F, -4.0F, 3, 8, 3, 0.0F, false));
		Leg6.cubeList.add(new ModelBox(Leg6, 0, 108, -6.0F, -8.0F, -4.0F, 3, 3, 3, 0.0F, false));

		Leg7 = new RendererModel(this);
		Leg7.setRotationPoint(5.3126F, -0.234F, 3.6428F);
		setRotationAngle(Leg7, 0.4363F, 0.0F, -0.4363F);
		Quadruped2.addChild(Leg7);
		Leg7.cubeList.add(new ModelBox(Leg7, 62, 96, 0.5611F, -3.0516F, -0.0383F, 2, 10, 3, 0.0F, false));

		Leg8 = new RendererModel(this);
		Leg8.setRotationPoint(-6.0F, 2.5F, 4.0F);
		Quadruped2.addChild(Leg8);
		Leg8.cubeList.add(new ModelBox(Leg8, 42, 21, -1.0F, -6.0F, -1.0F, 4, 9, 4, 0.0F, false));

		HeadAndNeck5 = new RendererModel(this);
		HeadAndNeck5.setRotationPoint(-21.0F, -17.5F, 33.0F);
		setRotationAngle(HeadAndNeck5, -0.2618F, 2.618F, 0.0F);
		Root.addChild(HeadAndNeck5);
		HeadAndNeck5.cubeList.add(new ModelBox(HeadAndNeck5, 42, 123, -3.6844F, -7.6723F, -8.828F, 10, 12, 11, 0.0F, false));

		Head5 = new RendererModel(this);
		Head5.setRotationPoint(-0.0864F, 3.8182F, -3.0534F);
		setRotationAngle(Head5, 0.3491F, 0.5236F, 0.0F);
		HeadAndNeck5.addChild(Head5);
		Head5.cubeList.add(new ModelBox(Head5, 0, 25, -3.0F, -14.0F, -10.0F, 13, 15, 10, 0.0F, false));

		HeadAndNeck6 = new RendererModel(this);
		HeadAndNeck6.setRotationPoint(31.4402F, -3.5067F, 11.1686F);
		setRotationAngle(HeadAndNeck6, -0.2618F, -1.4835F, 0.0F);
		Root.addChild(HeadAndNeck6);
		HeadAndNeck6.cubeList.add(new ModelBox(HeadAndNeck6, 0, 123, -4.5021F, -6.1984F, -6.2045F, 10, 12, 11, 0.0F, false));

		Head6 = new RendererModel(this);
		Head6.setRotationPoint(0.5312F, 0.6332F, -6.0951F);
		setRotationAngle(Head6, 0.3491F, 0.5236F, 0.0F);
		HeadAndNeck6.addChild(Head6);
		Head6.cubeList.add(new ModelBox(Head6, 0, 0, -7.0755F, -8.1895F, -7.6575F, 13, 15, 10, 0.0F, false));
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