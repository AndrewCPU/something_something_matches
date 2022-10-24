import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Exception{

		File inputFile = new File(Arrays.stream(args).collect(Collectors.joining(" ")));
		LinkedList<Vulnerability> teamData = SheetLoader.loadData(new File("data.csv"));
		LinkedList<Vulnerability> inputData = SheetLoader.loadData(inputFile);

		for(Vulnerability input : inputData) {
			System.out.println("Scanning for matches to " + input.toString());
			List<Vulnerability> matches = findBestMatch(teamData, input);
			System.out.println("Found " + matches.size() + " match(es)");
			if(matches.size() == 1){
				System.out.println("SEND TO " + matches.get(0).getTeam());
			}
			else{
				System.out.println("MULTIPLE MATCHES FOUND, COULD BE: " + matches.stream().map(Vulnerability::getTeam).collect(Collectors.joining(", ")));
			}
			System.out.println();
		}
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
