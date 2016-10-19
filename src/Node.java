import java.io.BufferedReader;
import java.util.List;

public class Node {
	
	String word;
	List<String> posting;
	BufferedReader br;
	
	public String getData(){
		return this.word;
	}

}
