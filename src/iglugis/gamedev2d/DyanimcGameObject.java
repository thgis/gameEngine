package iglugis.gamedev2d;

import iglugis.math.Vector2;

public class DyanimcGameObject extends GameObject {
	public final Vector2 velocity;
	public final Vector2 accel;
	
	public DyanimcGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}
	protected void update(float deltaTime)
	{
		this.position.x+=deltaTime;
		this.position.y+=deltaTime;
	}
}
