package view;

import javafx.scene.Group;
import javafx.scene.chart.*;

public class Screen extends Chart{
	
	public Screen() {
		Axis<Number> AxeX = new NumberAxis(); 
		Axis<Number> AxeY = new NumberAxis(); 
		LineChart<Number, Number> linechart = new LineChart<Number, Number>(AxeX, AxeY);
		Group groupe = new Group();
		groupe.getChildren().add(linechart);
		
		
		
	}

	@Override
	protected void layoutChartChildren(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		
	}
}
