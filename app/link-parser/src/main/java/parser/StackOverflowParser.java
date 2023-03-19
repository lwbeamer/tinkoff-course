package parser;

import result.ParseResult;
import result.StackOverflowParseResult;

public class StackOverflowParser extends AbstractParser {
    public StackOverflowParser(AbstractParser nextParser) {
        super(nextParser);
    }

    @Override
    public ParseResult parseResult(String url) {

        if (url == null) return null;
        String toParse = tweakUrl(url);
        String[] tokens = toParse.split("/");

        if (tokens.length >= 1 && tokens[0].equals("stackoverflow.com")) {
            if (tokens.length >= 2 && tokens[1].equals("questions")) {
                if (tokens.length >= 3) {
                    try {
                        return new StackOverflowParseResult(Long.parseLong(tokens[2]));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            } else return null;
        }

        if (nextParser != null) return nextParser.parseResult(url);

        return null;
    }
}
