package uk.ac.reading.dj014296.JavaFXapp;

import javafx.scene.image.Image;

public class Poison extends AnEntity {
	private int poisonX, poisonY;
	private Image poisonImg;

	/**
	 * Constructor for poison
	 * 
	 * @param poisonX
	 * @param poisonY
	 * @param aWorld
	 */
	public Poison(int poisonX, int poisonY, AWorld aWorld) {
		super();
		this.poisonX = poisonX;
		this.poisonY = poisonY;
		setImage(new Image(getClass().getResourceAsStream("poison.png")));

	}

	public Poison() {

	}

	/**
	 * Sends the details of poison to the interface
	 * 
	 * @param r
	 *            sends the details
	 */
	public void showPoison(AnInterface r) {

		r.showPoison(poisonImg, poisonX, poisonY);
	}

	/**
	 * @return x position of poison
	 */
	public int getX() {
		return poisonX;

	}

	/**
	 * @return y position of poison
	 */
	public int getY() {
		return poisonY;
	}

	/**
	 * sets the X coord of poison
	 * 
	 * @param poison X
	 */
	public void setX(int poisonX) {
		this.poisonX = poisonX;
	}

	/**
	 * sets the y coord
	 * @param poisonY
	 */
	public void setY(int poisonY) {
		this.poisonY = poisonY;
	}

}
