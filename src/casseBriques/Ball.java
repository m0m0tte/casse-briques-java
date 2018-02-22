package casseBriques;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Drawable, Collidable{
	
	protected Point2D position;
	protected Point2D velocity;
	protected double size = 10;
	
	Ball(Point2D position, Point2D velocity){
		this.position = position;
		this.velocity = velocity;				
	}
	
	public Point2D getVelocity(){
		return velocity;
	}
	
	public void setVelocity(double x, double y) {
		velocity = new Point2D(x,y);
	}
	
	public Point2D getPosition(){
		return position;
	}
	
	public void setPosition(double x, double y){
		position = new Point2D(x,y);
	}
	
	public void accelerate(double factorX, double factorY){
		velocity = new Point2D(velocity.getX()*factorX,velocity.getY()*factorY);
	}
	
	public boolean outOfBounds(double screenWidth, double screenHeight){
		return position.getY() > screenHeight;
	}
	
	@Override
	public void draw(GraphicsContext ctx, Positionable area){
		double x = position.getX();
		double y = position.getY();
		
		ctx.setFill(Color.web("#50514F"));
		ctx.fillOval(x, y, size, size);	
		
		Rectangle2D bounds = area.getBounds();
		
		if(x >= bounds.getMaxX() || x < 0){
			velocity = new Point2D(velocity.getX() * -1 ,velocity.getY());
		}
		
		if (/*y >bounds.getMaxY() || */ y < 0){
			velocity = new Point2D(velocity.getX(), velocity.getY() * -1);
		}
		
		position = position.add(velocity);
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D(position.getX(),position.getY(),size,size);
		
	}
	
	public boolean collide(Collidable target) {
		return getBounds().intersects(target.getBounds());		
	}
	
	@Override
	public boolean bounce(Ball ball) {
		return true;
	}


}
