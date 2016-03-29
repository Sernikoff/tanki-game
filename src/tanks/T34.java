package Tanks.tanks;

import java.awt.Color;
import java.awt.Graphics;

import Tanks.interfaces.Direction;
import Tanks.launchMain.ActionField;
import Tanks.launchMain.BattleField;

public class T34 extends AbstractTank {
	
	
	public T34(ActionField af, BattleField bf){
		 this(af, bf, 0, 64, Direction.RIGHT);
	}
	
	public T34(ActionField af, BattleField bf, int x, int y, Direction direction){
		super(af, bf, x, y, direction);
	    tankColor = new Color(0, 255, 0);
	    towerColor = new Color(255, 0, 0);
		speed=20;
    }
	
	   public void destroy() throws Exception{
	        updateX(-100);
	        updateY(-100);
	        af.repaint();
	    }
}
