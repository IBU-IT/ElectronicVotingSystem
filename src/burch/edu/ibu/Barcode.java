package burch.edu.ibu;

import java.io.File;
import java.io.IOException;

import com.google.zxing.Result;
import com.google.zxing.client.j2se.DecodeWorker;
import com.google.zxing.client.j2se.DecoderConfig;


// RIJAD
public class Barcode {
	public static void main(String args[])  {
	
		DecodeWorker worker = new DecodeWorker();
		File f = new File("hello-world.png");
		Result[] r = null;
			try {
				r = worker.decode(f.getAbsoluteFile().toURI(), new DecoderConfig().buildHints());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("SAMO NASTAVI MOLIM TE ");
	}
	public static String returntextfromBC(String photourl)  {
		DecodeWorker worker = new DecodeWorker();
		File f = new File(photourl);
		Result[] r = null;
			try {
				r = worker.decode(f.getAbsoluteFile().toURI(), new DecoderConfig().buildHints());
				String returnstring;
				returnstring=r[0].getText();
				return returnstring;
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
		
		
			}
}
