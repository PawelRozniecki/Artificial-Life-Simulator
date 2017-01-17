package uk.ac.reading.dj014296.JavaFXapp;

import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author PawelRozniecki
 * 
 *         AnInterface class will handle all of the other classes in order to
 *         display the entities in the world. It will be a main class for
 *         handling user input
 *
 */

public class AnInterface extends Application {

	private Stage stagePrimary;
	private Timeline timeline, foodTimeline;
	private Properties prop = new Properties();
	private File lastConfiguration;
	private InputStream inStream;
	private int SCREENSIZE = 512;
	private boolean isRunning = false;
	private VBox rtPane;
	private File fileDirectory = new File("./config");
	private GraphicsContext gc;
	private AnimationTimer timer;
	private AnEntity e = new AnEntity();
	private AWorld aWorld = new AWorld(512, 512);
	private FileChooser fChooser = new FileChooser();
	private Random rand = new Random();

	/*
	 * Sets the menu bar and adds all relevant menu items
	 */
	public MenuBar setMenu() {

		MenuBar menuBar = new MenuBar();

		Menu mFile = new Menu("File");
		Menu mView = new Menu("View");
		Menu mConfig = new Menu("Configure");
		Menu mRun = new Menu("Simulation");
		Menu mHelp = new Menu("Help");

		MenuItem mNew = new MenuItem("New");
		MenuItem mExit = new MenuItem("Exit");
		MenuItem mDisplayConfig = new MenuItem("Display configuration");
		MenuItem mEditConfig = new MenuItem("Edit Configuration");
		MenuItem mDisplayEntityInfo = new MenuItem("Info about life forms");
		MenuItem mDisplayMapInfo = new MenuItem("Info about world");
		MenuItem mSet = new MenuItem("Add entity");
		MenuItem mRemove = new MenuItem("Remove Entity");
		MenuItem mStart = new MenuItem("Start");
		MenuItem mStop = new MenuItem("Stop");
		MenuItem mAppInfo = new MenuItem("About the app");
		MenuItem mAuthor = new MenuItem("About the author");
		MenuItem mOpen = new MenuItem("Open");
		MenuItem mSave = new MenuItem("Save");
		MenuItem mIncSpeed = new MenuItem("Increase Speed");
		MenuItem mDecSpeed = new MenuItem("Decrease Speed");
		MenuItem mFood = new MenuItem("Add food");
		MenuItem mAddPredator = new MenuItem("add predator");
		MenuItem mSaveAs = new MenuItem("Save As");

		// adds menuItems to Menu category

		mRun.getItems().addAll(mStart, mStop);
		mView.getItems().addAll(mDisplayConfig, mEditConfig, mDisplayEntityInfo, mDisplayMapInfo);
		mConfig.getItems().addAll(mSet, mFood, mAddPredator, mRemove);
		mFile.getItems().addAll(mNew, mSave, mOpen, mExit);
		mHelp.getItems().addAll(mAppInfo, mAuthor);
		menuBar.getMenus().addAll(mFile, mView, mConfig, mRun, mHelp);

		/*
		 * These methods add functionality to the MenuBar items
		 */

		mNew.setOnAction(actionEvent -> {

			aWorld.restartWorld();

			Dialog<String> inputDialog = new Dialog<>();
			ButtonType btnFinish = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
			ButtonType btnCancel = new ButtonType("Cance", ButtonBar.ButtonData.CANCEL_CLOSE);

			inputDialog.getDialogPane().getButtonTypes().addAll(btnFinish, btnCancel);

			inputDialog.setTitle("New settings");
			inputDialog.setHeaderText("Configure your world");

			BorderPane bPane = new BorderPane();
			bPane.setPadding(new Insets(10, 20, 10, 15));

			Slider entitiesSlider = new Slider(0, 50, 0);
			Label entitiesLabel = new Label();
			entitiesSlider.setShowTickLabels(true);
			entitiesSlider.setSnapToTicks(true);
			entitiesSlider.setMajorTickUnit(10);

			Label label1 = new Label("Set number of entities");
			Label label2 = new Label("Set number of food");
			Label label3 = new Label("Set number of obstacles");

			Slider foodSlider = new Slider(0, 50, 0);
			Label foodLabel = new Label();
			foodSlider.setShowTickLabels(true);
			foodSlider.setSnapToTicks(true);
			foodSlider.setMajorTickUnit(10);

			entitiesSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
				entitiesLabel.setText(newValue.intValue() + " entities");

				if (newValue.intValue() <= 50) {
					aWorld.addEnts();

				}
			});

			foodSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
				foodLabel.setText(newValue.intValue() + " food");
				if (newValue.intValue() <= 50) {
					aWorld.addFood();
				}

			});

			Slider obstacleSlider = new Slider(0, 10, 0);
			Label obstacleLabel = new Label();
			obstacleSlider.setShowTickLabels(true);
			obstacleSlider.setSnapToTicks(true);
			obstacleSlider.setMajorTickUnit(1);

			obstacleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
				obstacleLabel.setText(newValue.intValue() + " obstacles");
				if (newValue.intValue() <= 10) {
					aWorld.addObstacle();
				}
			});

			VBox vb = new VBox();

			bPane.setCenter(vb);

			vb.getChildren().addAll(label1, entitiesSlider, entitiesLabel, label2, foodSlider, foodLabel, label3,
					obstacleSlider, obstacleLabel);
			inputDialog.getDialogPane().setContent(bPane);

			inputDialog.setResultConverter(dialogButton -> {
				if (dialogButton == btnFinish) {
					return (int) entitiesSlider.getValue() + " " + (int) foodSlider.getValue();
				}
				return null;
			});

			inputDialog.showAndWait();

		});

		mAddPredator.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				aWorld.addPredator();

			}
		});

		mSaveAs.setOnAction(actionEvent -> {

		});

		mFood.setOnAction(actionEvent -> {

			aWorld.addFood();

		});

		mEditConfig.setOnAction(actionEvent -> {

		});
		mOpen.setOnAction(actionEvent -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose a file");
			File file = fileChooser.showOpenDialog(stagePrimary);
			InputStream input;

			try {
				input = new FileInputStream(aWorld.toString());

			} catch (IOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Ups... Something went wrong! Please try again!");
				alert.showAndWait();
			}
		});

		mSave.setOnAction(actionEvent -> {

		});

		mStart.setOnAction(actionEvent -> {
			timer.start();
			isRunning = true;
		});

		mRemove.setOnAction(actionEvent -> {
			aWorld.removeEntity();
		});

		mAppInfo.setOnAction(actionEvent -> {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setTitle("About the app");
			a.setHeaderText("Artificial Life Simulator");
			a.setContentText(
					"This app was designed to simulate a 2- dimensional world where entities live, move around, and search for food in order to survive."
							+ "Wolf tries to hunt down all the chickens while chickens search for worms avoiding obstacles. Energy of chicken is decremented and when it reaches 0 it dies."
							+ " Alternatively it can die by eating poison"
							+ "User can interact with simulation by using the toolbar and menu provided");
			a.showAndWait();

		});

		mDisplayMapInfo.setOnAction(actionEvent -> {

			Alert stats = new Alert(AlertType.INFORMATION);

			stats.setTitle("Map information");
			stats.setHeaderText("Statistics");
			stats.setContentText(aWorld.worldStats());
			stats.showAndWait();
		});

		mAuthor.setOnAction(actionEvent -> {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About the author");
			alert.setHeaderText("Thank you for using this app");
			alert.setContentText(
					"This app was designed by Pawel Rozniecki. 2nd year CS student at the University of Reading");
			alert.showAndWait();

		});
		mStop.setOnAction(actionEvent -> {
			timer.stop();
			isRunning = false;
		});

		mSet.setOnAction(actionEvent -> {

			aWorld.addEnts();

		});

		mExit.setOnAction(actionEvent -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm exit");
			alert.setHeaderText("Are you sure you want to quit?");
			alert.setContentText("choose OK to quit, Cancel to resume");

			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					timer.stop();
					System.exit(0);
				} else {

				}
			});

		});

		return menuBar;
	}

	/*
	 * Draws the map and all the objects on the canvas
	 */
	public void drawWorld() {

		Image background = new Image(getClass().getResourceAsStream("background.png"));

		gc.drawImage(background, 0, 0, aWorld.getXsize(), aWorld.getYsize());

		aWorld.showEntities(this);
		aWorld.showAllFood(this);
		aWorld.showAllPredator(this);
		aWorld.showAllObstacle(this);
		aWorld.showAllPoison(this);
	}

	/**
	 * Method for drawing chicken image on canvas
	 * 
	 * @param chickenImg
	 * @param x
	 * @param y
	 * @param opacity
	 */

	public void show(Image chickenImg, int x, int y, double opacity) {

		chickenImg = new Image(getClass().getResourceAsStream("chicken.png"));
		gc.drawImage(chickenImg, x, y, e.imgSize, e.imgSize);

	}

	/**
	 * Method for drawing food on canvas
	 * 
	 * @param foodImg
	 * @param x
	 * @param y
	 */
	public void showFood(Image foodImg, int x, int y) {
		foodImg = new Image(getClass().getResourceAsStream("worm.png"));
		gc.drawImage(foodImg, x, y, 15, 15);
	}

	/**
	 * method for drawing predator image on canvas
	 * 
	 * @param predatorImg
	 * @param x
	 * @param y
	 */
	public void showPredator(Image predatorImg, int x, int y) {
		predatorImg = new Image(getClass().getResourceAsStream("predator.png"));
		gc.drawImage(predatorImg, x, y, 40, 40);
	}

	/**
	 * method for drawing obstacle image on canvas
	 * 
	 * @param obstacleImg
	 * @param x
	 * @param y
	 */
	public void showObstacle(Image obstacleImg, int x, int y) {

		obstacleImg = new Image(getClass().getResourceAsStream("obstacl.png"));
		gc.drawImage(obstacleImg, x, y, 35, 35);

	}

	/**
	 * method for drawing poison image on canvas
	 * 
	 * @param poisonImg
	 * @param x
	 * @param y
	 */
	public void showPoison(Image poisonImg, int x, int y) {
		poisonImg = new Image(getClass().getResourceAsStream("poison.png"));
		gc.drawImage(poisonImg, x, y, 15, 15);
	}

	public void start(final Stage primaryStage) throws Exception {

		stagePrimary = primaryStage;

		primaryStage.setTitle("Artificial Life Simulator");
		primaryStage.setResizable(false);

		Pane pane = new Pane();
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 20, 10, 15));
		bp.setRight(pane);
		bp.setTop(setMenu());
		// bp.setBottom(slider);

		Group root = new Group();
		Canvas canvas = new Canvas(SCREENSIZE, SCREENSIZE);

		root.getChildren().add(canvas);
		// sets the canvas at the centre of the screen.
		bp.setCenter(root);

		gc = canvas.getGraphicsContext2D();

		// initialization of labels
		Label label = new Label();
		Label label2 = new Label();
		Label label3 = new Label();
		Label posLabel = new Label();
		Label energyLabel = new Label();
		Label countdown = new Label();
		Label obstacleLabel = new Label();

		/*
		 * timeline which updates labels
		 */

		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(17), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

				aWorld.getNumEntity();

				label.setText("Number of entities: " + aWorld.getNumEntity() + " out of " + aWorld.getMaxEntity());
				label2.setText("Number of food " + aWorld.getNumFood() + " ouf of " + aWorld.getMaxFood());
				posLabel.setText("x: " + aWorld.getXCoord() + " y: " + aWorld.getYCoord());
				energyLabel.setText("energy: " + aWorld.getEnergyData());
				obstacleLabel.setText(
						"Number of obstacles: " + aWorld.getNumObstacle() + " out of " + aWorld.getMaxObstacle());

			}
		}));

		/*
		 * timeline which adds poison food at random intervals minimum is 8
		 * second maximum is 30 second interval
		 */
		foodTimeline = new Timeline();
		foodTimeline.setCycleCount(Animation.INDEFINITE);

		foodTimeline.getKeyFrames()
				.add(new KeyFrame(Duration.seconds(8 + rand.nextInt(30 - 8) + 1), new EventHandler<ActionEvent>() {

					public void handle(ActionEvent t) {

						aWorld.addPoison();

					}
				}));

		/*
		 * timeline that takes away 2 energy points every second
		 */
		Timeline energyTimeline = new Timeline();
		energyTimeline.setCycleCount(Animation.INDEFINITE);
		energyTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				aWorld.decrementEnergy();
			}
		}));

		drawWorld();

		/*
		 * timer that handles the animation if its running then moves all
		 * entities on screen and plays the timeline
		 */
		timer = new AnimationTimer() {

			public void handle(long currentNanoTime) {

				if (isRunning) {
					aWorld.moveChaser();
					aWorld.moveEnt();
					drawWorld();
					foodTimeline.play();
					timeline.play();
					energyTimeline.play();
				}

			}

		};

		timer.start();

		/*
		 * adds VBOx to the right pane of the screen
		 */

		rtPane = new VBox(5);
		rtPane.setAlignment(Pos.CENTER);
		rtPane.setPadding(new Insets(10, 10, 10, 10));
		bp.setRight(rtPane);

		// restart button

		Button btnRestart = new Button("Restart");
		Image imgReset = new Image(getClass().getResourceAsStream("reset.png"));
		ImageView resView = new ImageView(imgReset);
		resView.setFitHeight(15);
		resView.setFitWidth(15);
		btnRestart.setGraphic(resView);
		btnRestart.setPrefWidth(100);

		// remove button
		Button btnRemove = new Button("Remove");
		Image dec = new Image(getClass().getResourceAsStream("remove.png"));
		ImageView iv = new ImageView(dec);
		iv.setFitWidth(15);
		iv.setFitHeight(15);
		btnRemove.setGraphic(iv);
		btnRemove.setPrefWidth(100);

		// add entity button
		Image add = new Image(getClass().getResourceAsStream("chicken.png"));
		Button btnAdd = new Button("Add");
		ImageView imageView = new ImageView(add);
		imageView.setFitWidth(15);
		imageView.setFitHeight(15);
		btnAdd.setPrefWidth(100);
		btnAdd.setGraphic(imageView);

		// pause button
		Image pause = new Image(getClass().getResourceAsStream("pause.png"));
		ImageView pauseIv = new ImageView(pause);
		Button btnPause = new Button("Pause");
		pauseIv.setFitHeight(15);
		pauseIv.setFitWidth(15);
		btnPause.setGraphic(pauseIv);
		btnPause.setPrefWidth(100);

		// start button
		Image start = new Image(getClass().getResourceAsStream("start.png"));
		ImageView startIv = new ImageView(start);
		Button btnStart = new Button("Start");
		btnStart.setPrefWidth(100);
		startIv.setFitHeight(15);
		startIv.setFitWidth(15);
		btnStart.setGraphic(startIv);

		// add food button
		Image wormImg = new Image(getClass().getResourceAsStream("worm.png"));
		ImageView ivFlower = new ImageView(wormImg);
		Button btnAddFood = new Button("Add food");
		btnAddFood.setPrefWidth(100);
		ivFlower.setFitHeight(20);
		ivFlower.setFitWidth(20);
		btnAddFood.setGraphic(ivFlower);

		// remove food button
		Image removeFood = new Image(getClass().getResourceAsStream("removeWorm.png"));
		ImageView ivRF = new ImageView(removeFood);
		Button btnRemoveFood = new Button("remove");
		btnRemoveFood.setPrefWidth(100);
		ivRF.setFitWidth(20);
		ivRF.setFitHeight(20);
		btnRemoveFood.setGraphic(ivRF);

		// add obstacle button
		Image obstacleImg = new Image(getClass().getResourceAsStream("obstacl.png"));
		ImageView obstacleIv = new ImageView(obstacleImg);
		Button btnAddObstacle = new Button("Add");
		btnAddObstacle.setPrefWidth(100);
		obstacleIv.setFitWidth(20);
		obstacleIv.setFitHeight(20);
		btnAddObstacle.setGraphic(obstacleIv);

		// add wolf button
		Image wolfImg = new Image(getClass().getResourceAsStream("wolf.png"));
		ImageView ivWolf = new ImageView(wolfImg);
		Button btnPredator = new Button("Spawn");
		btnPredator.setPrefWidth(100);
		ivWolf.setFitHeight(20);
		ivWolf.setFitWidth(20);
		btnPredator.setGraphic(ivWolf);

		Image removeO = new Image(getClass().getResourceAsStream("removeRock.png"));
		ImageView ivRemoveO = new ImageView(removeO);
		Button btnRemoveObstacle = new Button("Remove Rock");
		btnRemoveObstacle.setPrefWidth(100);
		ivRemoveO.setFitHeight(20);
		ivRemoveO.setFitWidth(20);
		btnRemoveObstacle.setGraphic(ivRemoveO);

		// adds all label and buttons to the VBox in the right Pane
		rtPane.getChildren().addAll(label, label2, obstacleLabel, label3, posLabel, energyLabel, btnStart, btnPause,
				btnRestart, btnAdd, btnRemove, btnAddFood, btnRemoveFood, btnAddObstacle, btnRemoveObstacle,
				btnPredator);

		// button press handling

		btnAddObstacle.setOnAction(actionEvent -> {
			aWorld.addObstacle();
		});

		btnRestart.setOnAction(actionEvent -> {
			aWorld.restartWorld();
		});
		btnRemoveFood.setOnAction(actionEvent -> {
			aWorld.removeFood();
		});

		btnStart.setOnAction(actionEvent -> {
			timer.start();
			isRunning = true;
		});

		btnAdd.setOnAction(actionEvent -> {

			aWorld.addEnts();

		});
		btnRemoveObstacle.setOnAction(actionEvent -> {
			aWorld.removeObstacle();
		});

		btnPause.setOnAction(actionEvent -> {

			timer.stop();
			isRunning = false;

		});
		btnRemove.setOnAction(actionEvent -> {
			aWorld.removeEntity();

		});

		btnAddFood.setOnAction(actionEvent -> {
			aWorld.addFood();

		});

		btnPredator.setOnAction(actionEvent -> {
			aWorld.addPredator();
		});

		// sets the size of the window
		Scene scene = new Scene(bp, 800, 600);

		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());

		// sets the scene and then shows it
		primaryStage.setScene(scene);
		primaryStage.show();

		// welcome user dialog

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setWidth(50);
		alert.setHeight(10);

		alert.setTitle("Information Dialog");
		alert.setHeaderText("Welcome to the Artificial life simulator!");

		alert.setContentText(null);
		alert.show();

	}

	public static void main(String[] args) {

		Application.launch(args);
	}

}
