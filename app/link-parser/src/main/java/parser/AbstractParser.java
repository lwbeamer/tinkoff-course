package parser;

import result.ParseResult;

public abstract class AbstractParser {

    AbstractParser nextParser;

    public AbstractParser(AbstractParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract ParseResult parseResult(String url);

    public final String tweakUrl(String url) {
        url = url.replaceAll("\\s", "");
        if (url.startsWith("https://")) {
            return url.substring(8);
        } else if (url.startsWith("http://")) {
            return url.substring(7);
        } else {
            return url;
        }
    }
}
