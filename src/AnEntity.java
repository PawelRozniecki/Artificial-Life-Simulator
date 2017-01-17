package uk.ac.reading.dj014296.JavaFXapp;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.Random;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author PawelRozniecki
 * 
 *         AnEntity class will be used to hold the attributes of the entities.
 *
 */

public class AnEntity {

	private int x, y; // horizontal and vertical positions
	protected int energy = 100;
	protected double opacity = 1;
	protected int imgSize = 30;
	protected boolean solid = false;
	private int dx, dy;
	protected int time = 0;
	protected int randY = 0;
	protected int randX = 0;
	protected int entitySpeed = 1;
	protected Random random = new Random();
	protected int xLimit = 512;
	protected int yLimit = 512;
	protected AWorld aWorld = null;
	protected Food f;
	protected Image image;
	protected int r;
	protected int counter;
	int range = 50;

	/**
	 * Constructor that takes parameters of entity object
	 * 
	 * @param x
	 * @param y
	 * @param energy
	 * @param world
	 */

	public AnEntity(int x, int y, int energy, AWorld world) {
		super();
		this.x = x;
		this.energy = energy;
		this.y = y;
		this.aWorld = world;
		setImage(new Image(getClass().getResourceAsStream("chicken.png")));

	}

	public AnEntity() {

	}

	public Image getImage() {

		return image;
	}

	/**
	 * sets the image
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;

	}

	/**
	 * Sends the details of an entity to the interface class
	 * 
	 * @param r
	 */

	public void showEntity(AnInterface r) {

		r.show(image, x, y, opacity);

	}

	// function that generates random movement for the entities.

	public void moveEntity() {
		Random random = new Random();

		/*
		 * changes direction at random intervals roughly every second time % 60
		 * == 0 is equal to 1 second
		 */

		time++;

		if (time % (random.nextInt(50) + 30) == 0) {

			randX = random.nextInt(3) - 1;

			randY = random.nextInt(3) - 1;

		}
		try {

			if (randY > 0)

				canMoveUp();

			else if (randY < 0)
				canMoveDown();

			if (randX < 0)
				canMoveLeft();
			else if (randX > 0)
				canMoveRight();
			// avoiding rocks

		} catch (

		Exception e) {

		}
	}

	/**
	 * calculates the diagonal distance between the food and the chicken using
	 * Pythagorean distance formula
	 * 
	 * @return true if entity can smell worm
	 * @return false if entity cannot smell the worm
	 */
	public boolean smellWorm() {

		for (Food f : aWorld.foodObj) {

			dx = Math.abs(getX() - f.getX());
			dy = Math.abs(getY() - f.getY());

			double distance = Math.sqrt((dx * dx) + (dy * dy));

			/*
			 * These conditions check if the food is either to the right,left,
			 * above or below of the chicken, and move the chicken to the
			 * position of food
			 */

			if (distance <= range) {

				if (getX() < f.getFoodX())
					x++;
				else if (getX() > f.getFoodX())
					x--;
				if (getY() < f.getFoodY())
					y++;
				else if (getY() > f.getFoodY())
					y--;

				// System.out.println("Distance: " + distance);

				if (getX() == f.getFoodX() && getY() == f.getFoodY()) {
					aWorld.foodObj.remove(aWorld.foodObj.indexOf(f));
					setEnergy(100);
				}
				return true;
			}

		}

		return false;
	}

	public boolean detectObstacle() {

		/*
		 * This method check if the obstacle is either to the right,left, above
		 * or below of the chicken, and move the chicken away from the obstacle
		 * 
		 */

		for (Obstacle o : aWorld.obstacle) {
			Obstacle currentOb = (Obstacle) o;
			dx = Math.abs(getX() - currentOb.getX());
			dy = Math.abs(getY() - currentOb.getX());
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= 10) {
				System.out.println("detecting obstacle");
				if (getX() < currentOb.getX())
					x--;
				if (getX() > currentOb.getX())
					x++;
				if (getY() < currentOb.getY())
					y--;
				if (getY() > currentOb.getY())
					y++;
			}
			return true;

		}

		return false;
	}

	public boolean collectPoison() {
		for (Poison p : aWorld.poison) {

			if (getX() < p.getX())
				x++;
			else if (getX() > p.getX())
				x--;
			if (getY() < p.getY())
				y++;
			else if (getY() > p.getY())
				y--;

			if (getX() == p.getX() && getY() == p.getY()) {
				aWorld.poison.remove(aWorld.poison.indexOf(p));
				aWorld.chickenDeath();
			}
			return true;

		}
		return false;

	}

	/*
	 * Methods that check the position of an entity and decide if it can move in
	 * that direction.
	 */

	public void canMoveRight() {

		if (getX() != aWorld.getXsize() - 50) {
			setX(getX() + getSpeed());

		}
	}

	public void canMoveLeft() {
		if (getX() != 0) {
			setX(getX() - getSpeed());

		}
	}

	public void canMoveUp() {
		if (getY() != 0) {
			setY(getY() - getSpeed());

		}

	}

	public void canMoveDown() {
		if (getY() != aWorld.getYsize() - 50) {
			setY(getY() + getSpeed());

		}
	}

	/*
	 * Getters and Setters for X and Y positions
	 * 
	 */

	public int getX() // returns X position of entity
	{
		return x;
	}

	/**
	 * Sets the X coordinate
	 * 
	 * @param x
	 */

	public void setX(int x) // sets X position
	{
		this.x = x;
	}

	public int getY() // get Y position of Entity
	{
		return y;
	}

	/**
	 * sets the y coordinate
	 * 
	 * @param y
	 */
	public void setY(int y) // sets y position
	{
		this.y = y;
	}

	/**
	 * 
	 * @return entity's energy
	 */
	public int getEnergy() // gets the energy attribute
	{
		return energy;
	}

	/**
	 * 
	 * @return entity's speed
	 */
	public int getSpeed() {

		return entitySpeed;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	/**
	 * sets the speed of entity
	 * 
	 * @param entitySpeed
	 */

	public void setSpeed(int entitySpeed) {
		this.entitySpeed = entitySpeed;
	}

	/**
	 * sets the energy of entity
	 * 
	 * @param energy
	 */

	public void setEnergy(int energy) // sets the energy
	{
		this.energy = energy;
	}

}