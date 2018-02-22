package casseBriques;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

/* ----------- Une brique doit etre frappee 3 fois avant de disparaitre ----------- */

public class MultipleBounceStrategy implements BounceStrategy {

	protected int hitCount;
	protected AudioClip hitBrick = new AudioClip(getClass().getResource("/sons/son3.wav").toExternalForm());
	
	MultipleBounceStrategy(){
		this.hitCount = 3;
	}
	
	MultipleBounceStrategy(int hitCount){
		this.hitCount = hitCount;
	}
	
	@Override	
	public boolean bounce(Brick brick, Ball ball) {		
		hitCount --;		
		ball.accelerate(1, -1);
		
		if(hitCount == 2){
			brick.setColor(Color.GAINSBORO);
		}
		
		if(hitCount == 1){
			brick.setColor(Color.GHOSTWHITE);
		}		
		
		if(hitCount==0){
		hitBrick.play();// /!\	a tester
		}
		return hitCount == 0;
	}
	
	
	public void addParameters(Object[] params) {
		hitCount = (int) params[0];
	}

}
