package casseBriques;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class Racket implements Drawable, Collidable{

	protected double x = 0;
	protected double y = 0;
	protected double height = 20;
	protected double width = 100;
	protected double halfWidth;
	protected static double MAX_VELOCITY = 10;
	protected AudioClip hit = new AudioClip(getClass().getResource("/sons/collision.wav").toExternalForm());
	
		
	public Rectangle2D getBounds(){
		return new Rectangle2D(x-halfWidth, y, width, height);
	}
	
	public Point2D getPosition(){
		return new Point2D(x+halfWidth, y);
	}
			
	public void setWidth(double w){
		this.width = w;
		halfWidth = width/2;
	}
	
	public double getX(){
		return this.x;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	@Override
	public void draw(GraphicsContext ctx, Positionable area) {
		y = area.getBounds().getMaxY() - height - 20;
		ctx.setFill(Color.web("#00ACB9"));
		ctx.fillRoundRect(x - halfWidth, y, width, height, 20, 20);		
	}

	@Override
	public boolean collide(Collidable target) {
		/*Rectangle2D bounds = getBounds();
		Rectangle2D tbounds = target.getBounds();		
		return tbounds.getMaxY() > bounds.getMinY() &&
				tbounds.getMinY() >= bounds.getMinX() &&
				tbounds.getMinX() <= bounds.getMaxX();*/
		return getBounds().intersects(target.getBounds());		
	}

	@Override
	public boolean bounce(Ball ball) {
		//pour rebondir suivant l'angle d'arrivee sur la raquette :
		
		double ballX = ball.getPosition().getX();
		double dist = ballX - x;
		double factorX = dist / halfWidth;
		double newVelX = factorX * MAX_VELOCITY;
		
		ball.setVelocity(newVelX, ball.getVelocity().getY()*-1);
		hit.play();
	
		
	
		return true;
	}




}
