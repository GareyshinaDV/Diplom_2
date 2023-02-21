import api.OrderApi;
import data.Data;
import data.ListOfIngredients;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import java.util.ArrayList;


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

    @Before
    public void  getIngredientsHash(){

        Response response = OrderApi.getIngredients();
        ListOfIngredients list = response.as(ListOfIngredients.class);
        ArrayList<Data> ingredients = list.getIngredients();
        Data ingredientOne = ingredients.get(0);
        hashIngredientOne = ingredientOne.get_id();
        Data ingredientTwo = ingredients.get(1);
        hashIngredientTwo = ingredientTwo.get_id();
        Data ingredientThree = ingredients.get(2);
        hashIngredientThree = ingredientThree.get_id();
            }

    public String  hashIngredientOne;
    public String  hashIngredientTwo;
    public String  hashIngredientThree;

    public String hashIncorrect = "8888861c0c5a71d1f82001bdaaa6e";

}