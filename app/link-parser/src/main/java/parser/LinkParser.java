package parser;

import parser.AbstractParser;
import parser.GitHubParser;
import parser.StackOverflowParser;
import result.ParseResult;

public class LinkParser {
    public ParseResult parseUrl(String url) {
        AbstractParser parser1 = new GitHubParser(null);
        AbstractParser parser2 = new StackOverflowParser(parser1);

        return parser2.parseResult(url);
    }

}
