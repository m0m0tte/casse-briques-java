package casseBriques;


/* ----------- Divise par 2 la taille de la brique lors d'une collision balle/brique ----------- */

public class SmallBrickStrategy implements BounceStrategy{
	protected int hitCount;
	
	SmallBrickStrategy(){
		this.hitCount = 2;
	}
	
	SmallBrickStrategy(int hitCount){
		this.hitCount = hitCount;
	}
	@Override	
	public boolean bounce(Brick brick, Ball ball) {	
		hitCount --;
		brick.setWidth(brick.getWidth()/2);
		ball.accelerate(1, -1);
		return hitCount == 0;
	}
	
	
	@Override
	public void addParameters(Object[] params) {		
	}

}
