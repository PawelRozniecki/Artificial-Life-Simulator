package uk.ac.reading.dj014296.JavaFXapp;

import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class Predator extends AnEntity {
	protected int pX, pY;
	protected Image predatorImg;

	/**
	 * Constructor for the wolf object
	 * 
	 * @param pX
	 * @param pY
	 * @param energy
	 * @param aWorld
	 */

	public Predator(int pX, int pY, int energy, AWorld aWorld) {
		super(pX, pY, energy, aWorld);
		setImage(new Image(getClass().getResourceAsStream("predator.png")));
		this.pX = pX;
		this.pY = pY;
		this.energy = energy;

	}

	public Predator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * sends the details of wolf to the interface class
	 * 
	 * @param r
	 */
	public void showPredator(AnInterface r) {
		r.showPredator(predatorImg, pX, pY);
	}

	/*
	 * This method moves predator randomly on the screen and checks if it can
	 * move in that direction
	 */

	public void movePredator() {

		Random random = new Random();
		AnEntity ent = aWorld.getChicken();

		time++;
		if (time % 60 == 0) {
			randX = random.nextInt(3) - 1;
			randY = random.nextInt(3) - 1;
		}

		if (randY > 0)
			if (getY() != 0) {
				setY(getY() - getSpeed());
			} else if (randY < 0)
				if (getY() != aWorld.getYsize() - 60) {
					setY(getY() + getSpeed());
				}
		if (randX < 0)
			if (getX() != 0) {
				setX(getX() - getSpeed());
			} else if (randX > 0)
				if (getX() != aWorld.getXsize() - 60) {
					setX(getX() + getSpeed());

				}
		try {

			/*
			 * conditional statements for chasing an entity
			 */

			if (getX() < ent.getX())
				pX++;

			else if (getX() > ent.getX())
				pX--;
			if (getY() < ent.getY())
				pY++;

			else if (getY() > ent.getY())
				pY--;
			aWorld.eatPrey();

		} catch (Exception e) {

		}

	}

	/**
	 * sets the Y coordinate
	 * 
	 * @param pY
	 */
	public void setY(int pY) {
		this.pY = pY;
	}

	/**
	 * sets the X coordinate
	 * 
	 * @param pX
	 *            coord
	 */
	public void setX(int pX) {
		this.pX = pX;

	}

	public int getX() {
		return pX;
	}

	public int getY() {

		return pY;

	}
}
