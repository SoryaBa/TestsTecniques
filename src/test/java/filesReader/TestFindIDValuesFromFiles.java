package filesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import junit.framework.TestCase;
import reader.FindIDValuesFromFiles;

public class TestFindIDValuesFromFiles extends TestCase {
	private FindIDValuesFromFiles reader;

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public TestFindIDValuesFromFiles(String testName) {
		super(testName);
		this.reader = new FindIDValuesFromFiles();
	}

	/**
	 * Donne un resultat trié dans ordre decroisant avec un repertoire avec les
	 * elements attendus
	 * 
	 */
	public void testFindFromDirWith2DifferentIdValues() {
		Path path = Paths.get("src", "test", "resources", "logs", "Petit");
		Map<String, Integer> result = reader.findFromDir(path.toString());

		Boolean expectedResult = result.containsKey("690") && result.get("690") == 5 
				&& result.containsKey("835") && result.get("835") == 4 
				&& result.containsKey("43") && result.get("43") == 3
				&& result.containsKey("446") && result.get("446") == 2 
				&& result.containsKey("156") && result.get("156") == 1 
				&& result.size() == 5;
		
		assertTrue("Doit contenir le resultat attendu ", expectedResult);

		Boolean isSorted = true;
		Integer last = Integer.MAX_VALUE;
		for (var entry : result.entrySet()) {
			isSorted = last >= entry.getValue();
			if (!isSorted) {
				break;
			}
		}
		assertTrue("Les elements resultat doivent etre trier dans l'odre decroissant ", isSorted);
	}

	/**
	 * Avec des URLs/lignes ne respectant pas format d'une URL: sans le parametre id
	 * et ou un id sans la valeur exple :"http://a.com?id=" avec deux parametres id
	 * dans la meme URL : Verifie que nous ne trouvons pas la seconde valeure
	 * 
	 */
	public void testFindFromDirWithBadFormatURLs() {
		Path path = Paths.get("src", "test", "resources", "logs", "MauvaisFormat");
		Map<String, Integer> result = reader.findFromDir(path.toString());

		Boolean expectedResult = result.containsKey("197") && result.get("197") == 2
				&& result.containsKey("628") && result.get("628") == 2
				&& result.containsKey("308") && result.get("308") == 3
				&& result.containsKey("404") && result.get("404") == 1 
				&& result.size() == 4;

		assertTrue("Doit contenir le resultat attendu ", expectedResult);

		assertFalse("Une URL ne respectant pas le format ", result.containsKey("111"));
		assertFalse("Une URL avec 2 params id, verfions que noue ne trouvons que la premiere valeur ",
				result.containsKey("222"));
	}

	/**
	 * Donne un resultat trié dans ordre decroisance avec un fichier .zip
	 */
	public void testFindFromDir() {
		Path path = Paths.get("src", "test", "resources", "logs", "logs.zip");
		Map<String, Integer> result = reader.findFromZipFile(path.toString());

		assertTrue("Le nombre d'elements retournés doit etre superieur à 0 ", result.size() > 0);

		assertEquals("Le nombre d'elements retournés doit etre egale à 5 ", result.size(), 5);

		Boolean isSorted = true;
		Integer last = Integer.MAX_VALUE;
		for (var entry : result.entrySet()) {
			isSorted = last >= entry.getValue();
			if (!isSorted) {
				break;
			}
		}
		assertTrue("Les elements resultat doivent etre trier dans l'odre decroissant ", isSorted);
	}

}
