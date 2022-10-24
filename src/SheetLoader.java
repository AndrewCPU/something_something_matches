import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SheetLoader {
	public static LinkedList<Vulnerability> loadData(File file) throws Exception {
		List<String> lines = Files.readAllLines(file.toPath());
		LinkedList<Vulnerability> vulnerabilities = lines.stream().map(line -> line.split(",")).map(Vulnerability::fromRow).collect(Collectors.toCollection(LinkedList::new));
		return vulnerabilities;
	}
}
