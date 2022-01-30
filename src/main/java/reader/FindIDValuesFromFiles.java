package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Compte le nombre de fois la valeur d'un parametre id est repeté dans des URLs
 * contenus dans des fichiers d'un repertoire ou d'un fichier .Zip donné et
 * renvoie les valeurs des 5 id les plus utilisés dans les URL presentes dans
 * les differents fichiers du repertoireou .Zip.
 * 
 * @author Dell
 *
 */
public class FindIDValuesFromFiles {
	/**
	 * Pour stocker l'ensembles des valeurs presentes du parametre id et le nombre
	 * de fois chacune d'elles apparait
	 */
	private Map<String, Integer> mapIdCount;
	/**
	 * Le nombre maximal d'elements que le resultat doit contenir
	 */
	private static int NBR = 5;

	public FindIDValuesFromFiles() {
		this.mapIdCount = new HashMap<String, Integer>();
	}

	/**
	 * Renvoie une collection de type Map contenant les valeurs du parametre "id"
	 * les plus repetées et le nombre de fois ces valeurs apparaissent dans les
	 * fichiers du repertoire.
	 * 
	 * @param dirName nom du repertoire où se trouve les fichiers à lire
	 * 
	 * @return la Map des 5 premieres valeurs du parametre id les plus présentes et
	 *         nombre de fois elles sont présentes
	 */
	public Map<String, Integer> findFromDir(String dirName) {

		Path dirPath = Paths.get(dirName);

		DirectoryStream.Filter<Path> filtre = new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path element) throws IOException {
				return Files.isRegularFile(element) & Files.isReadable(element);
			}
		};

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, filtre)) {

			for (Path entry : stream) {

				try (Stream<String> lines = Files.lines(entry)) {
					findFromFile(lines);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sortMapWithLimit();
	}

	/**
	 * A partir d'un fichier Zip, renvoie une collection de type Map contenant les
	 * valeurs du parametre "id" les plus repetées et le nombre de fois ces valeurs
	 * apparaissent dans les fichiers du Zip.
	 * 
	 * @param zipName nom du fichier .zip où se trouve les fichier à lire
	 * 
	 * @return la Map des 5 premieres valeurs les plus presentes et nombre de fois
	 *         elles apparaissent.
	 */
	public Map<String, Integer> findFromZipFile(String zipName) {

		try (final ZipFile zipFile = new ZipFile(zipName)) {

			final Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader(zipFile.getInputStream(entry)));

				try (Stream<String> lines = br.lines()) {
					findFromFile(lines);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return sortMapWithLimit();
	}

	/**
	 * lit et compte le nombre de fois la valeur de chaque parametre id apparait
	 * dans le fichier dont les lignes sont passées en parametre
	 * 
	 * @param lines les lignes du fichier à lire
	 */
	private void findFromFile(Stream<String> lines) {
		Matcher m = Pattern.compile("id=(\\d+)").matcher("");
		lines.filter(line -> m.reset(line)
				.find()).map(line -> m.group()
						.replace("id=", ""))
						.forEach(id -> {
							if (mapIdCount.containsKey(id)) {
								mapIdCount.put(id, mapIdCount.get(id) + 1);
							} else {
								mapIdCount.put(id, 1);
							}
						});
	}

	/**
	 * Prepare la map resultat
	 * 
	 * @return La map resultat
	 */
	private Map<String, Integer> sortMapWithLimit() {
		return mapIdCount.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(NBR)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(e1, e2) -> e1, LinkedHashMap::new));
	}

}
