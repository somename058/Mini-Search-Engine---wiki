import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class MultiWayMerge {
	
	//static int noOfFiles = 2334;
	static int noOfFiles = indexing.fileCount;
	static PriorityQueue < Node >  priQ = new PriorityQueue <Node> (noOfFiles+2, new Comparator<Node>( ){
		 public int compare(Node i, Node j) {
					 return i.getData().compareTo(j.getData()); 
			 }
		 });
	
	static Map<String, String> wordPosting = new HashMap<String, String>();
	static Long linesInPrim = 0L;
	static Long linesInSec = 0L;
	static Long sizePrim;
	static Long sizeSec;
	static StringBuffer bf = new StringBuffer();
	static StringBuffer bt = new StringBuffer();
	//static BufferedWriter bw;
	
	public static void main(String args[]){
		//System.out.println("called ");
		mergeFiles();
	}
	
	public static void mergeFiles(){
		//System.out.println("called ");
		File f = new File("/home/user/Desktop/sem2/IRE/final/src/results/finalfile.txt");
		File sf = new File("/home/user/Desktop/sem2/IRE/final/src/results/secInd.txt");
		//File tf = new File("/home/user/Desktop/sem2/IRE/final/src/results/terInd.txt");
		try{
			f.createNewFile();
			//BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			sf.createNewFile();
			BufferedWriter bwsf = new BufferedWriter(new FileWriter(sf));
			//tf.createNewFile();
			//BufferedWriter bwtf = new BufferedWriter(new FileWriter(tf));
			for(int i=0;i<=noOfFiles;i++ )
			{
				File file = new File("/home/user/Desktop/sem2/IRE/final/src/results/file"+i+".txt");				
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				ReadLineFromFile(br);
				
			}			
			Node minNode = new Node();		
			String curWord = null;
			while(!priQ.isEmpty()){
				//System.out.println("Sizee "+priQ.size());
				minNode = priQ.poll();
				if(!minNode.word.equals(curWord)&&curWord!=null){
					//System.out.println("worddd "+minNode.word);
					//WriteToFile(curWord,bw,bwsf,bwtf,f,sf);
					WriteToFile(curWord,f,sf,bwsf);
				}				
				NodetoDict(minNode);
				ReadLineFromFile(minNode.br);
				curWord = minNode.word;
			}
			//WriteToFile(curWord,bw,bwsf,bwtf,f,sf);
			WriteToFile(curWord,f,sf,bwsf);
			//bw.close();
			//System.out.println("Length "+sf.length());
			bwsf.close();
			//bwtf.close();
		}	
			//f.close();
		catch(Exception e){
		}
	}
	public static void WriteToFile(String node, File f, File sf, BufferedWriter bwsf){
		 //Map<String, String> Dict = new TreeMap<String, String>(wordPosting);
		//StringBuffer sf = new StringBuffer();
		
		 try
		 {
			 //System.out.println("Writingg ");
			 BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
			 //BufferedWriter bwsf = new BufferedWriter(new FileWriter(sf));
			 //BufferedWriter bwtf = new BufferedWriter(new FileWriter(tf,true));
			 long docfreq = wordPosting.size();
			 //String string;
			 double idf = Math.log10(readXML.totalpages/docfreq);
			 HashMap<Long, StringBuffer> tfidfDict = new HashMap<Long, StringBuffer>();
			 StringBuffer wt = new StringBuffer();
			 int nDocs = 0;
			 wt.append(node+"=");
			 linesInPrim++;
			 if(linesInPrim==1){
				// bw.close();
				 sizePrim = f.length();
				 //bw = new BufferedWriter(new FileWriter(f,true));
				 bf.append(node+"-");
				 
			 }
			 //sizePrim  = f.length();
			 //System.out.println("word is "+node);
			 Set<String> docids = wordPosting.keySet();
			 for(String docid: docids)
				{
				 	 String buf = wordPosting.get(docid);
					 List<String> list =  Arrays.asList(buf.split(":"));
					 long tfidf = (long)idf * (Long.parseLong(list.get(0))/10L);
					 //System.out.println("IDF "+idf+" tfidf "+tfidf+" tf "+Integer.parseInt(list.get(0)));
					 if(tfidfDict.get(tfidf)==null) {
						 StringBuffer sb = new StringBuffer();
						 sb.append(docid+":");
						 sb.append(list.get(1)+"|");
						 nDocs++;
						 tfidfDict.put(tfidf, sb);
					 } 
					 else {
						 StringBuffer sb = new StringBuffer();
						 sb.append(docid+":");
						 sb.append(list.get(1)+"|");
						 nDocs++;
						 tfidfDict.get(tfidf).append(sb);
					 }
				}
			 	Set<Long> tfids = tfidfDict.keySet();
				List<Long> list1 = new ArrayList<Long>();
				list1.addAll(tfids);
				Collections.sort(list1, new Comparator<Long>() {
					@Override
					public int compare(Long tfidf1, Long tfidf2) {
						return -(tfidf1.compareTo(tfidf2));
					}
				});
			 	for(Long tfidf : list1){
			 		nDocs++;
			 		if(nDocs==100)
			 			break;
			 		wt.append(tfidf+"-");
			 		wt.append(tfidfDict.get(tfidf)+",");
			 	}
			 wt.append("\n");
			 
			 //System.out.println(wttoString());
			 bw.write(wt.toString());
			 //System.out.println("length "+f.length());
			 if(linesInPrim==2000){
			 	 bf.append(sizePrim+"\n");
			 	 linesInSec++;
			 	/* if(linesInSec==1){
			 		List<String> list =  Arrays.asList(bf.toString().split("-"));
			 		 bt.append(list.get(0)+"-");
			 		sizeSec = sf.length();
			 	 }*/
			 	 bwsf.write(bf.toString());
			 	 //System.out.println("length "+sf.length());
			 	 //System.out.println("IT is this "+sf.toString());
			 	 linesInPrim = 0L;
			 	 bf.setLength(0);
			 }
			/*if(linesInSec==1000){
		 		 bt.append(sizeSec+"\n");
		 		 //System.out.println(bt.toString());
		 		 bwtf.write(bt.toString());
		 		 linesInSec = 0L;
		 		 bt.setLength(0);
		 	 }*/
			 wt.setLength(0);
			 tfidfDict.clear();
			 wordPosting.clear();
			 bw.close();
			 //bwsf.close();
			// bwtf.close();
		 }
		 catch(Exception e){	
			 System.out.println("Errr "+e);
		 }
	}
	public static void ReadLineFromFile(BufferedReader br){
		try
		{
			String line = br.readLine();
			//System.out.println(line);
			if(line!=null)
			{
				List<String> wordPosting = Arrays.asList(line.split("="));
				Node newNode = new Node();
				newNode.word = wordPosting.get(0);
				//System.out.println(wordPosting.get(1));
				newNode.posting = Arrays.asList(wordPosting.get(1).split(","));
				newNode.br = br; 
				priQ.add(newNode);
			}	
			//br.close();
		}
		catch(Exception e){
			System.out.println("Errorrr "+e);
		}
	}
	public static void NodetoDict(Node minNode){
		List<String> docList = minNode.posting;
		//int len = docList.size();
		try{
		for(String pairs : docList){
			//System.out.println("Helllo "+pairs);
			if(pairs!="\n")
			{
				List<String> docPosting = Arrays.asList(pairs.split(":"));	
				String docID = docPosting.get(0);
				String posting = docPosting.get(1)+":"+docPosting.get(2);
				if (wordPosting.containsKey(docID)) {
					//System.out.println("ID "+docID);				
					wordPosting.put(docID, posting);
				}
				else{
					wordPosting.put(docID, posting);
				}
			}	
		}
		}
		catch(Exception e){
			
		}
		
	}
}
