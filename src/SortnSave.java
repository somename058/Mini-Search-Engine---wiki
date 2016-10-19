import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Set;
import java.util.TreeMap;

public class SortnSave {
	
	public static TreeMap<String, ArrayList<String>> outerDict = new TreeMap<String, ArrayList<String>>();
	 
	 public static void saveToFile(String file){
		 //Map<String, Map<Integer, long[]>> outerDict = new TreeMap<String, Map<Integer, long[]>>(indexing.mapIndex);
		
		
		File f = new File(file);
		try {
				//System.out.println("Call made");
				f.createNewFile();	
				outerDict.putAll(indexing.mapfinalindex);
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				StringBuffer wt = new StringBuffer();
				Set<String> keys = outerDict.keySet();
				for(String word: keys)
				{
					
					wt.append(word+"=");
					ArrayList<String> list = outerDict.get(word);
					for (String posting : list) {
						wt.append(posting);
						wt.append(",");
					}
					wt.append("\n");
					//System.out.println(wt.toString());
					bw.write(wt.toString());
					wt.setLength(0);				
				}
				indexing.mapfinalindex.clear();
				//System.out.println("Sizeee: "+outerDict.size());
				outerDict.clear();
				
				bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
