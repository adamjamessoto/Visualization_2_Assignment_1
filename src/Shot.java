

//import processing.core.PApplet;
//import processing.core.PVector;
import processing.core.*;

public class Shot {
	
	public PVector finalLoc;
	public PVector currentLoc;
	public float radius;
	public int id;
	public PApplet p;
	public int shotMade;
	public String actionType;
    public String shotType;
    public double shotDistance;
    public String opponent;

	public Shot () {
	}
	
	public Shot (PVector finalLoc, float radius, int id, PApplet p, int shotMade) {
		this.finalLoc = alterCoordinates(finalLoc);
		this.radius = radius;
		this.id = id;
		this.p = p;
		this.shotMade = shotMade;
		this.currentLoc = new PVector(300, 500);
		
		System.out.println("Shot ID: "  + this.id);
	}
	
	public Shot (PVector finalLoc, float radius, int id, PApplet p, int shotMade, String opponent) {
		this.finalLoc = alterCoordinates(finalLoc);
		this.radius = radius;
		this.id = id;
		this.p = p;
		this.shotMade = shotMade;
		this.currentLoc = new PVector(300, 500);
	    this.opponent = opponent;
	    
	    System.out.println("Shot ID: "  + this.id);
	}
	
	public float getCurrentLocationX () {
		return this.currentLoc.x;
	}
	
	public float getCurrentLocationY () {
		return this.currentLoc.y;
	}
	
	public void updateCurrentLocation (float x, float y) {
		this.currentLoc.set(x, y);
	}
	
	public float getFinalLocationX () {
		return this.finalLoc.x;
	}
	
	public float getFinalLocationY () {
		return this.finalLoc.y;
	}
	
	public boolean getShotMade () {
		return ((this.shotMade == 1) ? true : false);
		
	}
	
	public boolean isHit() {
		
	    if (p.dist(p.mouseX, p.mouseY, finalLoc.x, finalLoc.y) < radius/2)
	      return true; 
	      
	    return false;
	}
	
	
	private PVector alterCoordinates (PVector loc) {
		float updateX;
		float updateY;
		
		// change x coordinate (600, 600)
		if (loc.x <= 0) {
			updateX = 300 + loc.x;
		}
		else {
			updateX = 300 + loc.x;
		}
		
		// change y coordinate (600, 600)
		if (loc.y <= 0) {
			updateY = 500 + loc.y;
		}
		else {
			updateY = 500 - loc.y;
		}
		
		return new PVector(updateX, updateY);
	}

}
