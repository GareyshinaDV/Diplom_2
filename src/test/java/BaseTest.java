import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    public String testEmail = "agatha@gmail.com";
    public String testPassword = "agatha";
    public String testName = "Агата";

    public String testEmailSecond = "agatha_simple@gmail.com";

    public String testPasswordSecond = "agatha_simple";

    public String testNameSecond = "Агата Простая";

    public String testEmailAlreadyExists = "agatha_exists@gmail.com";

    public String testPasswordAlreadyExists = "agatha_exists";

    public String testNameAlreadyExists = "Агата Простая";

    public String hashBun = "61c0c5a71d1f82001bdaaa6d";
    public String hashSouse = "61c0c5a71d1f82001bdaaa75";
    public String hashFilling = "61c0c5a71d1f82001bdaaa6e";
    public String hashIncorrect = "8888861c0c5a71d1f82001bdaaa6e";




}