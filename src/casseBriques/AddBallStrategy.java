package casseBriques;

import javafx.scene.media.AudioClip;

/* ----------- Cree une nouvelle balle lors d'une collision balle/brique ----------- */

public class AddBallStrategy implements BounceStrategy{

	protected AudioClip hitBrick = new AudioClip(getClass().getResource("/sons/blupblupblup.wav").toExternalForm());
	@Override
	public boolean bounce(Brick brick, Ball ball) {
		ball.accelerate(1, -1);
		Ball newBall = new Ball(ball.getPosition(),ball.getVelocity());
		newBall.accelerate(-1, 1);
		AnimateCanvas.balls.add(newBall);
		hitBrick.play();
		return true;
	}

	public void addParameters(Object[] params) {		
	}

}
