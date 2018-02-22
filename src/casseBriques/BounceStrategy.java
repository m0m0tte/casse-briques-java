package casseBriques;

public interface BounceStrategy {
	public boolean bounce(Brick brick, Ball ball) ;
	public void addParameters(Object[] params);
	
}
