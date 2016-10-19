import java.util.HashSet;

public class stopWords{

	public static final String stopwordslist[] = { "a", "able", "about", "above",
			"across", "after", "again", "against", "all", "almost", "also", "am",
			"among", "an", "and", "any", "are", "aren't", "as", "at", "b", "be",
			"because", "been", "before", "being", "below", "between", "both",
			"but", "by", "c", "can", "cann't", "cannot", "com", "could", "couldn't", "coord", "d",
			"dear", "did", "didn't", "doesn't", "doing", "dont", "don't", "do",
			"does","down", "during", "e", "each", "either", "else", "ever", "every",
			"f", "for", "from", "further", "g", "get", "got", "gr", "h", "had", "hadn't",
			"has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her",
			"here", "her's", "hers", "herself", "him", "himself", "his", "how", "how's",
			"however", "http", "http","https", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into",
			"is", "isn't", "it", "its", "itself", "j", "just", "k", "l", "least", "let", "like",
			"likely", "m", "may", "me", "might", "most", "more", "must", "mustn't", "my",
			"myself", "nbsp", "neither", "n", "no", "nor", "not", "o", "of", "off", "often",
			"on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves",
			"out", "own", "over", "p", "q", "r", "rather", "s", "said", "same", "shan't",
		    "say", "says", "she", "she'd", "she'll", "she's", "should", "shouldn't",
		    "since", "so", "some", "such", "t", "than", "that", "that's", "the", "their", "them",
		    "themselves", "then", "there", "these", "they", "this", "those", "through", "to", "too", "tr","td",
		    "twas", "u", "under", "until", "up", "us", "v", "very", "w", "wants", "was", "wasn't", "we",
		    "we", "we'd", "we'll", "we're", "we've","were", "weren't", "what", "when", "where",
			"which", "while", "who", "whom", "why", "will", "with", "won't", "would", "www",
			"x", "y", "yet", "you", "your", "yours", "yourself", "yourselves", "z" };
	
	static final String[] noise={"([a-z])\\1{1,}", "\\<!--.*?--\\>","http://[a-z./-_]*","\\[\\[Image:.*\\]\\]","\\[\\[File:.*\\]\\]","\\[\\[...?:.*\\]\\]"}; 

	public static final HashSet<String> stopwordsarrList = new HashSet<String>();
	static {
		for (String stopword : stopwordslist) {
			stopwordsarrList.add(stopword);
		}
		
	}
	
}
