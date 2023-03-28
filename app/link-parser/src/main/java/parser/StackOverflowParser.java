package parser;

import result.ParseResult;
import result.StackOverflowParseResult;

import java.net.URL;

public class StackOverflowParser extends AbstractParser {
    public StackOverflowParser(AbstractParser nextParser) {
        super(nextParser);
    }

    @Override
    public ParseResult parseResult(String url) {
        URL toParse = tweakUrl(url);
        if (toParse == null) return null;


        if (toParse.getHost().equals("stackoverflow.com")) {
            String[] tokens = toParse.getFile().substring(1).split("/");
            if (tokens.length >= 2 && tokens[0].equals("questions")) {
                try {
                    return new StackOverflowParseResult(Long.parseLong(tokens[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect question ID");
                    return null;
                }
            } else return null;
        }

        if (nextParser != null) return nextParser.parseResult(url);

        return null;
    }
}
