
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;

public class SpellCheckTester {
	@Test
	public void testDictionary() {
		
		System.out.print("DICTIONARY RUNTIME TEST -------------------------------- \n");
		long start, end;
		
		//check runtime of constructor
		start = System.nanoTime();
		Dictionary d = new Dictionary();
		end = System.nanoTime();
		
		long creationTime = (end - start);
		System.out.println("Dictionary created in " + creationTime + " nanoseconds.");
		
//		//should be less than a second (hopefully)
//		assertTrue(creationTime < 1_000_000_000);
		
		//check runtime of failed search (entire tree traversed)
		start = System.nanoTime();
		assertTrue(!d.searchWord("thisIsNotAWord")); //make sure word fails (entire tree is searched)
		end = System.nanoTime();
		
		long lookupFailure = (end - start);
		System.out.println("Lookup failure ran in " + lookupFailure + " nanoseconds.");
		
//		//should take less time than constructing (hopefully)
//		assertTrue(lookupFailure < creationTime);
		
		//check runtime for successful search O(lg(n))
		boolean found = false;
		start = System.nanoTime();
		assertTrue(d.searchWord("hello")); //ensure word is found
		end = System.nanoTime();
		
		long lookupSuccess = (end - start);
		System.out.println("Lookup success ran in " + lookupSuccess + " nanoseconds.");
		
		//should take less time than failure (hopefully)
		assertTrue(lookupSuccess < lookupFailure);
	}
	
	@Test
	public void testSpellChecker() throws IOException {
		Dictionary dict = new Dictionary();
		String text = "hello slkdf there sjdlf your lkdfjo";
		String[] expected = {"slkdf", "sjdlf", "lkdfjo"};
		
		String[] misspelledWords = dict.getMisspelledWords(text);
		
		assertEquals(misspelledWords.length, expected.length);
		for (int i = 0; i < misspelledWords.length; i++) {
			System.out.println(misspelledWords[i]);
			assertEquals(misspelledWords[i], expected[i]); //ensure the search matches expected
		}
		
	}
	
	@Test
	public void testPoemSpellCheck() throws IOException {
		
		System.out.println("CHECK POEM SPELLING -----------------------");
		
		long start, end, time;
		Dictionary dict = new Dictionary();
		String poem = readTextFromFile("resource/poem1.txt");
		String[] misspelledWords;
		
		System.out.println("Dictionary built");
		
		//check runtime for getting misspelled words (for fun)
		start = System.nanoTime();
		misspelledWords = dict.getMisspelledWords(poem);
		end = System.nanoTime();
		time = end - start;
		
		System.out.println(misspelledWords.length + " Misspelled Words found in " + time + " nanoseconds");
		
		
		
		//ensure all "misspelled" words are not found in dictionary
		for (String word : misspelledWords) {
//			System.out.println(word);
			assertTrue(!dict.searchWord(word));
		}
	}
	
	
	/**
	 * reads text from a file
	 * @param source string with location of file
	 * @return String containing the text read from the file
	 * @throws IOException if no file is found
	 */
	public static String readTextFromFile(String source) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("resources/poem1.txt"));
		String text = "";
		String line = "";
		
		while ((line = br.readLine()) != null)
			text += line + "\n";
		
		return text;
	}
}
