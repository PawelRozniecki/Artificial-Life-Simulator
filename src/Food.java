package uk.ac.reading.dj014296.JavaFXapp;

import javafx.scene.image.Image;

public class Food extends AnEntity {

	private Image foodImage;

	private int foodX, foodY;

	/**
	 * constructor for food objects
	 * 
	 * @param foodX
	 * @param foodY
	 * @param energy
	 * @param aWorld
	 */
	public Food(int foodX, int foodY, int energy, AWorld aWorld) {
		super(foodX, foodY, energy, aWorld);

		this.foodX = foodX;
		this.foodY = foodY;
		setImage(new Image(getClass().getResourceAsStream("worm.png")));

	}

	public Food() {

	}

	/**
	 * Sends the details of the food to the interface
	 * 
	 * @param r
	 *            sends the details
	 */

	public void showF(AnInterface r) {
		r.showFood(foodImage, foodX, foodY);
	}

	/**
	 * @return x position of food
	 */
	public int getFoodX() {
		return foodX;

	}

	/**
	 * 
	 * @return y position of food
	 */
	public int getFoodY() {
		return foodY;
	}

}