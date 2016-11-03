package burch.edu.ibu;

import com.github.sarxos.webcam.Webcam;

public class closecam {
	
	public static void main(String[] args) {
		Webcam webcam=Webcam.getDefault();
		webcam.close();
	}
	
}
