package shotChartPackage;

import java.awt.Color;

import org.json.simple.JSONObject;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.core.*;

public class ShotChart extends PApplet {

	JSONArray playerShots;
	Shot[] shotsAttempts;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("shotChartPackage.ShotChart");

	}

	public void settings(){
		
		size(600,600);
		
    }

    public void setup(){
    	background(230, 191, 131);
    	
    	// Load JSON file
    	playerShots = loadJSONArray("westbrookShots_15_16.json");
    	int dataSize = playerShots.size();
    	
    	shotsAttempts = new Shot[dataSize];
    	
    	for (int i=0; i<dataSize; i++) {
    		processing.data.JSONObject shot = playerShots.getJSONObject(i); 
    		shotsAttempts[i] = new Shot(new PVector(shot.getInt("x"), shot.getInt("y")), 3, i, this, shot.getInt("shot_made_flag"));
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
        	//ellipse(finalLocX, finalLocY, shot.radius, shot.radius);
	    	
	    	ellipse(nextLocX, nextLocY, shot.radius, shot.radius);
	    	shot.updateCurrentLocation(nextLocX, nextLocY);	
    	    
    	    popMatrix();
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
}
