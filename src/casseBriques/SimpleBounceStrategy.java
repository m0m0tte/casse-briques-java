package casseBriques;

/* ----------- Gere le comportement d'une brique de base ----------- */

public class SimpleBounceStrategy implements BounceStrategy {

	@Override
	public boolean bounce(Brick brick, Ball ball) {
		ball.accelerate(1, -1);
		return true;
	}
	
	public void addParameters(Object[] params) {		
	}

}
