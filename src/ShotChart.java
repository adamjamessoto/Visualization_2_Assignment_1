

import java.awt.Color;

import org.json.simple.JSONObject;
import processing.opengl.*;
import processing.data.*;
import processing.core.*;

public class ShotChart extends PApplet {

	JSONArray playerShots;
	Shot[] shotsAttempts;
	PFont font;
	NbaCourt court;
	Color lineColor;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("shotChartPackage.ShotChart");

	}

	public void settings(){
		size(600, 600);
//		size(600, 600, P3D);
		
    }

    public void setup(){
    	background(230, 191, 131);
    	font = loadFont("ArialMT-148.vlw");
    	
    	court = new NbaCourt();
    	lineColor = new Color (255, 255, 255);
    	
    	// Load JSON file
    	playerShots = loadJSONArray("westbrookShots_15_16.json");
    	int dataSize = playerShots.size();
    	
    	shotsAttempts = new Shot[dataSize];
    	
    	for (int i=0; i<dataSize; i++) {
    		processing.data.JSONObject shot = playerShots.getJSONObject(i);
    		shotsAttempts[i] = new Shot(new PVector(shot.getInt("x"), shot.getInt("y")), 5, i, this, shot.getInt("shot_made_flag"), shot.getString("opponent"));
    	}
    }

    public void draw(){
    	background(230, 191, 131);
    	
    	float currentLocX;
    	float currentLocY;
    	float finalLocX;
    	float finalLocY;
    	float nextLocX;
    	float nextLocY;
    	
    	buildLowerCourt(lineColor);
    	buildThreePoint(lineColor);
    	buildKey(lineColor);
    	
    	
    	// Drawing the individual shots
    	for (int i = 0; i < shotsAttempts.length; i++) {
    		Shot shot = shotsAttempts[i];
    	    pushMatrix();
    	    noStroke();
    	    
    	    // Set color of shot
    	    Color shotColor = selectColor(shot.getShotMade());
    	    fill(shotColor.getRGB());
    	    
    	    // Set position variables
    	    currentLocX = shot.currentLoc.x;
        	currentLocY = shot.currentLoc.y;
        	finalLocX = shot.finalLoc.x;
        	finalLocY = shot.finalLoc.y;
        	
        	// Check current position of shot
       		nextLocX = updateLocX(currentLocX, finalLocX);
       		nextLocY = updateLocY(currentLocY, finalLocY);
        	
        	// Draw ellipse in next position
	    	ellipse(nextLocX, nextLocY, shot.radius, shot.radius);
//       		translate(nextLocX, nextLocY, 0);
//       		sphere(shot.radius);
	    	shot.updateCurrentLocation(nextLocX, nextLocY);	
    	    
    	    popMatrix();
    	    
    	    shotSelected(shot);
    	}
    }
	
    private float updateLocX (float currentLocX, float finalLocX) {
    	
    	if (currentLocX < finalLocX)
    		return ++currentLocX;
    	
    	else if (currentLocX > finalLocX)
    		return --currentLocX;
    	
    	else
    		return currentLocX;
	}
    
	private float updateLocY (float currentLocY, float finalLocY) {
	    	
		if (currentLocY > finalLocY)
			return --currentLocY;
		
		else if (currentLocY < finalLocY)
			return ++currentLocY;
		
		else
			return currentLocY;
	}
	
	private Color selectColor (boolean shotMade) {
		
		if (shotMade)
	    	return new Color(0,125,195);
	    	
	    else
	    	return new Color(240,81,51);	
	}
	
	private void shotSelected (Shot shot) {
		
		// mouse detection
	    if (shot.isHit()) {
			fill(85);
			textFont(font, 23);
			String s = "Shot ID:  " + shot.id;
			text(s, 50, 100);
			String s1 = "Made:  " + (shot.getShotMade() ? "Yes" :"No");
			text(s1, 50, 125);
			String s2 = "Opponent: " + shot.opponent;
			text(s2, 50, 150);
	    }
	}
	
	public void buildLowerCourt (Color lineColor) {
		noFill();
    	stroke(lineColor.getRGB());
    	rect (45, 150, 510 , 353, 7);
    	noFill();
    	stroke(lineColor.getRGB());
    	ellipse (300, 150, 107, 107);
	}
	
	public void buildThreePoint (Color lineColor) {
		stroke(lineColor.getRGB());
		arc(300, 503, 455, 480,PI, TWO_PI);
	}
	
	public void buildKey (Color lineColor) {
		fill(48, 139, 206);
    	stroke(lineColor.getRGB());
    	rect (246, 340, 107, 163);
    	noFill();
    	stroke(lineColor.getRGB());
    	ellipse(300, 340, 107, 107);
	}
	
	public void buildOverLay () {
		fill(230, 191, 131);
    	noStroke();
    	rect(100, 504, 400,400);
	}
}
