package parser;

import java.net.URL;
import result.ParseResult;

public abstract class AbstractParser {

    AbstractParser nextParser;

    public AbstractParser(AbstractParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract ParseResult parseResult(URL url);
}
