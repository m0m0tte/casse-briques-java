package casseBriques;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;

public class Brick /*extends Rectangle2D*/ implements Drawable, Collidable {
	
	protected Rectangle2D rectBrick ;	
	protected double minX;
	protected double minY;
	protected double width;
	protected double height;
	protected Paint color;
	protected BounceStrategy bounceStrategy;
	protected AudioClip hitBrick = new AudioClip(getClass().getResource("/sons/boom.wav").toExternalForm());
	
	public Brick(double minX, double minY, double width, double height, Paint color, BounceStrategy bounceStrategy){
		rectBrick = new Rectangle2D(minX,minY,width,height);
		this.minX = minX;
		this.minY = minY;
		this.width = width;
		this.height = height;
		this.color = color;
		setBounceStrategy(bounceStrategy);
	}
	
	public void setWidth(double w){
		this.width =w;
		rectBrick = new Rectangle2D(minX,minY,w,height);
	}
	
	public void setColor(Paint color){
		this.color =color;
	}
	
	public Paint getColor(){
		return this.color;
	}
	
	public Rectangle2D getRectBrick(){
		return rectBrick;
	}
	
	/*public Brick(double minX, double minY, double width, double height, Paint color, BounceStrategy bounceStrategy){
		super(minX,minY,width,height);
		this.color = color;
		setBounceStrategy(bounceStrategy);
	}*/
	
	public void setBounceStrategy(BounceStrategy bounceStrategy){
		this.bounceStrategy = bounceStrategy;
	}
	
		
	@Override	
	public void draw(GraphicsContext ctx, Positionable area) {
		ctx.setFill(color);
		ctx.fillRect(getRectBrick().getMinX(),getRectBrick().getMinY(), getRectBrick().getWidth(),getRectBrick().getHeight()/*getMinX(), getMinY(), getWidth(), getHeight()*/);		
	}

	
	
	@Override
	public Rectangle2D getBounds() {
		return rectBrick;
		//return this;
	}

	@Override
	public Point2D getPosition() {
		return new Point2D(getRectBrick().getMinX(), getRectBrick().getMinY());
		//return new Point2D(getMinX(),getMinY());
	}

	@Override
	public boolean collide(Collidable target) {
		return getRectBrick().intersects(target.getBounds());
		//return intersects(target.getBounds());		
	}
	
	@Override
	public boolean bounce(Ball ball) {
		hitBrick.play();
		return bounceStrategy.bounce(this, ball);
	}

	public double getWidth() {		
		return getRectBrick().getWidth();
	}
	
}
