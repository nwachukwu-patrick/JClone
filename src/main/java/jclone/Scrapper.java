package main.java.jclone;


//import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Scrapper {
  @SuppressWarnings("unchecked")
  Document html = null;
  String url;
  public void Scrapper(){}
  public Scrapper(String url) {
      this.url = url;
      try{

          this.html = Jsoup.connect(url).get();

      }catch (Exception ex){
  System.out.println(ex.getMessage());
      }
  }
  
  public Document getHtml(String url) {
     Document html = null;
      try{

          html = Jsoup.connect(url).ignoreContentType(true).timeout(1000 * 1000).get();

      }catch (Exception ex){
  System.out.println(ex.getMessage());
      }
      return html;
  }


  public String  getTitle(){
      return this.html.title();
  }
  public List <String> getTags(String tagName){
      LinkedList <String> tags = new LinkedList<>();
      tags.add(this.html.getElementsByTag(tagName).toString());
      return tags;

  }
  public List<String> getAttr(String TagName, String attr){
      Elements attri = new Elements();
      LinkedList <String> attrValue = new LinkedList<>();
      attrValue.add(this.html.getElementsByTag(TagName).attr(attr));
      return attrValue;
  }
  public List<String> getLinks(String link, String TagName, String attr){
      List <String> attrValue = new LinkedList<>();
      Document htmll;
      try{

          htmll = Jsoup.connect(link).get();
          Elements attri = new Elements();
          attrValue.add(htmll.getElementsByTag(TagName).attr(attr));
      }catch (Exception ex){
          System.out.println(ex.getMessage());
      }

      return attrValue;
  }
  public List<String> getTagText(String tag){
      LinkedList <String> text = new LinkedList<>();
      this.html.getElementsByTag(tag).forEach(n ->text.add(n.text()));
      return  text;
  }


  public String test(String outputDir,String url){
	  String result = "Completed check "+outputDir+" For output";
      List <String> tempunVisitedLinks= new ArrayList<>();
      String  baseURL = url;
     Document html = getHtml(baseURL);


      Elements Ls = html.getElementsByTag("a");
      List<String> linkAttributes = new LinkedList<>(); //placeholder for link attributes
      Ls.forEach(n->{
    	 if(n.equals(" ")) {
    		 
    	 }else {
    		 linkAttributes.add(n.attr("href"));
    	 }
      });
      
          linkAttributes.forEach(n->{
              String link = (String) n;
              Boolean found = false;
             if(link.startsWith("http")){
                  // External Links
                  if(link.endsWith(".png")||  link.endsWith(".pdf") || link.endsWith(".jpg")||  link.endsWith(".jpeg")){
                      //External Image File

                  }else if( link.endsWith(".pdf") ){
                      //External PDF File

                  }else if( link.endsWith(".mp4")){
                      //External PDF File

                  }
              } else if( link.contains("@")){
                  //email
//                      System.out.println(link);
              }else {
                  //form new link
                  String currentURL = url;
                  if(url.endsWith("/")){
                      currentURL = url + n;
                  }else{
                      currentURL = url + "/" + n;
                  }

                  if (url.contains(currentURL)) {

                  }else{
                      tempunVisitedLinks.add(currentURL);
                      	for(String x :tempunVisitedLinks ) {
                      		
                      		 if(x.equalsIgnoreCase(currentURL)){
                                 found  = true;
                             }else{
                                 found = false;
                             }
                      		 currentURL = x;
                      }

                        
                      	
                              
                      }
                  }

      });
          this.search(tempunVisitedLinks).forEach(n->{
//        	  System.out.println(this.getHtml(n).outerHtml());
//        	 this.saveFile(outputDir,
//        			 n.substring(n.lastIndexOf("=")+1)+".php",
//        			 this.getHtml(n).outerHtml());

        	  this.saveWEBPAGE(outputDir, n, baseURL);
        	  this.saveCSS(outputDir,n,baseURL);
        	  this.saveJS(outputDir,n,baseURL);
        	  this.saveIMAGE(outputDir,n,baseURL);
        	  this.saveVIDEO(outputDir, n, baseURL);
        	  this.saveMUSIC(outputDir, n, baseURL);
        	  
        
          });
//          this.getCSS(this.search(tempunVisitedLinks).remove(1));
//          System.out.println(this.search(tempunVisitedLinks).remove(1));
//          System.out.println(this.getHtml(this.search(tempunVisitedLinks).remove(1)).outerHtml());
//          this.saveFile(outputDir, url, html.outerHtml());
          return result;
  }
  public void saveCSS(String outputDir,String url,String baseURL) {
	  List <String> cssfiles = new LinkedList<>();
	  List <String> localcssfiles = new LinkedList<>();
	  Elements csslink = this.getHtml(url).getElementsByAttributeValue("rel", "stylesheet");
	  csslink.forEach(n->{
		  cssfiles.add(n.attr("href"));
	  });
	 
	 cssfiles.forEach(n->{
		 if(n.startsWith("https://")) {
			 
		 }else {
			 localcssfiles.add(n);
		 }
	 });
//	  System.out.println(this.getHtml(baseURL+"/"+this.search(localcssfiles).remove(1)).text());
	 this.search(localcssfiles).forEach(n->{
		 String cssurl = "";
		 String filename = n.substring(n.lastIndexOf("/")+1);
		 int wantedfolderlen = 0;
		 String cssFolder = "";
		 String cssfilename = "";
		 wantedfolderlen = n.length()- n.substring(n.lastIndexOf("/")).length();
		 cssFolder = n.substring(0,wantedfolderlen);
		 if(baseURL.endsWith("/")){
			 cssurl = baseURL+n;
		 }else {
			 cssurl = baseURL+"/"+n;
//		 System.out.println(n);
		 if(filename.endsWith(".css")) {
		
//			 System.out.println(filename);
			 cssfilename = n;
		 }else {
			 int wantedlen = filename.lastIndexOf(".css")+4;
			 filename = filename.substring(0,wantedlen);
			 wantedlen = n.lastIndexOf(".css")+4;
			 cssfilename = n.substring(0,wantedlen);
			
		 }
		 
		 }
		 cssFolder = cssFolder.replace('/', '\\');
//		 System.out.println(filename +"  \n  "+cssFolder);
//		 System.out.println(outputDir+"/"+cssFolder);
		  this.saveFile(outputDir+"\\"+cssFolder,filename, this.getHtml(cssurl).text());
	 });
//	 System.out.println("Done...");
  }
  
  public void saveJS(String outputDir,String url,String baseURL) {
	  List <String> jsfiles = new LinkedList<>();
	  List <String> localjsfiles = new LinkedList<>();
//	  System.out.println(this.getHtml(url).getElementsByTag("script"));
	  Elements jstags = this.getHtml(url).getElementsByTag("script");
	  jstags.forEach(n->{
		  jsfiles.add(n.attr("src"));
	  });
	 
	 jsfiles.forEach(n->{
//		 System.out.println(n);
		 if(n.startsWith("https://")) {
			 
		 }else if(n.startsWith("//")) {
			 
		 }else if(n == "") {
			 
		 }else {
			 localjsfiles.add(n);
		 }
	 });
	 
	  
	 this.search(localjsfiles).forEach(n->{
		 String jsurl = "";
		 String filename = n.substring(n.lastIndexOf("/")+1);
		 int wantedfolderlen = 0;
		 String jsFolder = "";
		 String jsfilename = "";
		 wantedfolderlen = n.length()- n.substring(n.lastIndexOf("/")).length();
		 jsFolder = n.substring(0,wantedfolderlen);
		 if(baseURL.endsWith("/")){
			 jsurl = baseURL+n;
		 }else {
			 jsurl = baseURL+"/"+n;
//		 System.out.println(n);
		 if(filename.endsWith(".js")) {
			
//			 System.out.println(filename);
			 jsfilename = n;
		 }else {
			 
			 int wantedlen = filename.lastIndexOf(".js")+3;
			 filename = filename.substring(0,wantedlen);
			 wantedlen = n.lastIndexOf(".js")+3;
			 jsfilename = n.substring(0,wantedlen);
		 }
		 }
		 jsFolder = jsFolder.replace('/', '\\');
		 String Dir = outputDir+"\\"+jsFolder;
		  File file = new File(Dir+"/"+filename);
	      File f1 = new File(Dir);

	      try{
	         f1.mkdirs();
	      }catch (Exception e){
	          System.out.println(e.getMessage());
	      }

	          try {
	             if(file.createNewFile()){
	            	 this.download(jsurl, file);
	            	 
	             }
	          }catch (Exception e) {
				throw new RuntimeException();
			}
		
	 });
	 
  }
  public void saveVIDEO(String outputDir,String url,String baseURL) {
	  List <String> jsfiles = new LinkedList<>();
	  List <String> localjsfiles = new LinkedList<>();
//	  System.out.println(this.getHtml(url).getElementsByTag("script"));
	  Elements jstags = this.getHtml(url).getElementsByTag("video");
	  jstags.forEach(n->{
		  jsfiles.add(n.attr("src"));
	  });
	 
	 jsfiles.forEach(n->{
//		 System.out.println(n);
		 if(n.startsWith("https://")) {
			 
		 }else if(n.startsWith("//")) {
			 
		 }else if(n == "") {
			 
		 }else {
			 localjsfiles.add(n);
		 }
	 });
	 
	  
	 this.search(localjsfiles).forEach(n->{
		 String jsurl = "";
		 String filename = n.substring(n.lastIndexOf("/")+1);
		 int wantedfolderlen = 0;
		 String jsFolder = "";
		 String jsfilename = "";
		 try {
		 wantedfolderlen = n.length()- n.substring(n.lastIndexOf("/")).length();
		 jsFolder = n.substring(0,wantedfolderlen);
 }catch(java.lang.StringIndexOutOfBoundsException ex) {
			 
		 }
		 if(baseURL.endsWith("/")){
			 jsurl = baseURL+n;
		 }else {
			 jsurl = baseURL+"/"+n;
//		 System.out.println(n);
		 if(filename.endsWith(".jpeg") || filename.endsWith(".png")
				 || filename.endsWith(".jpg")) {
			
//			 System.out.println(filename);
			 jsfilename = n;
		 }else {
			 
//			 int wantedlen = filename.lastIndexOf(".js")+3;
//			 filename = filename.substring(0,wantedlen);
//			 wantedlen = n.lastIndexOf(".js")+3;
//			 jsfilename = n.substring(0,wantedlen);
		 }
		 }
		
		 jsFolder = jsFolder.replace('/', '\\');
		 String Dir = outputDir+"\\"+jsFolder;
		  File file = new File(Dir+"/"+filename);
	      File f1 = new File(Dir);

	      try{
	         f1.mkdirs();
	      }catch (Exception e){
	          System.out.println(e.getMessage());
	      }

	          try {
	             if(file.createNewFile()){
	            	 this.download(jsurl, file);
	            	 
	             }
	          }catch (Exception e) {
				throw new RuntimeException();
			}
		
	 });
//	 System.out.println("Done...");
	 
  }

  public void saveMUSIC(String outputDir,String url,String baseURL) {
	  List <String> jsfiles = new LinkedList<>();
	  List <String> localjsfiles = new LinkedList<>();
//	  System.out.println(this.getHtml(url).getElementsByTag("script"));
	  Elements jstags = this.getHtml(url).getElementsByTag("audio");
	  jstags.forEach(n->{
		  jsfiles.add(n.attr("src"));
	  });
	 
	 jsfiles.forEach(n->{
		 if(n.startsWith("https://")) {
			 
		 }else if(n.startsWith("//")) {
			 
		 }else if(n == "") {
			 
		 }else {
			 localjsfiles.add(n);
		 }
	 });
	 
	  
	 this.search(localjsfiles).forEach(n->{
		 String jsurl = "";
		 String filename = n.substring(n.lastIndexOf("/")+1);
		 int wantedfolderlen = 0;
		 String jsFolder = "";
		 String jsfilename = "";
		 try{
		 wantedfolderlen = n.length()- n.substring(n.lastIndexOf("/")).length();
		 jsFolder = n.substring(0,wantedfolderlen);
 }catch(java.lang.StringIndexOutOfBoundsException ex) {
			 
		 }
		 if(baseURL.endsWith("/")){
			 jsurl = baseURL+n;
		 }else {
			 jsurl = baseURL+"/"+n;
//		 System.out.println(n);
		 if(filename.endsWith(".mp3") || filename.endsWith(".ogg")) {
			
//			 System.out.println(filename);
			 jsfilename = n;
		 }else {
			 
//			 int wantedlen = filename.lastIndexOf(".js")+3;
//			 filename = filename.substring(0,wantedlen);
//			 wantedlen = n.lastIndexOf(".js")+3;
//			 jsfilename = n.substring(0,wantedlen);
		 }
		 }
		
		 jsFolder = jsFolder.replace('/', '\\');
		 String Dir = outputDir+"\\"+jsFolder;
		  File file = new File(Dir+"/"+filename);
	      File f1 = new File(Dir);

	      try{
	         f1.mkdirs();
	      }catch (Exception e){
	          System.out.println(e.getMessage());
	      }

	          try {
	             if(file.createNewFile()){
	            	 this.download(jsurl, file);
	            	 
	             }
	          }catch (Exception e) {
				throw new RuntimeException();
			}
		
	 });
//	 System.out.println("Done...");
	 
  }
  public void saveIMAGE(String outputDir,String url,String baseURL) {
	  List <String> jsfiles = new LinkedList<>();
	  List <String> localjsfiles = new LinkedList<>();
//	  System.out.println(this.getHtml(url).getElementsByTag("script"));
	  Elements jstags = this.getHtml(url).getElementsByTag("img");
	  jstags.forEach(n->{
		  jsfiles.add(n.attr("src"));
	  });
	 
	 jsfiles.forEach(n->{
//		 System.out.println(n);
		 if(n.startsWith("https://")) {
			 
		 }else if(n.startsWith("//")) {
			 
		 }else if(n == "") {
			 
		 }else {
			 localjsfiles.add(n);
		 }
	 });
	 
	  
	 this.search(localjsfiles).forEach(n->{
		 String jsurl = "";
		 String filename = n.substring(n.lastIndexOf("/")+1);
		 int wantedfolderlen = 0;
		 String jsFolder = "";
		 String jsfilename = "";
		 try {
		 wantedfolderlen = n.length()- n.substring(n.lastIndexOf("/")).length();
		 jsFolder = n.substring(0,wantedfolderlen);
		 }catch(java.lang.StringIndexOutOfBoundsException ex) {
			 
		 }
		 if(baseURL.endsWith("/")){
			 jsurl = baseURL+n;
		 }else {
			 jsurl = baseURL+"/"+n;
//		 System.out.println(n);
		 if(filename.endsWith(".jpeg") || filename.endsWith(".png")
				 || filename.endsWith(".jpg")) {
			
//			 System.out.println(filename);
			 jsfilename = n;
		 }else {
			 
//			 int wantedlen = filename.lastIndexOf(".js")+3;
//			 filename = filename.substring(0,wantedlen);
//			 wantedlen = n.lastIndexOf(".js")+3;
//			 jsfilename = n.substring(0,wantedlen);
		 }
		 }
		
		 jsFolder = jsFolder.replace('/', '\\');
		 String Dir = outputDir+"\\"+jsFolder;
		  File file = new File(Dir+"/"+filename);
	      File f1 = new File(Dir);

	      try{
	         f1.mkdirs();
	      }catch (Exception e){
	          System.out.println(e.getMessage());
	      }

	          try {
	             if(file.createNewFile()){
	            	 this.download(jsurl, file);
	            	 
	             }
	          }catch (Exception e) {
				throw new RuntimeException();
			}
		
	 });
//	 System.out.println("Done...");
	 
  }
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
//	        System.out.println("Downloaded "+ percent + "%");
	      }
//	      System.out.println("Your download is now complete.");
	      bufferOut.close();
	      input.close();
	    }
	    catch(IOException e){
	      e.printStackTrace();
	    }
	      
	  }

  public void saveWEBPAGE(String outputDir,String url,String baseURL) {
	  String folder= outputDir;
	  
	  int wantedlen = 0;
	  String filename = "";
	  String urlorquery = url.substring(url.lastIndexOf(baseURL));
	  if(url.contains(baseURL)) {
		  urlorquery = url.replace(baseURL, "");
	  }
	  
		 if(urlorquery.length() == 1) {
			 filename = "index.php";
			 if(baseURL.endsWith("/")) {
				 url = baseURL+filename;
			 }else {
				 url = baseURL+"/"+filename;
			 }
		 }else if(urlorquery.contains("#")) {
			 filename = "index.php";
			 if(baseURL.endsWith("/")) {
				 url = baseURL+filename;
			 }else {
				 url = baseURL+"/"+filename;
			 }
		 }else if(urlorquery.contains("=")) {
			
				 filename = url.substring(url.lastIndexOf("=")+1)+".php";
//				 if(baseURL.endsWith("/")) {
					 url = baseURL+urlorquery;
//				 }else {
//					 url = baseURL+"/"+urlorquery;
//				 }
//				 System.out.println(url);
				 
		 }else if(urlorquery.endsWith(".php")) {
			 filename = urlorquery.substring(urlorquery.lastIndexOf("/")+1);
			 wantedlen = filename.lastIndexOf(".php")+5;
			 if(baseURL.endsWith("/")) {
				 url = baseURL+filename;
			 }else {
				 url = baseURL+"/"+filename;
			 }
		 }else if(urlorquery.endsWith(".html")) {
			 filename = urlorquery.substring(urlorquery.lastIndexOf("/")+1);
			 wantedlen = filename.lastIndexOf(".html")+6;
				 url = baseURL+urlorquery;
		 }else {
			 if(urlorquery.contains("?")) {
				 filename = url.substring(url.lastIndexOf("?")+1)+".php";
			 }
			 if(baseURL.endsWith("/")) {
				 url = baseURL+filename;
			 }else {
				 url = baseURL+"/"+filename;
			 }
		 }
		  File file = new File(folder+filename);
	      File f1 = new File(folder);
	      try{
	         f1.mkdirs();
	      }catch (Exception e){
	          System.out.println(e.getMessage());
	      }

	          try {
	             if(file.createNewFile()){
	            	 this.download(url, file);
	            	 
	             }
	          }catch (Exception e) {
				throw new RuntimeException();
			}
//	          this.search(null)
//		 System.out.println(url);
//		 this.saveFile(folder, filename,url );
  }
  
  /***************  SEARCH FOR LNIKS  ********************/
  public List<String> search(List <String> listofstring) {

      List <String> unVisitedLinks = new ArrayList<>();
	 	int start = 1;
      	int current = start;
      	int end = listofstring.size();
      	while(start < end ) {
      		String curl = listofstring.get(current);
      		
      		if(curl.equalsIgnoreCase(listofstring.get(current-1))) {
      		}else {
  			unVisitedLinks.add(listofstring.get(current));
  			
      		}
      		
      		current = start+1;
      		start++;
      	}

      	
	 return  unVisitedLinks;

	  
  }

  public void loopThroughLink(String dirname,String url){
       String  baseURL = url;
      AtomicReference<String> curURL = new AtomicReference<>(url);
      List<String> links = getLinks(curURL.get(),"a","href");
      links.forEach(x-> {
              String q = (String) x;

              if(q.startsWith("https://coinlib.") || q.startsWith("https://www.")|| q.startsWith("https://irishformations.live")
                      ||  q.endsWith(".png")||  q.endsWith(".pdf") || q.endsWith(".jpg")||  q.endsWith(".jpeg")||  q.endsWith("javascript:void(0)")){

                  System.out.println("LINK: " + q);
//                  try {
//                      String name  = q.replaceAll("[-,?,=,/,.,:]","");
//                      test(dirname,name,Jsoup.connect(q).get().outerHtml());
//                      System.out.println("saved...");
//
//                  } catch (IOException e) {
//                      throw new RuntimeException(e);
//                  }
              }else if(q.startsWith("http")){

                  System.out.println("LINK: " + q);
                  try {
                      String name  = q.replaceAll("[-,?,=,/,.,:]","");
                      saveFile(dirname,name,Jsoup.connect(q).get().outerHtml());
                      System.out.println("saved...");

                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              }else if( q.startsWith("/")) {
                  curURL.set(baseURL  + q);
                  String rURL = ""+curURL;
                  System.out.println("LINK: " + rURL);
                  try {
                          String name  = rURL.replaceAll("[-,?,=,/,.,:]","");
                      saveFile(dirname,name,Jsoup.connect(rURL).get().outerHtml());
                          System.out.println("saved...");

                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              }
              else{
                      curURL.set(baseURL + "/" + q);
                      String rURL = ""+ curURL;
                      System.out.println("LINK: " + rURL);

//                      loopThroughLink(rURL);
                  List<String> anchor = getLinks(rURL,"a","href");
                  try {
                          String [] r = rURL.split("=");
                          String name  = rURL.replaceAll("[-,?,=,/,.,:]","");
                      saveFile(dirname,name,Jsoup.connect(rURL).get().outerHtml());
                          System.out.println("saved...");



                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
                  }
      });

  }
  public static void print(Object x){
      System.out.println(x);
  }

   public void  saveFile(String dirname, String name,String content){

      File file = new File(dirname+"/"+name);
      File f1 = new File(dirname);

      try{
         f1.mkdirs();
      }catch (Exception e){
          System.out.println(e.getMessage());
      }

          try {
             if(file.createNewFile()){
                 try {
                     FileWriter writer = new FileWriter(file);
                     writer.write(content);
                     writer.flush();
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             }
          } catch (IOException e) {
              throw new RuntimeException(e);
          }


  }

   public void scrap(String outputDir,String url){
		  String result = "Completed check "+outputDir+" For output";
	      List <String> tempunVisitedLinks= new ArrayList<>();
	      String  baseURL = url;
	     Document html = getHtml(baseURL);

	    	System.out.println(html.outerHtml());
}
   
}
