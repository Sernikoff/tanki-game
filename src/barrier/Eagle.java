package Tanks.barrier;

import java.awt.Color;

public class Eagle extends AbstractBarrier{
	
	public Eagle(int x, int y){
		this.x = x;
		this.y = y;
		barrierColor = new Color(100 , 0, 100);
	}
}
