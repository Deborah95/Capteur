package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

class Grid {
	
	int pasHorizontal;
	int pasVertical;
	Color couleur;
	boolean presenceLabel;
	boolean presenceGraduation;
	
	Grid(int dimHorizontal, int dimVertical){
		int i;
		for(i=pasVertical;i<dimVertical;i+=pasVertical) {
			Line line = new Line();
			line.setStartX(0);
			line.setStartY(i);
			line.setEndX(dimHorizontal);
			line.setEndY(i);
			line.setStroke(couleur);
		}
		for(i=pasHorizontal;i<dimHorizontal;i+=pasHorizontal) {
			Line line = new Line();
			line.setStartX(i);
			line.setStartY(0);
			line.setEndX(i);
			line.setEndY(dimVertical);
			line.setStroke(couleur);
		}
	}
	
}
