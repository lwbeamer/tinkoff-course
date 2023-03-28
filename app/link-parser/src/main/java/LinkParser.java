import parser.AbstractParser;
import parser.GitHubParser;
import parser.StackOverflowParser;
import result.ParseResult;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class LinkParser {
    public ParseResult parseUrl(String url) throws MalformedURLException, URISyntaxException {
        AbstractParser parser1 = new GitHubParser(null);
        AbstractParser parser2 = new StackOverflowParser(parser1);

        return parser2.parseResult(url);
    }

}
