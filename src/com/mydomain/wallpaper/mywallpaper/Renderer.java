package com.mydomain.wallpaper.mywallpaper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

public class Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mRoad, mTron, mRoad2;
	
	public Renderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	public void initScene() {
		mLight = new DirectionalLight(0, -1, -1);
		mLight.setPower(.5f);
		
		Camera camera = getCurrentCamera();
		camera.setPosition(0, 1, 4);
		camera.setFogNear(1);
		camera.setFogFar(15);
		camera.setFogColor(0x999999);
		
		setFogEnabled(true);
		getCurrentScene().setBackgroundColor(0x999999);
		
		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.road);
		try {
			objParser.parse();
			mRoad = objParser.getParsedObject();
			mRoad.addLight(mLight);
			mRoad.setZ(-2);
			mRoad.setRotY(180);
			addChild(mRoad);


			
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		
		objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.tron_obj);
		try {
			objParser.parse();
			mTron = objParser.getParsedObject();
			mTron.addLight(mLight);
			mTron.setScale(0.1f);
			mTron.setRotY(90);
			mTron.setZ(-3);
			addChild(mTron);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		try {
			
			mRoad2 = mRoad.clone();
			mRoad2.addLight(mLight);
			mRoad2.setZ(-3);
			mRoad2.setRotY(180);
			addChild(mRoad2);
			
			DiffuseMaterial roadMaterial = new DiffuseMaterial();
			roadMaterial.addTexture(new Texture(R.drawable.road));
			mRoad.getChildByName("Road").setMaterial(roadMaterial);
			mRoad2.getChildByName("Road").setMaterial(roadMaterial);
			
			DiffuseMaterial signMaterial = new DiffuseMaterial();
			signMaterial.addTexture(new Texture(R.drawable.sign));
			mRoad.getChildByName("Sign").setMaterial(signMaterial);
			mRoad2.getChildByName("Sign").setMaterial(signMaterial);
		
			DiffuseMaterial warningMaterial = new DiffuseMaterial();
			warningMaterial.addTexture(new Texture(R.drawable.warning));
			mRoad.getChildByName("Warning").setMaterial(warningMaterial);
			mRoad2.getChildByName("Warning").setMaterial(warningMaterial);
			
		} catch(TextureException tme) {
			tme.printStackTrace();
		}
		

		
		TranslateAnimation3D camAnim = new TranslateAnimation3D(new Vector3(0, 1, -50));
		camAnim.setDuration(8000);
//		camAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		camAnim.setTransformable3D(getCurrentCamera());
		registerAnimation(camAnim);
		camAnim.play();
		
		TranslateAnimation3D camAnim2 = new TranslateAnimation3D(new Vector3(0, 0, -55));
		camAnim2.setDuration(8000);
//		camAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
		camAnim2.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		camAnim2.setTransformable3D(mTron);
		registerAnimation(camAnim2);
		camAnim2.play();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mLight.setZ(getCurrentCamera().getZ());
	}
}
