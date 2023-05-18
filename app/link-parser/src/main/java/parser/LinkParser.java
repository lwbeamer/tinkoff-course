package parser;

import java.net.MalformedURLException;
import java.net.URL;
import result.ParseResult;

public class LinkParser {
    public ParseResult parseUrl(String url) {

        URL urlToCheck = tweakUrl(url);

        if (urlToCheck == null) {
            return null;
        }

        AbstractParser parser1 = new GitHubParser(null);
        AbstractParser parser2 = new StackOverflowParser(parser1);

        return parser2.parseResult(urlToCheck);
    }

    public final URL tweakUrl(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            return null;
        }
        return url;
    }

}
