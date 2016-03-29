package Tanks.launchMain;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Tanks.barrier.Brick;
import Tanks.barrier.Eagle;
import Tanks.barrier.Rock;
import Tanks.barrier.Water;
import Tanks.interfaces.Drowable;

public class BattleField implements Drowable{
	boolean COLORDED_MODE = false;
	private final int BF_WIDTH = 576;
    private final int BF_HEIGHT = 576;

    public String[][] battleField = {
            {"B", "B", "B", "B", "B", "B", "B", "B", "B"},
            {" ", " ", " ", " ", " ", " ", " ", " ", "B"},
            {"B", "B", "B", " ", "B", " ", "B", "B", "B"},
            {"B", "R", "R", " ", " ", " ", "R", "R", "B"},
            {"B", "B", "B", " ", "W", " ", "B", "B", "B"},
            {"B", "B", " ", "W", "W", "W", " ", "B", "B"},
            {"B", "B", " ", " ", " ", " ", " ", "B", "B"},
            {"B", " ", " ", "B", "B", "B", " ", " ", "B"},
            {"B", " ", " ", "B", "E", "B", " ", " ", "B"}
    };

    public BattleField(){
    }

    public BattleField(String[][] battleField){
        this.battleField = battleField;
    }

    public String[][] getBattleField() {
        return battleField;
    }

    public int getBF_WIDTH() {
        return BF_WIDTH;
    }

    public int getBF_HEIGHT() {
        return BF_HEIGHT;
    }

    public String scanQuadrant(int x, int y) {
        return  battleField[x][y];
    }

    public void updateQuadrant(int x, int y, String field){
        battleField[x][y] = field;
    }

    public int getDimentionX(){
        return battleField.length;
    }

    public int getDimentionY(){
        return battleField[0].length;
    }
    
    public String getAgressorLocation(){
    	String str = null;
    	Random r = new Random();
    	while (true){
    		if (r.nextInt(4) > 0) {
            if (r.nextInt(4)==1) {if (str!= "128_64"){str = "128_64"; return str;}}
            if (r.nextInt(4)==2) {if (str!= "256_64"){str = "256_64"; return str;}}
            if (r.nextInt(4)==3) {if (str!= "448_64"){str = "448_64"; return str;}}
            }
         }
     }
    
    public void draw(Graphics g){
    	int i = 0;
		Color cc;
		for (int v = 0; v < 9; v++) {
			for (int h = 0; h < 9; h++) {
				if (COLORDED_MODE) {
					if (i % 2 == 0) {
						cc = new Color(252, 241, 177);
					} else {
						cc = new Color(233, 243, 255);
					}
				} else {
					cc = new Color(180, 180, 180);
				}
				i++;
				g.setColor(cc);
				g.fillRect(h * 64, v * 64, 64, 64);
			}
		}
		
		for (int j = 0; j < getDimentionX(); j++) {
			for (int k = 0; k < getDimentionY(); k++) {
				
					String coordinates = ActionField.getQuadrantXY(j + 1, k + 1);
					int separator = coordinates.indexOf("_");
					int y = Integer.parseInt(coordinates.substring(0, separator));
					int x = Integer.parseInt(coordinates.substring(separator + 1));
					
					if (scanQuadrant(j, k).equals("B")) {
						 Brick brick =new Brick(x, y);
	                        brick.draw(g);	
				}
					if (scanQuadrant(j, k).equals("W")) {
						 Water water =new Water(x, y);
	                        water.draw(g);	
				}
					if (scanQuadrant(j, k).equals("R")) {
						 Rock rock =new Rock(x, y);
	                        rock.draw(g);	
				}
					if (scanQuadrant(j, k).equals("E")) {
						 Eagle eagle =new Eagle(x, y);
	                        eagle.draw(g);	
				}
			}
		}
    }
}
