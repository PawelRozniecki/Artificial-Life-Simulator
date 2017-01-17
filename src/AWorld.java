package uk.ac.reading.dj014296.JavaFXapp;

import java.util.Scanner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author PawelRozniecki
 *
 *         World class defines the world in which the entities will exist.
 *
 */

public class AWorld {

	private int xSize = 512;
	private int ySize = 512;
	private int maxEntity = 30;
	private int maxPredator = 1;
	private int maxObstacle = 8;
	private int maxPoison = 2;
	private int maxFood = 25;
	private int genX, genY, energy;
	private Random random = new Random();
	private AnEntity e = new AnEntity();
	protected List<AnEntity> entities;
	protected List<Food> foodObj;
	protected List<Predator> chaser;
	protected List<Obstacle> obstacle;
	protected List<Poison> poison;

	/**
	 * Constructor for the world
	 * 
	 * @param xs
	 *            X size of world
	 * @param ys
	 *            Y size of world
	 */
	public AWorld(int xs, int ys) {

		xSize = xs;
		ySize = ys;

		entities = new ArrayList<>();
		random = new Random();
		foodObj = new ArrayList<>();
		chaser = new ArrayList<>();
		obstacle = new ArrayList<>();
		poison = new ArrayList<>();

	}

	public AWorld() {

	}

	public void addEnts() {

		try {

			if (entities.size() < maxEntity) {

				do {

					genX = random.nextInt(xSize - e.imgSize);
					genY = random.nextInt(ySize - e.imgSize);
					energy = e.getEnergy();
					System.out.println("New positions: " + "x: " + genX + " " + "y: " + genY);

				} while (getEntity(genX, genY) != null);
				AnEntity newEnt = new AnEntity(genX, genY, energy, this);

				entities.add(newEnt);

			}
		} catch (ArrayIndexOutOfBoundsException e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Exception occured");
			alert.setContentText("Please press the restart button");
			alert.showAndWait();

		}
	}

	/**
	 * Checks if there are entities at given position
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @return e return entity if it matches the coordinates
	 * @return null if entities are not found at x,y coordinates
	 */

	public AnEntity getEntity(int x, int y) {

		for (AnEntity e : entities) {
			if (e == null)
				continue;
			if (e.getX() == x && e.getY() == y)
				return e;
		}
		return null;
	}

	/*
	 * Methods for adding objects to the world. These methods generate random
	 * coordinates for each object
	 */

	public void addFood() {

		try {

			if (foodObj.size() < maxEntity) {

				do {

					genX = random.nextInt(xSize - 60);
					genY = random.nextInt(ySize - 60);
					energy = (int) (Math.random() * 200) + 50;
					System.out.println("New positions: " + genX + " " + genY);

				} while (getEntity(genX, genY) != null);
				Food newFood = new Food(genX, genY, energy, this);

				foodObj.add(newFood);

			}
		} catch (ArrayIndexOutOfBoundsException e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Exception occured");
			alert.setContentText("Please press the restart button");
			alert.showAndWait();

		}
	}

	/**
	 * adds poison at random position
	 */
	public void addPoison() {

		int randX, randY;
		try {
			if (poison.size() < maxPoison) {
				do {
					randX = random.nextInt(xSize - 60);
					randY = random.nextInt(ySize - 60);

					System.out.println("position for poison" + " " + randX + " " + randY);

				} while (getEntity(randX, randY) != null);

				Poison newPoison = new Poison(randX, randY, this);
				poison.add(newPoison);
			}
		} catch (Exception e) {

		}
	}

	/*
	 * adds wolf at random position
	 */
	public void addPredator() {
		int randX, randY, energy;
		if (chaser.size() < maxPredator) {
			do {
				randX = random.nextInt(xSize - 50);
				randY = random.nextInt(ySize - 50);
				energy = random.nextInt(200) * 10;
				System.out.println("position for predator" + " " + randX + " " + randY);

			} while (getEntity(randX, randY) != null);
			Predator newChaser = new Predator(randX, randY, energy, this);
			chaser.add(newChaser);
		}
	}

	/*
	 * adds obstacle at random position
	 */
	public void addObstacle() {

		int randX, randY;
		if (obstacle.size() < maxObstacle) {

			do {

				randX = random.nextInt(xSize - 50);
				randY = random.nextInt(ySize - 50);
				System.out.println("Position for obstacle: " + " x: " + randX + " y" + randY);
			} while (getEntity(randX, randY) != null);

			Obstacle newObstacle = new Obstacle(randX, randY, this);
			obstacle.add(newObstacle);
		}

	}

	/*
	 * Checks if there is a chicken at wolf position if there is remove that
	 * chicken
	 */

	public void eatPrey() {

		for (AnEntity en : entities) {

			for (Predator ch : chaser) {
				if (en.getX() == ch.getX() && en.getY() == ch.getY()) {
					entities.remove(entities.indexOf(en));
				}
			}
		}

	}


	/*
	 * removes entity that is at the last index of ArrayList
	 */

	public void removeObstacle() {
		try {
			if (obstacle.size() > 0) {
				obstacle.remove(obstacle.size() - 1);
			}
		} catch (Exception e) {

		}
	}

	public void removeEntity() {

		try {

			if (entities.size() > 0) {

				entities.remove(entities.size() - 1);
			}

			if (entities.size() == 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setHeaderText("There are no entities to remove");
				alert.setContentText("Add entities first");
				alert.show();
			}

		} catch (ArrayIndexOutOfBoundsException a) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error occured");
			alert.setHeaderText("There are no entites to remove. Please add some entites first");
			alert.showAndWait();

		}

	}

	/*
	 * This method resets the world by clearing all ArrayLists
	 */
	public void restartWorld() {
		entities.clear();
		foodObj.clear();
		chaser.clear();
		obstacle.clear();
		poison.clear();

	}

	/*
	 * removes food at the last index of Arraylist outputs error if user tries
	 * to remove non existen food
	 */

	public void removeFood() {

		try {
			if (foodObj.size() > 0) {
				foodObj.remove(foodObj.size() - 1);

			}
			if (foodObj.size() < 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Please don't panic!");
				alert.setContentText(
						"You see this message because you tried to remove non existent food Object.Please add some food first");
				alert.show();
			}

		} catch (ArrayIndexOutOfBoundsException a) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Please don't panic!");
			alert.setContentText(
					"You see this message because you tried to remove non existent food Object.Please add some food first");
			alert.show();
		}

	}

	/**
	 * Method for displaying objects in the world
	 * 
	 * @param AnInterface
	 **/

	public void showEntities(AnInterface r) {

		for (AnEntity entity : entities)
			entity.showEntity(r);
	}

	/**
	 * displays food in a world by sending details to interface
	 * 
	 * @param r
	 *            sends details to interface
	 */
	public void showAllFood(AnInterface r) {
		for (Food food : foodObj)
			food.showF(r);

	}

	/**
	 * displays wolf in a world by sending details to interface
	 * 
	 * @param r
	 *            sends details to interface
	 */

	public void showAllPredator(AnInterface r) {

		for (Predator ch : chaser) {
			ch.showPredator(r);
		}
	}

	/**
	 * displays obstacles in a world by sending details to interface
	 * 
	 * @param r
	 *            sends details to interface
	 */
	public void showAllObstacle(AnInterface r) {
		for (Obstacle o : obstacle) {
			o.showObstacle(r);
		}
	}

	/**
	 * displays poison in a world by sending details to interface
	 * 
	 * @param r
	 *            sends details to interface
	 */

	public void showAllPoison(AnInterface r) {
		for (Poison p : poison) {
			p.showPoison(r);
		}
	}

	// Methods which loop through each life form, and move them in the world
	public void moveEnt() {

		try {

			for (AnEntity ent : entities) {

				ent.moveEntity();
				ent.smellWorm();
				ent.collectPoison();
			

			}

		} catch (Exception e) {

		}

	}

	/*
	 * This method moves predator
	 */

	public void moveChaser() {

		for (Predator ch : chaser) {
			ch.movePredator();

		}

	}

	/*
	 * this method decrements energy by 2 of each chicken in ArrayList if energy
	 * is equal to 0 then the chicken dies
	 * 
	 */
	public void decrementEnergy() {
		try {
			for (AnEntity ent : entities) {
				ent.setEnergy(ent.getEnergy() - 2);
				if (ent.getEnergy() == 0) {
					chickenDeath();
				}
			}
		} catch (Exception e) {

		}

	}

	/**
	 * checks if the chicken is the instance of AnEntity class
	 * 
	 * @return entities chicken object at a certain position in ArrayList
	 * @return null if no chicken was found
	 */
	public AnEntity getChicken() {

		for (int i = 0; i < entities.size();) {
			if (entities.get(i) instanceof AnEntity)
				;
			return entities.get(i);
		}

		return null;
	}

	public void chickenDeath() {

		for (AnEntity ent : entities) {

			entities.remove(entities.indexOf(ent));

		}
	}

	/**
	 * 
	 * @return food object at a certain position in ArrayList
	 * @return null if no food object was found
	 */
	public Food detectFood() {

		for (int i = 0; i < foodObj.size();) {

			if (foodObj.get(i) instanceof Food)
				;
			return foodObj.get(i);

		}
		return null;
	}

	public Obstacle getRock() {

		for (int i = 0; i < obstacle.size(); i++) {
			if (obstacle.get(i) instanceof Obstacle)
				;
			return obstacle.get(i);
		}
		return null;
	}

	/**
	 * Method for displaying the information about the entities in the world
	 * 
	 * @return xSize,ySize,Entities.size, foodObj,size, obstacle.size
	 */

	public String worldStats() {
		return "Width: " + " " + xSize + " Height: " + " " + ySize + "No of Entities: " + entities.size()
				+ " No of Food: " + foodObj.size() + " no of obstacles: " + obstacle.size();

	}

	// getters and setters

	/**
	 * gets xSize
	 * 
	 * @return xSize horizontal size of the world
	 */
	public int getXsize() {
		return xSize;
	}

	/**
	 * 
	 * @return ySize vertical size of the world
	 */
	public int getYsize() {
		return ySize;
	}

	/**
	 * 
	 * @return list of entities
	 */
	public List<AnEntity> getEntities() {
		return entities;
	}

	/**
	 * 
	 * @return entities.size() returns size of entities ArrayList
	 */
	public int getNumEntity() {
		return entities.size();
	}

	/**
	 * used for testing
	 * 
	 * @return xCoordinates for chicken in the interface label
	 */
	public int getXCoord() {
		for (AnEntity ent : entities) {

			return ent.getX();

		}
		return 0;
	}

	/**
	 * 
	 * @return energy data for entities
	 */
	public int getEnergyData() {
		for (AnEntity ent : entities) {
			return ent.getEnergy();
		}
		return 0;
	}

	/**
	 * 
	 * used for testing
	 * 
	 * @return y coordinates for the chicken in the interface label
	 */

	public int getYCoord() {

		for (AnEntity ent : entities) {
			return ent.getY();

		}
		return 0;
	}

	/**
	 * 
	 * @return foodObj.size() returns size of food array list
	 */

	public int getNumFood() {
		return foodObj.size();
	}

	/**
	 * 
	 * @return maximum number of food allowed on map
	 */
	public int getMaxFood() {

		return maxFood;
	}

	/**
	 * 
	 * @return maximum number of entities allowed on map
	 */

	public int getMaxEntity() {
		return maxEntity;
	}

	/**
	 * 
	 * @return number of obstacles
	 */
	public int getNumObstacle() {
		return obstacle.size();
	}

	public int getMaxObstacle() {
		return maxObstacle;
	}
}