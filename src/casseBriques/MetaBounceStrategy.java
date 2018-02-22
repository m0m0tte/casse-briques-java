package casseBriques;

import java.util.ArrayList;

/* ----------- Permet d'utiliser plusieurs strategies en meme temps ----------- */

public class MetaBounceStrategy implements BounceStrategy{

	ArrayList<BounceStrategy> strategies;
	
	@Override
	public boolean bounce(Brick brick, Ball ball) {
		for(BounceStrategy s : strategies){
			s.bounce(brick, ball);
		}
		return false;
	}

	@Override
	public void addParameters(Object[] params) {
		strategies = new ArrayList<>();
		for(int i=0 ; i<params.length ; i++){
			try{
				Class strategy = (Class) params[i];
				System.out.println(strategy);
				BounceStrategy bounceStrategy = (BounceStrategy) strategy.newInstance();
				strategies.add(bounceStrategy);
			}catch(Exception ex){
				System.out.println(ex.getStackTrace());
			}
			
		}
	}

}
