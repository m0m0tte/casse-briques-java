package casseBriques;


public interface Collidable extends Positionable {
	
	public boolean collide(Collidable target);	
	
	public boolean bounce (Ball ball);
}
