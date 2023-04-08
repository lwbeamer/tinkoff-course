import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parser.LinkParser;
import result.GithubParseResult;
import result.StackOverflowParseResult;

public class LinkParserTest {

    static String validGitHubLink;
    static String validStackOverflowLink;
    static String gitHubNotRepoLink;
    static String stackOverflowNotQuestionLink;
    static String emptyLink;
    static String withoutProtocolLink;
    static String unknownHostLink;
    static String invalidLink;


    @BeforeAll
    static void init() {
        validGitHubLink = "https://github.com/lwbeamer/asm-like-language";
        validStackOverflowLink = "https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file";
        gitHubNotRepoLink = "https://github.com/issues";
        stackOverflowNotQuestionLink = "https://stackoverflow.co/talent";
        emptyLink = "";
        withoutProtocolLink = "github.com/lwbeamer/asm-like-language";
        unknownHostLink = "https://vk.com/feed";
        invalidLink = "somethingNotValid";
    }


    @Test
    @DisplayName("Тест для проверки валидной ссылки с GitHub")
    void checkValidGitHubLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertEquals(new GithubParseResult("lwbeamer", "asm-like-language"), parser.parseUrl(validGitHubLink), "Тест не пройден, проверьте реузультат");
    }


    @Test
    @DisplayName("Тест для проверки валидной ссылки со StackOverflow")
    void validStackOverflowLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertEquals(new StackOverflowParseResult(2336692), parser.parseUrl(validStackOverflowLink), "Тест не пройден, проверьте реузультат");
    }


    @Test
    @DisplayName("Тест для проверки ссылки с GitHub, не являющейся репозиторием")
    void gitHubNotRepoLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertNull(parser.parseUrl(gitHubNotRepoLink), "Тест не пройден, проверьте реузультат");
    }

    @Test
    @DisplayName("Тест для проверки ссылки со StackOverflow, не являющейся вопросом")
    void stackOverflowNotQuestionLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertNull(parser.parseUrl(stackOverflowNotQuestionLink), "Тест не пройден, проверьте реузультат");
    }


    @Test
    @DisplayName("Тест для проверки пустой ссылки")
    void emptyLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertNull(parser.parseUrl(emptyLink), "Тест не пройден, проверьте реузультат");
    }


    @Test
    @DisplayName("Тест для проверки ссылки без указания протокола")
    void withoutProtocolLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertNull(parser.parseUrl(withoutProtocolLink), "Тест не пройден, проверьте реузультат");
    }

    @Test
    @DisplayName("Тест для проверки ссылки, не относящейся к отслеживаемым ресурсам")
    void unknownHostLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertNull(parser.parseUrl(unknownHostLink), "Тест не пройден, проверьте реузультат");
    }


    @Test
    @DisplayName("Тест для проверки непустой невалидной ссылки")
    void invalidLink() {
        LinkParser parser = new LinkParser();
        Assertions.assertNull(parser.parseUrl(invalidLink), "Тест не пройден, проверьте реузультат");
    }


}
