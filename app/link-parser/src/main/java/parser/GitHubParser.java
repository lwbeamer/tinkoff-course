package parser;

import result.GithubParseResult;
import result.ParseResult;

import java.util.Arrays;

public class GitHubParser extends AbstractParser {
    public GitHubParser(AbstractParser nextParser) {
        super(nextParser);
    }

    @Override
    public ParseResult parseResult(String url) {
        if (url == null) return null;
        String toParse = tweakUrl(url);
        String[] tokens = toParse.split("/");

        System.out.println(Arrays.toString(tokens));

        if (tokens.length >= 1 && tokens[0].equals("github.com")) {
            if (tokens.length >= 3) {
                return new GithubParseResult(tokens[1], tokens[2]);
            } else return null;
        }


        if (nextParser != null) return nextParser.parseResult(url);

        return null;
    }
}
