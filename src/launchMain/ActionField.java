package Tanks.launchMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Tanks.interfaces.Direction;
import Tanks.tanks.AbstractTank;
import Tanks.tanks.Bullet;
import Tanks.tanks.T34;
import Tanks.tanks.Tiger;

public class ActionField  extends JPanel {
	
	//boolean COLORDED_MODE = false;
	int speed = 10;
	private BattleField bf;
	private AbstractTank defender;
	private Tiger agressor;
	private Bullet bullet;
	private Direction direction;

    void runTheGame() throws Exception { 
    	//tank.moveRandome();
//    	defender.clean();
//    	defender.destroy();
    	defender.fire();
    	defender.fire();
    	defender.fire();
    	restartTigr();
    	defender.fire();
    	defender.fire();
       }
   	
	private boolean processInterception() throws Exception {
		String str = getQuadrant(bullet.getBulletX(), bullet.getBulletY());
		int x = Integer.valueOf(str.substring(0, str.indexOf("_")));
		int y = Integer.valueOf(str.substring(str.indexOf("_") + 1));
		if (x>=0 && y>=0 && x<9 && y<9 && bf.scanQuadrant(x,y) != " ") {
			bf.updateQuadrant(x,y," ");
			return true;
		}
		//check aggressor
		if (checkInterception(getQuadrant(agressor.getX(), agressor.getY()), str)){
            bullet.destroy();
            agressor.destroy();
            repaint();
            return true;
        }
		
		//check defender
//		if (checkInterception(getQuadrant(defender.getX(), defender.getY()), str)){
//	//	            bullet.destroy();
//		            defender.destroy();
//		            restartTigr();
//		            return true;
//		}
		return false;
	}
	
	private boolean checkInterception(String object, String quadrant){
		int oy = Integer.parseInt(object.split("_")[1]);
		int ox = Integer.parseInt(object.split("_")[0]);
		
		int qx = Integer.parseInt(quadrant.split("_")[0]);
		int qy = Integer.parseInt(quadrant.split("_")[1]);
		
		if (oy>=0 && oy<9 && ox>=0 && ox<9){
			if(oy==qy && ox==qx){
				return true;
			}
		}
		return false;
	}
	
	String getQuadrant(int x, int y) {
		String str = y / 64 + "_" + x / 64;
		return str;
	}
	public int getQuadrant(int vh) {
		return (vh - 1) * 64;
	}
	public static String getQuadrantXY(int v, int h) {
		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}
	
	public void processTurn(AbstractTank tank) throws Exception{
		repaint();
	}
	
	public void processMove(AbstractTank tank) throws Exception{
		int step = 1;
		int covered = 0;
		direction = tank.getDirection();
		int tankY = tank.getY();
		int tankX = tank.getX();
		int speed = tank.getSpeed();

		// check limits x: 0, 513; y: 0, 513
		if ((direction == Direction.UP && tankY == 0) || (direction == Direction.DOWN && tankY >= 512)
				|| (direction == Direction.LEFT && tankX == 0) || (direction == Direction.RIGHT && tankX >= 512)) {
			System.out.println("[illegal move] direction: " + direction + " tankX: " + tankX + ", tankY: " + tankY);
			return;
		}
		
		tank.turn(direction);

		while (covered < 64) {
			if (direction == Direction.UP) {
				 tank.updateY (-step);
				System.out.println("[move up] direction: " + direction + " tankX: " + tankX + ", tankY: " + tankY);
			} else if (direction == Direction.DOWN) {
				tank.updateY (step);
				System.out.println("[move down] direction: " + direction + " tankX: " + tankX + ", tankY: " + tankY);
			} else if (direction == Direction.LEFT) {
				tank.updateX (-step);
				System.out.println("[move left] direction: " + direction + " tankX: " + tankX + ", tankY: " + tankY);
			} else {
				tank.updateX (step);
				System.out.println("[move right] direction: " + direction + " tankX: " + tankX + ", tankY: " + tankY);
			}
			covered += step;

			repaint();
			Thread.sleep(speed);
		}
	}
	
	
	public void processFire(Bullet bullet) throws Exception {
		this.bullet = bullet;		
		
		if (bullet.getDirection() == Direction.UP && bullet.getBulletY() > 0) {
			while (bullet.getBulletY() > -14) {
				bullet.updateY(-1);
				repaint();
				if (processInterception()) {
					bullet.destroy(); return;
				}
				Thread.sleep(5l);
			}
		} else if (bullet.getDirection() == Direction.DOWN &&  bullet.getBulletY()< 590) {
			while (bullet.getBulletY() < 590) {
				bullet.updateY(1);
				repaint();
				if (processInterception()) {
					bullet.destroy(); return;
				}
				Thread.sleep(5l);
			}
		} else if (bullet.getDirection() == Direction.LEFT && bullet.getBulletX() > 0) {
			while (bullet.getBulletX() > -14) {
				bullet.updateX(-1);
				repaint();
				if (processInterception()) {
					bullet.destroy(); return;
				}
				Thread.sleep(5l);
			}
		} else if (bullet.getDirection() == Direction.RIGHT && bullet.getBulletX() < 590) {
			while (bullet.getBulletX() < 590) {
				bullet.updateX(1);
				repaint();
				if (processInterception()) {
				bullet.destroy(); return;
				}
				Thread.sleep(5l);
			}
		} else {
			return;
		}
	}
	
	void restartTigr(){
		String location = bf.getAgressorLocation();
		agressor = new Tiger(this, bf, Integer.parseInt(location.split("_")[0]), Integer.parseInt(location.split("_")[1]), Direction.UP);
	}
	
	public ActionField() throws Exception {
		
		bf = new BattleField();
        defender = new T34(this, bf);
        restartTigr();  
		
		JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(bf.getBF_WIDTH(), bf.getBF_HEIGHT() + 22));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		bf.draw(g);
		defender.draw(g);
		agressor.draw(g);
		bullet.draw(g);

	}
}

