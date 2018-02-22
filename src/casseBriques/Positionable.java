package casseBriques;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public interface Positionable{
	
	public Rectangle2D getBounds();
	
	public Point2D getPosition();
}
