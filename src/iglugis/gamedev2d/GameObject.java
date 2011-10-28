package iglugis.gamedev2d;

import iglugis.math.Rectangle;
import iglugis.math.Vector2;

public class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	public float angle;
	
	public GameObject(float x, float y, float width, float height, float angle){
		this.position = new Vector2(x,y);
		this.bounds = new Rectangle(x-width/2,y-height/2,width,height);
		this.angle = angle;
	}
	public GameObject(float x, float y, float width, float height){
		this(x,y,width,height,0);
	}
	
}
