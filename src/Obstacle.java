package uk.ac.reading.dj014296.JavaFXapp;

import javafx.scene.image.Image;

public class Obstacle extends AnEntity {
	private int oX, oY;
	private Image obstacleImg;

	/**
	 * Constructor for obstacle object
	 * 
	 * @param oX
	 * @param oY
	 * @param aWorld
	 */

	public Obstacle(int oX, int oY, AWorld aWorld) {

		super();

		this.oX = oX;
		this.oY = oY;
		setImage(new Image(getClass().getResourceAsStream("obstacl.png")));

	}

	public Obstacle() {

	}

	/**
	 * Sends the details of obstacle to the interface
	 * 
	 * @param r
	 *            sends the details
	 */
	public void showObstacle(AnInterface r) {
		r.showObstacle(obstacleImg, oX, oY);
	}

	/**
	 * returns x position of obstacle
	 */
	public int getX() {
		return oX;
	}

	/**
	 * @return y position of obstacle
	 */
	public int getY() {
		return oY;
	}

	/**
	 * sets the X coord
	 * 
	 * @param oX
	 */
	public void setX(int oX) {
		this.oX = oX;

	}

	/**
	 * sets the Y coord
	 * 
	 * @param oY
	 */
	public void setY(int oY) {
		this.oY = oY;
	}
}
