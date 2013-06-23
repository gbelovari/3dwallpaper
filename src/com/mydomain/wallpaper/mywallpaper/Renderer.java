package com.mydomain.wallpaper.mywallpaper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Vector3;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

public class Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D[] mSpheres;
	private BaseObject3D mRootsphere;
	
	public Renderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	public void initScene() {

//		camera.setFogNear(1);
//		camera.setFogFar(15);
//		camera.setFogColor(0x999999);
//		
//		setFogEnabled(true);
//		getCurrentScene().setBackgroundColor(0x999999);
		
//		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.tron_obj);
//		try {
//			objParser.parse();
//			mTron = objParser.getParsedObject();
//			mTron.addLight(mLight);
//			mTron.setScale(0.1f);
//			mTron.setRotY(90);
//			mTron.setZ(-3);
//			addChild(mTron);
//			
//		} catch(ParsingException e) {
//			e.printStackTrace();
//		}

		mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);

		mSpheres = new BaseObject3D[400];
		
		mRootsphere = new Sphere(0.3f, 6,6);
		SimpleMaterial rootsphereMaterial = new SimpleMaterial();
		rootsphereMaterial.setUseSingleColor(true);
//		try {
//			rootsphereMaterial.addTexture(new Texture(R.drawable.earthtruecolor_nasa_big));
//		} catch (TextureException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		mRootsphere.setMaterial(rootsphereMaterial);
		mRootsphere.addLight(mLight);
		mRootsphere.setColor(Color.GRAY);
		// -- similar objects with the same material, optimize
		mRootsphere.setRenderChildrenAsBatch(true);
//		addChild(mRootsphere);
		mSpheres[0] = mRootsphere;
		int k = 0;
		for(int i=1; i < 10 + 1; ++i) {
			for (int j = 1; j < 10 + 1; j++) {
				for (int j2 = 1; j2 < 4+1; j2++) {
					BaseObject3D sphere = mRootsphere.clone(true);
					sphere.setY(-1.5f + i*0.3f);
					sphere.setX(-1.5f + j*0.3f);
					sphere.setZ(-10-j2*0.3f);
					sphere.setScale(0.3f);
					mSpheres[k] = sphere;
					addChild(mSpheres[k]);	
					k = k + 1;
				}
			}	
		}	
		Log.e("k", Integer.toString(k));
		getCurrentCamera().setZ(0);

		
		for (int i = 0; i < k; i++) {
			TranslateAnimation3D testAnim = new TranslateAnimation3D(
					new Vector3(-10*Math.random()+5,
					-10*Math.random()+5,
					-10.5f*Math.random()+1
			));
			testAnim.setDuration(6000);
			testAnim.setDelay(2000);
			testAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			testAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			testAnim.setTransformable3D(mSpheres[i]);
			
			registerAnimation(testAnim);
			testAnim.play();	
		}		

		getCurrentCamera().setLookAt(mSpheres[46].getPosition());

		
		RotateAroundAnimation3D testCamRot = new RotateAroundAnimation3D(
				new Vector3(0, 0, -10),
				Axis.Y,
				10
		);
		testCamRot.setDuration(10000);
		testCamRot.setRepeatMode(RepeatMode.INFINITE);
		testCamRot.setTransformable3D(getCurrentCamera());
		registerAnimation(testCamRot);
		testCamRot.play();

		
//		TranslateAnimation3D camAnim = new TranslateAnimation3D(new Vector3(0, 1, -20));
//		camAnim.setDuration(8000);
//		camAnim.setInterpolator(new AccelerateDecelerateInterpolator());
//		camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
//		camAnim.setTransformable3D(getCurrentCamera());
//		registerAnimation(camAnim);
//		camAnim.play();
//		
//		RotateAnimation3D camAnim2 = new RotateAnimation3D(Axis.Y, 180);
//		camAnim2.setDuration(8000);
//		camAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
//		camAnim2.setRepeatMode(RepeatMode.REVERSE_INFINITE);
//		camAnim2.setTransformable3D(getCurrentCamera());
//		registerAnimation(camAnim2);
//		camAnim2.play();
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
//		for (int i = 1; i < mSpheres.length; i++) {
//			mSpheres[i].setRotY((float)(mSpheres[i].getRotY() + Math.random()*2));
//		}
	}
}
