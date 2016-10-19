import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

//import com.ire.tag.TagName;

public class indexing {
	
	WikiPage p;
	public static Map<String, Map<Integer, long[]>> mapIndex = new HashMap<String, Map<Integer, long[]>>();
	public static Map<String, long[]> maparrindex = new HashMap<String, long[]>();
	public static Map<String, ArrayList<String>> mapfinalindex = new HashMap<String, ArrayList<String>>();
	
	static int fileCount = 0;
	
	public indexing(WikiPage p){
		this.p = p;
		//removeStopWords(p);
		try{
			StringBuffer title = p.getTitle();
			//System.out.println(p.getTitle());
			int nTitle = index(title, 2);
			p.setnTitle(nTitle);			
			StringBuffer extLinks = p.getExternallinks();
			int nExtLink = index(extLinks,3);
			p.setnExtLink(nExtLink);
			StringBuffer category = p.getCategory();
			int nCategory = index(category,4);
			p.setnCategory(nCategory);
			StringBuffer infoBox = p.getInfobox();
			int nInfoBox = index(infoBox,5);
			p.setnInfoBox(nInfoBox);
			char [] ch = new char[1000];
			ch = p.getText();
			String str = new String(ch);
			StringBuffer body = new StringBuffer(str);
			int nText = index(body,6);
			p.setnText(nText);
			AddWeights();
			if(mapfinalindex.size()>80000){
				//System.out.println("Yesss");
				SortnSave.saveToFile("/home/user/Desktop/sem2/IRE/final/src/results/file"+fileCount+++".txt");
			}
			maparrindex.clear();
		}
		catch(Exception e){	
			System.out.println("Error here ");
		}
	}
	
	public int index(StringBuffer buffer, int id){
		
		int count = 0;
		int len = buffer.length();
		String str = buffer.toString();
		/*if(id==2)
		System.out.println(str);*/
		str = str.replaceAll("[ ]+", " ");
		for(String noise : stopWords.noise)
		 str = str.replaceAll(noise,"");
		/*if(id == 2)
			System.out.println(str);*/
		char [] ch = new char[len+1];
		str.getChars(0, str.length(), ch, 0);
		StringBuffer sb = new StringBuffer();
		len = ch.length;
		for (int i = 0; i < len; i++) {
			//int chin = (int) ch[i];
			if (ch[i] >= 'a' && ch[i] <= 'z') {
				sb.append((ch[i]));
			} 
			 else {
				if (sb.length() > 0 &&(id==2 || !stopWords.stopwordsarrList.contains(sb.toString()))) {
					stemmer stem = new stemmer();
					String token;
					stem.add(sb.toString().toCharArray(), sb.length());
					stem.stem();
					token = stem.toString();
					if(id==2 ||!stopWords.stopwordsarrList.contains(token)){
						//System.out.println(token+" "+id);
						addToIndex(token,id);
					}
					count++;
				}
				sb.setLength(0);
			}
		}
		return count;
		
	}
	
	public void addToIndex(String token, int id){
		if (maparrindex.containsKey(token)) {
			long[] count = maparrindex.get(token);
			count[1]++;
			count[id]++;
		} 
		else {
			long[] count = new long[8];
			count[0] = Long.parseLong(p.getPageId());
			//System.out.println(Long.parseLong(p.getPageId()));
			count[1]++;
			count[id]++;
			maparrindex.put(token, count);
		}
	}
	
	public void AddWeights(){
		int tcount = p.getnTitle();
		int ecount = p.getnExtLink();
		int ccount = p.getnCategory();
		int bcount = p.getnText();
		int icount = p.getnInfoBox();
		
		Set<String> keys = maparrindex.keySet();
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			long[] values = maparrindex.get(key);
			sb.append(values[0]+":");
			//System.out.println(sb.toString());
			double tvalue = 0f;
			double ivalue = 0f;
			double evalue = 0f;
			double cvalue = 0f;
			double bvalue = 0f;
			if (tcount != 0) {
				tvalue = ((double) values[2] / tcount) * 0.9f * 1000;
			}
			if (ecount != 0) {
				evalue = ((double) values[3] / ecount) * 0.8f * 1000;
			}
			if (ccount != 0) {
				cvalue = ((double) values[4] / ccount) * 0.5f * 1000;
			}
			if (icount != 0) {
				ivalue = ((double) values[5] / icount) * 0.3f * 1000;
			}			
			if (bcount != 0) {
				bvalue = ((double) values[6] / bcount) * 0.2f * 1000;
				 //System.out.println(" value [6] "+values[6]+" Bvalue "+bvalue);

			}
			Double tf = tvalue + ivalue + evalue + cvalue + bvalue;
			sb.append(tf.longValue()+":");
			StringBuffer posting = new StringBuffer();
			//posting.append("000");
			if (values[2] != 0) {
				posting.append("1");
			} else {
				posting.append("0");
			}
			if (values[3] != 0) {
				posting.append("1");
			} else {
				posting.append("0");
			}
			if (values[4] != 0) {
				posting.append("1");
			} else {
				posting.append("0");
			}
			if (values[5] != 0) {
				posting.append("1");
			} else {
				posting.append("0");
			}
			if (values[6] != 0) {
				posting.append("1");
			} else {
				posting.append("0");
			}
			
			byte b = Byte.parseByte(posting.toString(), 2);
			//System.out.println(key+" "+posting.toString()+"  "+b);
			sb.append(b);
			//System.out.println(key+" "+sb.toString());
			addtoFinalIndex(key, sb);
			sb.setLength(0);
		}
		
	}
	
	public void addtoFinalIndex(String token, StringBuffer value){
		if (mapfinalindex.containsKey(token)) {
			ArrayList<String> list = mapfinalindex.get(token);
			list.add(value.toString());
		} else {
			// System.out.println("Token not found"+mapindex);
			ArrayList<String> list = new ArrayList<String>();
			list.add(value.toString());
			mapfinalindex.put(token, list);

		}
	}
	
	/*public void addToFinalIndex(String token, int id){
		
		if(mapIndex.size()>80000){
			//System.out.println("Size:h "+mapIndex.size());
			SortnSave.saveToFile("/home/user/Desktop/sem2/IRE/final/src/results/file"+fileCount+++".txt");
		}		
		Map<Integer, long[]> pageData = new HashMap<Integer, long[]>();
		int pageId = Integer.parseInt(p.getPageId());
		if (mapIndex.containsKey(token)) {	
			pageData = mapIndex.get(token);
			if (pageData.containsKey(pageId))
			{
				long[] counts = pageData.get(pageId);
				counts[1]++;
				counts[id] = 1;
			}
			else
			{
				long[] counts = new long[8];
				counts[1]++;
				counts[id] = 1;
				//System.out.println("PageID "+p.getPageId());
				pageData.put(pageId, counts);
			}			
		}
		else
		{
			long[] counts = new long[8];
			counts[1]++;
			counts[id] = 1;
			pageData.put(pageId , counts);
			//System.out.println("PageID "+p.getPageId());
			//System.out.println("nn"+token+counts[id]);
			mapIndex.put(token,pageData);
		}		
	}	*/
}