package parser;

import result.ParseResult;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class AbstractParser {

    AbstractParser nextParser;

    public AbstractParser(AbstractParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract ParseResult parseResult(String url);

    public final URL tweakUrl(String urlString) {
        URL url;
        try{
            url = new URL(urlString);
        } catch (MalformedURLException e){
            System.out.println("Incorrect URL");
            return null;
        }
        return url;
    }
}
