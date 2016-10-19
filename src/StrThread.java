import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

public class StrThread implements Runnable{
	public String str;
	
	
	public StrThread(String str) {
	      this.str = str;
	   }
	public void run() {
		try
         	{
			 	 File file = new File("/home/user/Desktop/sem2/IRE/final/src/results1/secInd.txt");
				 FileInputStream fis = new FileInputStream(file);
				 BufferedReader brt = new BufferedReader(new InputStreamReader(fis));
				 String sCurrentLine;
				 Long frmLoc = 0L;
				 Long toLoc = 0L;
				 boolean fld = false;
				 String field = null;
				 String[] arr = null;
				 if(str.contains(":"))	
				 {
					 List<String> fields = Arrays.asList(str.split(":"));
					 field = fields.get(0);
					 str = fields.get(1);
					 //System.out.println(field +" sdsd "+ str);
					 fld = true;
				 }
				 while ((sCurrentLine = brt.readLine()) != null) {
			            arr = sCurrentLine.split("-");
			            if(str.compareTo(arr[0])>0)
			            	//break;
			            	frmLoc = Long.parseLong(arr[1]);
			            else
			            {
			            	toLoc = Long.parseLong(arr[1]);			            	
			            	break;
			            }
				 }
				 //System.out.println("from "+frmLoc+" To "+toLoc+" "+str);
				/* RandomAccessFile rafs = new RandomAccessFile("/home/user/Desktop/sem2/IRE/final/src/results1/secInd.txt", "r");
				 rafs.seek(frmLoc);
				 System.out.println("from "+rafs.readLine()+" "+str);
				 rafs.seek(toLoc);
				 String scanTill = null;
				 scanTill = rafs.readLine();
				 System.out.println("to "+scanTill+" "+str);
				 rafs.seek(frmLoc);
				 while ((sCurrentLine = rafs.readLine()) != null) {
			            arr = sCurrentLine.split("-");
			            if(str.compareTo(arr[0])>0)
			            	//break;
			            	frmLoc = Long.parseLong(arr[1]);
			            else if(str.compareTo(arr[0])<=0||arr[0]==scanTill)
			            {
			            	toLoc = Long.parseLong(arr[1]);
			            	break;
			            }
				 }*/
				 RandomAccessFile raff = new RandomAccessFile("/home/user/Desktop/sem2/IRE/final/src/results1/finalfile.txt", "r");
				 raff.seek(frmLoc);
				 //System.out.println("from "+raff.readLine()+" "+str );
				 raff.seek(toLoc);
				 //System.out.println("to "+raff.readLine()+" "+str);
				 //System.out.println(Searching.share);
				 brt.close();
				// rafs.close();
				
				 //RandomAccessFile rafm = new RandomAccessFile("/home/user/Desktop/sem2/IRE/final/src/results1/finalfile.txt", "r");
				 raff.seek(frmLoc);
				 while ((sCurrentLine = raff.readLine()) != null) {
			            arr = sCurrentLine.split("=");
			          
			            if(str.compareTo(arr[0])==0)
			            {
			            	 //System.out.println(arr[0]+" "+str);
			            	//toLoc = arr[1];
			            	//System.out.println("found "+arr[1]);
			            	AddtoDict(arr[1],fld,field);
			            	break;
			            }
			            else if(str.compareTo(arr[0])<0)
			            {			            	
			            	//System.out.println("Not Found "+arr[0]);
			            	break;
			            }
				 }
				 raff.close();
				 
         	}
		catch(Exception e)
		{
		}
	}
	public void AddtoDict(String Posting, boolean fld, String field){
		 int nDoc = 0;
		 boolean flag = false;
		 List<String> tfidfs =  Arrays.asList(Posting.split(","));
		 for (String tfidf : tfidfs){
			 //System.out.println(tfidf);
			 List<String> list =  Arrays.asList(tfidf.split("-"));
			
			 long ti = Long.parseLong( list.get(0));
			 //System.out.println(ti);
			 //System.out.println( list.get(1));
			 List<String> docs =  Arrays.asList(list.get(1).split("\\|"));
			 //System.out.println(docs);
			 for (String str : docs){
				 boolean fldexists = true;
				// System.out.println("string "+str);
				 List<String> doc =  Arrays.asList(str.split(":"));
				 String docId = doc.get(0);
				 if(fld==true){
					 int f = Integer.parseInt(doc.get(1));
					 String b = Integer.toBinaryString(f);
					 //System.out.println(docId+" "+b);
					 char ch[] = b.toCharArray();					 
					 int index = Searching.dictMap.get(field.charAt(0));
					 //System.out.println("sds "+" "+ch.length+ " "+field+" "+index);
					 int len = ch.length;
					/* for(int i=0;i<len;i++){
						 System.out.println(ch[i]);
					 }*/
					 char temp[] = new char[6];
					 for(int j=4; j >= len;j--)
					 {
						 temp[j] = '0';
					 }
					for(int j=len-1, i=0;i<len;i++,j--)
					 {
						temp[j] = ch[i];
						// System.out.println("yesss");
						 //ch[i] = '0';
					 }	
					/* for(int i=0;i<len;i++){
					 System.out.println(ch[i]);
				 	}*/
					 //System.out.println("len "+index+ " "+ch[index-1]);
					 if(temp[index-1]!='1')
					 {
						 fldexists = false;
					 }
					// if(len==5)
						 
				 }
				 //System.out.println("doc "+docId);
				 if(fldexists)
				 {	 
					 if(Searching.docCount.get(docId)==null) {
						nDoc++;
						if(nDoc==100)
						{
							//flag = true;
							//break;
						}
						//System.out.println(docId+" "+ti);
						 Searching.docCount.put(docId, ti);
					 } 
					 else {
						 long sb;
						 nDoc++;
						 sb = Searching.docCount.get(docId);
						 //List<String> cnt =  Arrays.asList(sb.split(":"));
						 //int count = Integer.parseInt(cnt.get(0));
						 //System.out.println(docId+" "+(ti+sb));
						 Searching.docCount.put(docId, (ti+sb));
					 }
			 }
			 }			 
		 }
		//System.out.println(Searching.docCount);
	}
}
