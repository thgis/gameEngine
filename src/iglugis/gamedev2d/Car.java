package iglugis.gamedev2d;

import android.util.FloatMath;
import iglugis.math.Vector2;

public class Car extends DyanimcGameObject {
	float weight = 200.0f;
	float wheelAngle;
	float maxWheelAngle = 5.0f * Vector2.TO_RADIANS;
	float maxDeltaAngle = 5.0f * Vector2.TO_RADIANS;
	final float airResistence  = -0.5f * 0.30f * 2.2f * 1.29f;
	final float roadResistence = -30.0f * 0.4257f;
	private float engineForce;
	private float resultantForce;
	private float acc;
	private float speed;

	public Car(float x, float y, float width, float height) {
		super(x, y, width, height);
		wheelAngle = 0;
		setSpeed(0);
	}
	@Override
	public void update(float deltaTime){
		
		angle += wheelAngle;
		
		if(angle > 2*Math.PI)
			angle = 0;
		if(angle < -2*Math.PI)
			angle = 0;
		
		resultantForce = engineForce * 10 + roadResistence + airResistence * getSpeed() * getSpeed();
		
		acc = resultantForce/weight;

		this.setSpeed(this.getSpeed() + acc*deltaTime);
		
		this.position.x += FloatMath.cos(angle)*getSpeed();
		this.position.y += FloatMath.sin(angle)*getSpeed();
	}
	public void updateWheel(float angleChange) {
		angleChange = angleChange * Vector2.TO_RADIANS;

			wheelAngle = angleChange;
			
		if(wheelAngle > maxWheelAngle)
			wheelAngle = maxWheelAngle;
		if(wheelAngle < -maxWheelAngle)
			wheelAngle = -maxWheelAngle;
	}
	public void updateSpeed(float engineForce) {
		this.engineForce = engineForce;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
