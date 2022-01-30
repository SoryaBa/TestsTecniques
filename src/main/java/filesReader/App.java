package filesReader;

import java.util.Map;

import reader.FindIDValuesFromFiles;

public class App {

	public static void main(String[] args) {		
		
		String repName = args[0];
		
		//String repName = "C:\\Users\\Dell\\Downloads\\logs.zip";
		
		FindIDValuesFromFiles fd = new FindIDValuesFromFiles();
		Map<String, Integer> result = fd.findFromZipFile(repName);
		
		if(result !=null) {
			result.forEach((k, v) -> System.out.println ("id=>"+k+", fréquence=>"+v));
		}
	}

}
