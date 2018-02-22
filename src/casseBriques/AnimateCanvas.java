/*		A FAIRE: 
 * systeme de score
 * stratregie raquette réduite
 * changer background
 * "gagné" quand il ne reste plus de briques
 * */

package casseBriques;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AnimateCanvas extends Application{

	GraphicsContext ctx;
	Bounds screenBounds = new Bounds(0,0,600,600);
	Racket racket;
	boolean running = false;
	
	//ArrayList<Drawable> drawables;
	static ArrayList<Brick> bricks;
	static ArrayList<Ball> balls;

	@Override
	public void start(Stage primaryStage){
		
		double sWidth = screenBounds.getWidth();
		double sHeight = screenBounds.getHeight();
		
		Canvas canvas = new Canvas(sWidth, sHeight);
		ctx = canvas.getGraphicsContext2D();
				
		StackPane root = new StackPane();
		root.getChildren().add(canvas);
		
		Scene scene = new Scene(root,sWidth, sHeight);
		
		root.setStyle("-fx-background-color: #dff8eb;");
		
		primaryStage.setTitle("Casse Briques");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false); 
		primaryStage.show();
		
		//*****************************************
		
		balls = new ArrayList<Ball>();
		Ball ball = new Ball(new Point2D(sWidth/2, sHeight-50), new Point2D(0, 0));
		balls.add(ball);

		
		racket = new Racket();
		racket.setX(sWidth/2);
		racket.setWidth(100);
		
		bricks = new ArrayList<Brick>();
		createBrickLine(14,0,Color.web("#50514F"),MultipleBounceStrategy.class);
		createBrickLine(13,1,Color.web("#F25F5C"),SmallBrickStrategy.class);
		createBrickLine(12,2,Color.web("#00ACB9"),AddBallStrategy.class);
		createBrickLine(11,3,Color.web("#FFE066"),SimpleBounceStrategy.class);			
		createBrickLine(10,4,Color.web("#50514F"),AddBallStrategy.class);		
		createBrickLine(9,5,Color.web("#F25F5C"),SimpleBounceStrategy.class);
		createBrickLine(8,6,Color.web("#00ACB9"),MultipleBounceStrategy.class);
		createBrickLine(7,7,Color.web("#FFE066"),AddBallStrategy.class);		
		createBrickLine(6,8,Color.web("#50514F"),SmallBrickStrategy.class);		
		createBrickLine(5,9,Color.web("#F25F5C"),SimpleBounceStrategy.class);
		
		//Class[] subs = {AddBallStrategy.class, SmallBrickStrategy.class};
		//createBrickLine(5,9,Color.web("#F25F5C"),MetaBounceStrategy.class,subs);
		
		
		/*drawables = new ArrayList<Drawable>();
		drawables.add(ball);
		drawables.add(racket);*/
		
					
		//*****************************************
		
		
		Tick t = new Tick();
		t.start();
		
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				running = true;	
				for (Ball b : balls){
					double x = Math.random() + 1 - 2;
					double y = Math.random() * 3 + 5;
					b.setVelocity(x * 5,-y);     
				}
											
			}			
		});
		
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				if(running){
				racket.setX(event.getX());
				}
			}			
		});
	}
			
	class Tick extends AnimationTimer{

		@Override
		public void handle(long now) {
			ctx.clearRect(0, 0, screenBounds.getWidth(),screenBounds.getHeight());
			
			Iterator <Ball> ballIterator = balls.iterator();
			
			while (ballIterator.hasNext()){
				Ball b = ballIterator.next();
			
				// test collision ball contre racket :
				if(racket.collide(b)){
					if(racket.bounce(b)){
						
					}
				}
				if (b.outOfBounds(screenBounds.getWidth(), screenBounds.getHeight()) == true){
					ballIterator.remove();
					if (balls.isEmpty()){
						reset();
						break;
					}					
					continue; 
				}
				
				//test collision ball contre briques :
				Iterator<Brick> iterator = bricks.iterator();
				while(iterator.hasNext()){
					Collidable c = iterator.next();
					if(c.collide(b)){
						if(c.bounce(b)){
							iterator.remove();
						}

						break;
					}
				}
			}			
			
			for (Ball b : balls){
				b.draw(ctx, screenBounds);
			}	
			
			for (Brick d : bricks){
				d.draw(ctx, screenBounds);
			}
			
			racket.draw(ctx, screenBounds);
		}		
	}
	
	public void createBrickLine(int totalBricks,int lineIndex, Paint color, Class strategy){
		createBrickLine(totalBricks,lineIndex, color, strategy, null);
	}
	
	public void createBrickLine(int totalBricks,int lineIndex, Paint color, Class strategy, Object[] params){
		double brickWidth = (screenBounds.getWidth()-((totalBricks-1)*2))/totalBricks;
		double y = lineIndex*30+(lineIndex*2);
		for(int i=0 ; i<totalBricks ; i++){
			try{
				BounceStrategy bounceStrategy = (BounceStrategy) strategy.newInstance();
				if (params != null){
					bounceStrategy.addParameters(params);
				}
				Brick b = new Brick(i*brickWidth + (i*2), y, brickWidth, 30, color, bounceStrategy);
				//Brick b = new Brick(i*brickWidth + (i*2), y, brickWidth, 30, color, bounceStrategy);
				bricks.add(b);
			}catch(Exception ex){
				System.out.println("Erreur creation brick");
			}
						
			/*if (type==1){
				Brick b = new Brick(i*brickWidth + (i*2), y, brickWidth, 30, color, new SimpleBounceStrategy());
				bricks.add(b);
			}else if(type==2){
				Brick b = new Brick(i*brickWidth + (i*2), y, brickWidth, 30, color, new MultipleBounceStrategy());
				bricks.add(b);
			}else if(type==3){
				Brick b = new Brick(i*brickWidth + (i*2), y, brickWidth, 30, color, new AddBallStrategy());
				bricks.add(b);
			}*/
		}
	}
	
	public void reset(){ // remise a zero des positions (qd la balle sort de l'écran)
		running = false;
		Ball ball = new Ball(new Point2D(screenBounds.getWidth()/2, screenBounds.getHeight()-50), new Point2D(0, 0));
		balls.add(ball);
		racket.setX(screenBounds.getWidth()/2);
	}
	

	/*represente les limites d'affichage :*/
	class Bounds extends Rectangle2D implements Positionable {		
		
		public Bounds(double minX, double minY, double width, double height) {
			super(minX, minY, width, height);
			}

		@Override
		public Rectangle2D getBounds() {
			return this;
		}

		@Override
		public Point2D getPosition() {
			return Point2D.ZERO;
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
