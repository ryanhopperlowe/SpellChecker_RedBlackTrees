import java.io.*;

public class Dictionary extends RedBlackTree<String>
{
	/**
	 * constructor for dictionary
	 */
	public Dictionary() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("resources/dictionary.txt"));
			
			String word = "";
			while ((word = br.readLine()) != null) {
				this.addNode(word);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param word the word to search for
	 * @return true if the word is found in the dictionary
	 * @return false if the word is not found
	 */
	public boolean searchWord(String word) {
		return lookup(word) != null;
	}
	
	public String[] getMisspelledWords(String text) throws IOException {
		//format text so that all words are easily split
		String formattedText = text.replaceAll("[\\p{Punct}]", " ").toLowerCase();
		//split words at all non letters except for a "'"
		String[] words = formattedText.split("[^a-z�]");
		
		boolean[] found = new boolean[words.length];
		
		//check to see the existance of the word in the dictionary
		int count = 0;
		for (int i = 0; i < words.length; i++) {
			found[i] = words[i].equals("") || this.searchWord(words[i]);
			if (!found[i])
				count++;
		}
		
		String[] notFound = new String[count]; 
		
		//add the not found words to the notFound array
		int j = 0;
		for (int k = 0; k < words.length; k++) {
			if (!found[k]) {
				notFound[j++] = words[k];
			}
		}
		return notFound;
	}
}
