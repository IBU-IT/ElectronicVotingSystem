package application;


import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.DecodeWorker;
import com.google.zxing.client.j2se.DecoderConfig;
import com.google.zxing.common.HybridBinarizer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import application.AdminModeController;
import application.ScanVoteController;
import burch.edu.ibu.Barcode;
import burch.edu.ibu.DatabaseManipulation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * This is controller for WebCamPreview FXML.
 * 
 * @author Rakesh Bhatt (rakeshbhatt10)
 */
public class ScanVoteController implements Initializable {

	
	Parent root;
	Scene sc;
	
	@FXML
	Button troubles;

	@FXML
	Button btnStopCamera;

	@FXML
	Button btnDisposeCamera;

	@FXML
	ComboBox<WebCamInfo> cbCameraOptions;

	@FXML
	BorderPane bpWebCamPaneHolder;

	@FXML
	FlowPane fpBottomPane;

	@FXML
	ImageView imgWebCamCapturedImage;

	private class WebCamInfo {

		private String webCamName;
		private int webCamIndex;

		public String getWebCamName() {
			return webCamName;
		}

		public void setWebCamName(String webCamName) {
			this.webCamName = webCamName;
		}

		public int getWebCamIndex() {
			return webCamIndex;
		}

		public void setWebCamIndex(int webCamIndex) {
			this.webCamIndex = webCamIndex;
		}

		@Override
		public String toString() {
			return webCamName;
		}
	}

	private BufferedImage grabbedImage;
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

	private String cameraListPromptText = "Choose Camera";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		fpBottomPane.setDisable(true);
		ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
		int webCamCounter = 0;
		for (Webcam webcam : Webcam.getWebcams()) {
			WebCamInfo webCamInfo = new WebCamInfo();
			webCamInfo.setWebCamIndex(webCamCounter);
			webCamInfo.setWebCamName(webcam.getName());
			options.add(webCamInfo);
			webCamCounter++;
		}
		cbCameraOptions.setItems(options);
		cbCameraOptions.setPromptText(cameraListPromptText);
		cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

			@Override
			public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
				if (arg2 != null) {
					System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
					initializeWebCam(arg2.getWebCamIndex());
				}
			}
		});
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				setImageViewSize();
			}
		});

	}

	protected void setImageViewSize() {

		double height = bpWebCamPaneHolder.getHeight();
		double width = bpWebCamPaneHolder.getWidth();
		imgWebCamCapturedImage.setFitHeight(height);
		imgWebCamCapturedImage.setFitWidth(width);
		imgWebCamCapturedImage.prefHeight(height);
		imgWebCamCapturedImage.prefWidth(width);
		imgWebCamCapturedImage.setPreserveRatio(true);

	}
	
	protected void initializeWebCam(final int webCamIndex) {

		Task<Void> webCamIntilizer = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				if (selWebCam == null) {
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				} else {
					closeCamera();
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				}
				startWebCamStream();
				return null;
			}

		};

		new Thread(webCamIntilizer).start();
		fpBottomPane.setDisable(false);
		
	}

	protected void startWebCamStream() {

		stopCamera = false;
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				while (!stopCamera) {
					try {
						if ((grabbedImage = selWebCam.getImage()) != null) {

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									final Image mainiamge = SwingFXUtils
										.toFXImage(grabbedImage, null);
									imageProperty.set(mainiamge);
								}
							});

							grabbedImage.flush();

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return null;
			}

		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imgWebCamCapturedImage.imageProperty().bind(imageProperty);

	}

	private void closeCamera() {
		if (selWebCam != null) {
			selWebCam.close();
		}
	}
	
	@FXML Button getim;
	public void getImage(ActionEvent e)    
	{
		
		try {
			ImageIO.write(selWebCam.getImage(), "PNG", new File("hello-world.png"));
		} catch (IOException e1) {
			System.out.println("nije usliko");
			}
			
		String url="image.png";
		String barcode="0";
			DecodeWorker worker = new DecodeWorker();
			File f = new File(url);
			Result[] r = null;
		try {
			
			r = worker.decode(f.getAbsoluteFile().toURI(), new DecoderConfig().buildHints());
					barcode=r[0].getText();			
		} catch (Exception e2) {
System.out.println("BARCODE EXCEPTION");
		}
		
		int idcode=Integer.parseInt(barcode);
		int test = 0;
		try {
			test = DatabaseManipulation.FindYourIDVoter(DatabaseManipulation.returnConnection(), 
					idcode);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			System.out.println("Ne valja find ID");
		}
		if(test==1)
		{
			sc=(Scene) getim.getScene();
			try {
				root = FXMLLoader.load(getClass().getResource("/application/VotingModeView.fxml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("ne valja fxml loader za voting mode");
			} 	      
			sc.setRoot(root);	
			try {
				DatabaseManipulation.UpdateVoter(DatabaseManipulation.returnConnection(), idcode);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("ne valja updatevoter");
				
			}
			
		}
		else if(test==0)
		{
			sc=(Scene) getim.getScene();
			try {
				root = FXMLLoader.load(getClass().getResource("/application/WebCamViewBadCode.fxml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("ne valja fxml loader za Bad Code");
				
			} 	      
			sc.setRoot(root);			
		}
		else if(test==2){
			sc=(Scene) getim.getScene();
			try {
				root = FXMLLoader.load(getClass().getResource("/application/WebCamViewAlreadyVoted.fxml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("ne valja fxml loader za already Voted");
			} 	      
			sc.setRoot(root);
		}

	}
		
		
	

	public void stopCamera(ActionEvent event) {
		stopCamera = true;
		
		btnStopCamera.setDisable(true);
	}

	public void startCamera(ActionEvent event) {
		stopCamera = false;
		startWebCamStream();
		
		btnStopCamera.setDisable(false);
	}


	
	public void onTroubles(ActionEvent event) throws IOException
	{
		sc=(Scene)troubles.getScene();
		root=FXMLLoader.load(getClass().getResource("/application/TypeVoterModeView.fxml"));
		sc.setRoot(root);
	}
}
