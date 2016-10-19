public class extractFields {
	
	public void fieldextractor(WikiPage page){
		
		//System.out.println("Extracting Fields");
		StringBuffer buffer = page.getBodytext();
		int len = buffer.length();
		char[] ch = new char[len+1];
		//WikiPage page = new WikiPage();		
		buffer.getChars(0, len, ch, 0);
		//WikiPage page = new WikiPage();
		getInfoBox(buffer,ch,page,len);
		getExtLinks(buffer,ch,page,len);
		getCategory(buffer,ch,page,len);
		page.setText(ch);
		indexing i = new indexing(page);
		//i.index(page);
	}
		
		public static void getCategory(StringBuffer str,char ch[], WikiPage page, int len)
		{
			int i;
			StringBuffer sb = new StringBuffer();
			int ind = str.indexOf("[[category:",0);
			while(ind!=-1)
			{
				for(i = ind+11;i<len&&ch[i]!=']';i++)
				{
					sb.append(ch[i]);
					ch[i] = ' ';
				}
				i = i+2;
				sb.append(' ');
				ind = str.indexOf("[[category:",i);
			}
			page.setCategory(sb);
			//System.out.println("Categoryyy extracted:  "+page.getCategory());
		}

		
		public static void getInfoBox(StringBuffer str, char ch[], WikiPage page, int len)
		{
			StringBuffer sb = new StringBuffer();
			int nonTag = 1,i;
			//System.out.println(str);
			//System.out.println(len);		
			int ind = str.indexOf("{{infobox");
			//System.out.println("Indexxx "+ind);
			if(ind==-1)
				return;
			for(i = ind+9;i<len-1;i++)
			{
				if(ch[i]=='{'&&ch[i+1]=='{')
					nonTag++;
				else if(ch[i]=='}'&&ch[i+1]=='}')
					nonTag--;
				else
				    {
					sb.append(ch[i]);
					ch[i] = ' ';
				    }
				if(nonTag == 0)
				{
					page.setInfobox(sb);	
					//System.out.print("InfoBox extracted"+page.getInfobox());
					break;
				}
			}
			
		}
		public static void getExtLinks(StringBuffer str, char ch[], WikiPage page, int len)
		{
			StringBuffer sb = new StringBuffer();
			int i;
			int ind = str.indexOf("==external links==");
			ind = str.indexOf("*[",ind);
			//if(ind==-1)
			//ind = str.indexOf("*[",ind);	
			//System.out.println("index: "+ind);
			while(ind!=-1)
			{	
				for(i=ind+2;i<len&&ch[i-1]!=']';i++)
				{
					if(ch[i]=='[')
						i++;
					if(ch[i+1]=='[')
						break;
					while ( i<len&&ch[i] != ']') 
					{						
						sb.append(ch[i]);
						ch[i] = ' ';
						i++;
						
					}		
				}
				ind = str.indexOf("*[",i);
			}
			page.setExternallinks(sb);
			//System.out.println("External Links extracted: "+page.getExternallinks()+"lalalala");
		}
		

}
