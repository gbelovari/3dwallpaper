package com.mydomain.wallpaper.mywallpaper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.math.Vector3;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

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
		
		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.tron_obj);
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

		Sphere globe = new Sphere(2, 24, 24);
		globe.addLight(mLight);
		addChild(globe);

		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
