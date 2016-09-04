package shotChartPackage;

import java.awt.Color;

import org.json.simple.JSONObject;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.core.*;
import processing.core.PFont;

public class ShotChart extends PApplet {

	JSONArray playerShots;
	Shot[] shotsAttempts;
	PFont font;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("shotChartPackage.ShotChart");

	}

	public void settings(){
		
		size(600,600);
		
    }

    public void setup(){
    	background(230, 191, 131);
    	font = loadFont("ArialMT-148.vlw");
    	
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
//        	ellipse(finalLocX, finalLocY, shot.radius, shot.radius);
	    	
	    	ellipse(nextLocX, nextLocY, shot.radius, shot.radius);
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
}
