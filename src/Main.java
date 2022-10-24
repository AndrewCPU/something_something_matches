import java.io.File;
import java.util.*;

public class Main {
	public static void main(String[] args) throws Exception{
		LinkedList<Vulnerability> vulnerabilities = SheetLoader.loadData(new File("data.csv"));
		Vulnerability vulnerability = Vulnerability.fromRow(new String[]{
				"Apple",
				"",
				"1",
				"93.0",
				"Team A"
		});
		List<Vulnerability> matches = findBestMatch(vulnerabilities, vulnerability);
		System.out.println(matches.size());
		matches.forEach(System.out::println);
	}

	/**
	 *
	 * @param vulnerabilities The list of vulnerabilities and teams to search through
	 * @param matchFor The vulnerability you would like to find the closest match to
	 * @return A list of the best matches (only more than one if multiple vulnerabilities have the same amount of similar values)
	 *         OR
	 *         Will return NULL if there are no valid matches.
	 */

	public static List<Vulnerability> findBestMatch(LinkedList<Vulnerability> vulnerabilities, Vulnerability matchFor) {
		HashMap<Integer, List<Vulnerability>> matches = new HashMap<>();
		for(Vulnerability vulnerability : vulnerabilities) {
			int similarity = vulnerability.calculateSimilarity(matchFor);
			if(!matches.containsKey(similarity)) {
				matches.put(similarity,new ArrayList<>());
			}
			matches.get(similarity).add(vulnerability);
		}
		Optional<Integer> smallestKey = matches.keySet().stream().sorted(Collections.reverseOrder()).findFirst();
		if(smallestKey.isPresent()) {
			return matches.get(smallestKey.get());
		}
		return null;
	}
}
