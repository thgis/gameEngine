package iglugis.thomasGame;

import android.util.FloatMath;
import iglugis.gamedev2d.DyanimcGameObject;
import iglugis.math.Vector2;

public class Car extends DyanimcGameObject {
	float wheelAngle;
	float maxWheelAngle = 2 * Vector2.TO_RADIANS;
	float maxDeltaAngle = 0.25f * Vector2.TO_RADIANS;
	private float directionalAcc;
	private float speed;

	public Car(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		wheelAngle = 0;
		speed = 0;
	}
	@Override
	public void update(float deltaTime){
		
		angle += wheelAngle;
		
		if(angle > 2*Math.PI)
			angle = 0;
		if(angle < -2*Math.PI)
			angle = 0;
		//accel.x = FloatMath.cos(angle)*directionalAcc;
		//accel.y = FloatMath.sin(angle)*directionalAcc;
		
		//this.velocity.x = this.accel.x;// * deltaTime;
		//this.velocity.y = this.accel.y;// * deltaTime;
		this.speed += directionalAcc*deltaTime*0.1;
		//if (this.speed<0)this.speed=0;
		
		this.position.x += FloatMath.cos(angle)*speed;//this.velocity.x * deltaTime;
		this.position.y += FloatMath.sin(angle)*speed;//this.velocity.y * deltaTime;
	}
	public void updateWheel(float angleChange) {
		angleChange = angleChange * Vector2.TO_RADIANS;
		
		if(angleChange > maxDeltaAngle )
			angleChange = maxDeltaAngle;
		if(angleChange < -maxDeltaAngle)
			angleChange = -maxDeltaAngle;
		
			wheelAngle += angleChange;
			
		if(wheelAngle > maxWheelAngle)
			wheelAngle = maxWheelAngle;
		if(wheelAngle < -maxWheelAngle)
			wheelAngle = -maxWheelAngle;
	}
	public void updateSpeed(float acc) {
		this.directionalAcc = acc;
	}
}
