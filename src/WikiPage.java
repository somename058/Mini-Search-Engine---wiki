public class WikiPage {

	public String pageId = "";
	public StringBuffer title = new StringBuffer();
	public StringBuffer infoBox = new StringBuffer();
	public StringBuffer extLinks  = new StringBuffer();
	public StringBuffer category = new StringBuffer();
	public StringBuffer text = new StringBuffer();
	public StringBuffer ref = new StringBuffer();
	public int nTitle = 0;
	public int nInfoBox = 0;
	public int nExtLink = 0;
	public int nCategory = 0;
	public int nText = 0;
	public int nRef = 0;
	private char [] txt = new char[100];
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public int getnTitle() {
		return nTitle;
	}
	public void setnTitle(int nTitle) {
		this.nTitle = nTitle;
	}
	public int getnText() {
		return nText;
	}
	public void setnText(int nText) {
		this.nText = nText;
	}
	public int getnCategory() {
		return nCategory;
	}
	public void setnCategory(int nCategory) {
		this.nCategory = nCategory;
	}
	public int getnInfoBox() {
		return nInfoBox;
	}
	public void setnInfoBox(int nInfoBox) {
		this.nInfoBox = nInfoBox;
	}
	public int getnExtLink() {
		return nExtLink;
	}
	public void setnExtLink(int nExtLink) {
		this.nExtLink = nExtLink;
	}
	public char[] getText() {
		return txt;
	}
	public void setText(char[] text) {
		this.txt = text;
	}
	public int getnRef() {
		return nRef;
	}
	public void setnRef(int nRef) {
		this.nRef = nRef;
	}
	public StringBuffer getInfobox() {
		return infoBox;
	}
	public void setInfobox(StringBuffer infoBox) {
		this.infoBox = infoBox;
	}
	public StringBuffer getBodytext() {
		return text;
	}
	public void setBodytext(StringBuffer text) {
		this.text = text;
	}
	public StringBuffer getTitle() {
		return title;
	}
	public void setTitle(StringBuffer title) {
		this.title = title;
	}
	public StringBuffer getCategory() {
		return category;
	}
	public void setCategory(StringBuffer category) {
		this.category = category;
	}
	public StringBuffer getReferences() {
		return ref;
	}
	public void setReferences(StringBuffer ref) {
		this.ref = ref;
	}
	public StringBuffer getExternallinks() {
		return extLinks;
	}
	public void setExternallinks(StringBuffer extLinks) {
		this.extLinks = extLinks;
	}
	

}
