import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class readXML {
	
	static WikiPage p;
	static StringBuffer buf = new StringBuffer();
	static long time1;
	static long time2;
	public static Long totalpages = 0L;
	public static BufferedWriter bw;
	public static BufferedWriter bout;
	static StringBuffer dbuf = new StringBuffer();
	static Long size = 0L;
	static Long nLine = 0L;
	static File f = new File("/home/user/Desktop/sem2/IRE/final/src/results/dicTitle.txt");
	static File fout = new File("/home/user/Desktop/sem2/IRE/final/src/results/dicInd.txt");
	
	public static void main(String[] args){
		
			//final String input = args[1];
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = null;
			//MultiWayMerge.mergeFiles();
			try{
					f.createNewFile();
					fout.createNewFile();
					bout = new BufferedWriter(new FileWriter(fout));
					bw = new BufferedWriter(new FileWriter(f,true));
			}
			catch(Exception e){
				
			}
			try {
				saxParser = factory.newSAXParser();
			    }
			catch (ParserConfigurationException e1) {
				e1.printStackTrace();
			} 
			catch (SAXException e1) {
				e1.printStackTrace();
			} 
			
			/*
			 * default handler for SAX handler class
			 * all three methods are written in handler's body
			 */
			DefaultHandler handler = new DefaultHandler(){
				
				boolean bIdRead = true; //to handle multiple id tags
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException{
					try				
					{	
						if(qName.equals("page")) {
							//System.out.println("Reading page:");
							p = new WikiPage();	
						}
						buf = new StringBuffer(); 
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void characters(char ch[], int start, int length) throws SAXException
				{
					try {
						for (int i = start; i < start + length; i++) {
						buf.append(Character.toLowerCase(ch[i]));
			        	        }
					    }
					    catch (Exception e) {
						e.printStackTrace();
						}
			    }
				
				public void endElement(String uri, String localName, String qName)
				{				
					if(qName.equals("text"))
					{
						p.setBodytext(buf);
						//System.out.println(text);
					}
					else if(qName.equals("title"))
					{
						//System.out.println("Read title");
						p.setTitle(buf);
						
						//System.out.println(p.getTitle());
					}
					else if(qName.equals("id")&&bIdRead)
					{
						//System.out.println("Read id");
						
						p.setPageId(buf.toString());
						bIdRead = false;
						try{
							
						   bw = new BufferedWriter(new FileWriter(f,true));
							bw.write(p.getPageId().toString().trim()+"-"+p.getTitle().toString().trim()+"\n");
							nLine++;
							if(nLine==1){
						 		 dbuf.append(p.getPageId()+"-");
						 		 //bw.close();
						 		size = f.length();
						 		//bw = new  BufferedWriter(new FileWriter(f,true));
							}
							if(nLine==1000){
								 dbuf.append(size+"\n");
						 		 //System.out.println(bt.toString());
						 		 bout.write(dbuf.toString());
						 		 nLine = 0L;
						 		 dbuf.setLength(0);
							}
							bw.close();
						}
						catch(Exception e){	
							System.out.println("Error in DocTitle ");
						}
					}	
					else if(qName.equals("page"))
					{
																																																																																				bIdRead = true;
						//System.out.println("Read page");
						extractFields ef = new extractFields();
						ef.fieldextractor(p);
						totalpages++;
					}
					else if(qName.equals("mediawiki"))
					{
						//indexing.fileend();
						//SortnSave.saveToFile(input);
						try{
							bw.close();
						    bout.close();
							if(indexing.mapfinalindex.size()>0)
							SortnSave.saveToFile("/home/user/Desktop/sem2/IRE/final/src/results/file"+indexing.fileCount+".txt");
							time2 = System.currentTimeMillis();
							System.out.println("Time taken "+(time2-time1)/1000+" seconds "+"\nMerging files");
							System.out.println("Total pages read "+totalpages);
							MultiWayMerge.mergeFiles();
							//System.out.println("Making ternary Index ");
							//MultiWayMerge.MakeTerIndex();	
							time1 = System.currentTimeMillis();
							System.out.println("Time taken "+(time1-time2)/1000+" seconds");
							/*System.out.println("file ended ");
							File f = new File("/home/user/Desktop/sem2/IRE/final/src/results/secInd.txt");
							System.out.println("Length "+f.length());*/
							
						}
						catch(Exception e){
							System.out.println("Exception in readXML ");
						}
					}
				}
			
		};
		try {
			time1 = System.currentTimeMillis();
			saxParser.parse("/home/user/dump.xml",handler);
			//saxParser.parse("/home/user/Desktop/sem2/IRE/hund.xml",handler);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	        
}
