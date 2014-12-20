package viewmodel;

import processing.core.PApplet;

public class Handle {
	  int minValue, maxValue;
	
	  int maxW;
	  int x, y;
	  int boxx, boxy;
	  int stretch;
	  int size;
	  boolean over;
	  boolean press;
	  boolean locked = false;
	  String name;
	  
	  Handle (int ix, int iy, int start, int size, int maxSize) {
	    x = ix;
	    y = iy;
	    stretch = start;
	    this.size = size;
	    boxx = x + stretch - size/2;
	    boxy = y - size/2;
	    maxW = maxSize;
	  }
	  
	  public void setMinMaxValue (int min, int max) {
		  minValue = min;
		  maxValue = max;
	  }
	  public void setName (String name) {
		  this.name = name;
	  }
	  
	  public int getValue () {
		  return (int)PApplet.map(stretch, 0, maxW - size - 1, minValue, maxValue);
	  }
	  
	  void update(SeasonCanvas canvas) {
	    boxx = x + stretch;
	    boxy = y - size/2;
	   	    
	    overEvent(canvas);
	    pressEvent(canvas);

	    
	    if (press) {
	      stretch = lock(canvas.mouseX - x  -size/2, 0, maxW - size - 1);
	    }
	  }
	  
	  void overEvent(SeasonCanvas canvas) {
	    if (overRect(canvas, boxx, boxy, size, size)) {
	      over = true;
	    } else {
	      over = false;
	    }
	  }
	  
	  void pressEvent(SeasonCanvas canvas) {
	    if (over && canvas.mousePressed || locked) {
	      press = true;
	      locked = true;
	    } else {
	      press = false;
	    }
	  }
	  
	  void releaseEvent() {
	    locked = false;
	  }
	  
	  void display(SeasonCanvas canvas) {

	    
		canvas.pushStyle();
		canvas.fill(80);
		canvas.textSize(15);
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.text(name, x - canvas.textWidth(String.valueOf(minValue)) - 4, y - 3);
		canvas.text(minValue, x - 2, y - 3);
		canvas.textAlign(PApplet.LEFT, PApplet.CENTER);
		canvas.text(maxValue, x + maxW + 4, y - 3);
	   
	    
		canvas.fill(255);
	    canvas.rect(x, y - size / 2 + 2, maxW, size - 4);
	    
	    canvas.stroke(0);
	    if (over || press) {
	    	 canvas.fill(50);
	    }
	    canvas.rect(boxx, boxy, size, size);
	    
	    canvas.fill(80);
		canvas.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		canvas.text(getValue(), boxx + size / 2, boxy - 2);
	    
	    canvas.popStyle();
	  }
	  
	  boolean overRect(SeasonCanvas canvas, int x, int y, int width, int height) {
		  if (canvas.mouseX >= x && canvas.mouseX <= x+width && 
				  canvas.mouseY >= y && canvas.mouseY <= y+height) {
		    return true;
		  } else {
		    return false;
		  }
		}
	  int lock(int val, int minv, int maxv) { 
		  return  Math.min(Math.max(val, minv), maxv); 
		} 
}
