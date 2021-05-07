package pass;

import java.nio.file.Path;
import java.net.URI;
import java.nio.file.Paths;

public class ForEachStatementIterable {
    public void forEachStatementAnalyze() {
        Path p = Paths.get("Something");
        int i = 0;
        for(Path r : p) {
            i += 1;
        }
    }
}
