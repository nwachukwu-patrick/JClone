package main.java.jclone;

import javax.swing.JOptionPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        String baseURL2 = "http://localhost";
        Scrapper scrapper2 = new Scrapper(baseURL2);
        scrapper2.scrap("C:\\Users\\Patrick\\Desktop\\iotex\\text","https://iotex.io/");;
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
//		   LocalDateTime now = LocalDateTime.now();  
//		    String start = dtf.format(now);
//		    String end = "2022/12/25 02:07:33";
//		   if( start.compareTo(end) == 1 ) {
////
//		            JOptionPane.showMessageDialog(null, "Time Expired");
//		   }else {
//			   JTextField folder = new JTextField();
//		        JTextField url = new JTextField();
//		        Object[] message = {
//		            "Folder where files will be saved", folder,
//		            "Enter Web url to clone", url
//		        };
//
//		        int option = JOptionPane.showConfirmDialog(null, message, "MINI CLONING", JOptionPane.OK_CANCEL_OPTION);
//		        if (option == JOptionPane.OK_OPTION) {
//		        	JOptionPane.showMessageDialog(null,scrapper2.test(folder.getText(),url.getText()));
//		        } else {
//		            JOptionPane.showMessageDialog(null, "Operation canceled");
//		        }
//		        
//		   }
       
    }
}