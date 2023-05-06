package parser;

import java.net.URL;
import result.GithubParseResult;
import result.ParseResult;

public class GitHubParser extends AbstractParser {
    public GitHubParser(AbstractParser nextParser) {
        super(nextParser);
    }

    @Override
    public ParseResult parseResult(URL url) {

        if (url.getHost().equals("github.com")) {
            String[] tokens = url.getFile().substring(1).split("/");
            if (tokens.length >= 2) {
                return new GithubParseResult(tokens[0], tokens[1]);
            }
        }

        if (nextParser != null) {
            return nextParser.parseResult(url);
        }

        return null;
    }
}
