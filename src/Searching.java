import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class comparing implements Comparator<String> {

    Map<String, Long> map;
   
    public comparing(Map<String, Long> base) {
        this.map = base;
    }
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
public class Searching {
	public static Map<Character,Integer > dictMap = new HashMap<Character,Integer>();
	public static String share = "Hello World! ";
	public static HashMap<String, Long> docCount = new HashMap<String, Long>();
	public static TreeMap<String, Long> SortByValue
				(HashMap<String, Long> map) {
			comparing vc =  new comparing(map);
			TreeMap<String,Long> sortedMap = new TreeMap<String,Long>(vc);
			sortedMap.putAll(map);
			return sortedMap;
		}
	 public static int line = 1;

	public static void main(String args[]){
		dictMap.put('t',5);
		dictMap.put('e',4);
		dictMap.put('c',3);
		dictMap.put('i',2);
		dictMap.put('b',1);
		while(true){
			System.out.println("\nEnter your query");
			try{
				 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				 StringBuffer queryStrings = new StringBuffer();
				 StringBuffer sb = new StringBuffer();
				 char ch [] = br.readLine().toCharArray();
				 int length = ch.length;
				 for (int i = 0; i <length; i++) {
					 //System.out.print(ch[i]);
						ch[i] = Character.toLowerCase(ch[i]);
						if (ch[i] >= 'a' && ch[i] <= 'z'|| ch[i] == ':') {
							sb.append(ch[i]);
							//System.out.println(sb.toString()+" "+sb.length());
						}
						else
						{
							if(sb.length()>0)
							{
								//System.out.println(sb.toString());
								stemmer stem = new stemmer();
								//String token;
								stem.add(sb.toString().toCharArray(), sb.length());
								stem.stem();
								queryStrings.append(stem.toString()+" ");
								sb.setLength(0);
								//System.out.println("now"+sb.toString());
							}
						}
					}
				 if(sb.length()>0)
					{
						//System.out.println(sb.toString());
						stemmer stem = new stemmer();
						//String token;
						stem.add(sb.toString().toCharArray(), sb.length());
						stem.stem();
						queryStrings.append(stem.toString()+" ");
						sb.setLength(0);
						//System.out.println("now"+sb.toString());
					}
				 //System.out.println(queryStrings.toString());
				 SearchEngine(queryStrings);
				 
				// System.out.println("done");
				 //System.out.println(docCount);
				 TreeMap<String, Long> sortedMap = SortByValue(docCount);
				 int best = 0;
				 //List<String> docIds = Arrays.asList();
				 for (Map.Entry<String, Long> entry : sortedMap.entrySet())
                 {
					 String str = entry.getKey();
                     //System.out.println("doc "+str+" tf "+entry.getValue());
					 //docIds.add(str);
					 if(best==10)
					 {
						 line = 1;
						 break;
					 }	 
					 getTitle(str);
					 line++;
					 best++;
					 docCount.clear();
                 }
				 //System.out.println(docIds);
				line = 1;
			 }
			 catch(Exception e){
				 
			 }
		}
	}
	public static void getTitle(String str){
		
		File file = new File("/home/user/Desktop/sem2/IRE/final/src/results1/dicInd.txt");
		try{
			FileInputStream fis = new FileInputStream(file);
			BufferedReader brt = new BufferedReader(new InputStreamReader(fis));
			String sCurrentLine;
			Long frmLoc = 0L;
			long docId = Long.parseLong(str);
			String[] arr = null;
			 while ((sCurrentLine = brt.readLine()) != null) {
		            arr = sCurrentLine.split("-");
		            if(docId>Long.parseLong(arr[0]))
		            	//break;
		            	frmLoc = Long.parseLong(arr[1]);
		            else
		            {	            	
		            	break;
		            }
			 }
			 brt.close();
			 RandomAccessFile raff = new RandomAccessFile("/home/user/Desktop/sem2/IRE/final/src/results1/dicTitle.txt", "r");
			 raff.seek(frmLoc);
			 while ((sCurrentLine = raff.readLine()) != null) {
		            arr = sCurrentLine.split("-");
		          
		            if(docId==Long.parseLong(arr[0]))
		            {
		            	System.out.println(line + ". Doc ID: "+docId+" Title: "+arr[1]);
		            	break;
		            }
			 }
		}
		catch(Exception e){
			
		}
		 
		
	}
	
	public static void SearchEngine(StringBuffer queryStrings){
		 List<String> queryTerms =  Arrays.asList(queryStrings.toString().split(" "));
		 int len = queryTerms.size(),i=0;
		 Thread myThreads[] = new Thread[len];
		 for(String str :queryTerms){
			 try{
				    //System.out.println("String is "+str);
 			 	    Runnable r = new StrThread(str);
 			 	    myThreads[i] = new Thread(r);
 			 	    myThreads[i++].start(); 
				    //r.wait();
			
				 for (int j = 0; j < len; j++) {
					    myThreads[j].join(); //todo add catch exception
					}
				 //System.out.println("Die");
				}	 
				 catch(Exception e){ 
			 }
		 }
	}
}
