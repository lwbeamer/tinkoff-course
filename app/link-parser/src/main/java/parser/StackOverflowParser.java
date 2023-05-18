package parser;

import java.net.URL;
import result.ParseResult;
import result.StackOverflowParseResult;

public class StackOverflowParser extends AbstractParser {
    public StackOverflowParser(AbstractParser nextParser) {
        super(nextParser);
    }

    @Override
    public ParseResult parseResult(URL url) {
        if (url.getHost().equals("stackoverflow.com")) {
            String[] tokens = url.getFile().substring(1).split("/");
            if (tokens.length >= 2 && tokens[0].equals("questions")) {
                try {
                    return new StackOverflowParseResult(Long.parseLong(tokens[1]));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }

        if (nextParser != null) {
            return nextParser.parseResult(url);
        }

        return null;
    }
}
