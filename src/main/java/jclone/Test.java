package main.java.jclone;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
	public void download(String urlLink, File fileLoc) {
	    try {
	      
	      byte[] buffer = new byte[1024];
	      double TotalDownload = 0.00;
	      int readbyte = 0; //Stores the number of bytes written in each iteration.
	      double percentOfDownload = 0.00;
	      
	      URL url = new URL(urlLink);
	      HttpURLConnection http = (HttpURLConnection)url.openConnection();
	      double filesize = (double)http.getContentLengthLong();
	      
	      BufferedInputStream input = new BufferedInputStream(http.getInputStream());
	      FileOutputStream ouputfile = new FileOutputStream(fileLoc);
	      BufferedOutputStream bufferOut = new BufferedOutputStream(ouputfile, 1024);
	      while((readbyte = input.read(buffer, 0, 1024)) >= 0) {
	        //Writing the content onto the file.
	        bufferOut.write(buffer,0,readbyte);
	        //TotalDownload is the total bytes written onto the file.
	        TotalDownload += readbyte;
	        //Calculating the percentage of download. 
	        percentOfDownload = (TotalDownload*100)/filesize;
	        //Formatting the percentage up to 2 decimal points.
	        String percent = String.format("%.2f", percentOfDownload);
	        System.out.println("Downloaded "+ percent + "%");
	      }
	      System.out.println("Your download is now complete.");
	      bufferOut.close();
	      input.close();
	    }
	    catch(IOException e){
	      e.printStackTrace();
	    }
	      
	  }
	  public static void main(String[] args) {
		 
	    //Please provide the correct URL of what you want to download, and the correct directory with a name and extension to save the downloaded file in.
//	    String link = "http://free.epubebooks.net/ebooks/books/harry-potter-book-1.pdf";
//	    File fileLoc = new File("C:\\Users\\Patrick\\Desktop\\localhost\\HarryPotter.pdf");
//	    
//	    Test d = new Test();
//	    d.download(link, fileLoc);
	  }
	}