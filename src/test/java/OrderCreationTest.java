import Api.OrderApi;
import Api.UserApi;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderCreationTest extends BaseTest {

    private String token;

    @Before
    public void setUpOrderTest() {
        UserApi user = new UserApi(testEmail, testPassword, testName);
        user.creationOfUser(user);
        token = user.authorizationOfUser(user).as(UserData.class).getAccessToken();
    }

    @Test
    @DisplayName("Проверка создания нового заказа с ингредиентами авторизованным пользователем")
    @Description("Тест проверяет, что авторизованный пользователь может разместить заказ")
    public void creationNewOrderWithAuthTest() {

        ArrayList<String> ingredients = new ArrayList<>();;
        ingredients.add(hashBun);
        ingredients.add(hashSouse);
        ingredients.add(hashFilling);
        OrderApi order = new OrderApi(ingredients);
        Response response = order.creationOfOrderWithAuth(order, token);
                 response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка создания нового заказа с ингредиентами неавторизованным пользователем")
    @Description("Тест проверяет, что авторизованный пользователь может разместить заказ")
    public void creationNewOrderWithoutAuthTest() {

        ArrayList<String> ingredients = new ArrayList<>();;
        ingredients.add(hashBun);
        ingredients.add(hashSouse);
        ingredients.add(hashFilling);
        OrderApi order = new OrderApi(ingredients);
        Response response = order.creationOfOrderWithoutAuth(order);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка невозможности создания нового заказа без ингредиентов авторизованным пользователем")
    @Description("Тест проверяет, что авторизованный пользователь не может разместить заказ без ингредиентов")
    public void creationNewOrderWithoutIngredientsTest() {

        ArrayList<String> ingredients = new ArrayList<>();;
        OrderApi order = new OrderApi(ingredients);
        Response response = order.creationOfOrderWithAuth(order, token);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Проверка невозможности создания заказа с неверным хешем игредиента")
    @Description("Тест проверяет, что нельзя разместить заказ с ингредиентом с неверных хешем")
    public void creationNewOrderWithWrongHashTest() {

        ArrayList<String> ingredients = new ArrayList<>();;
        ingredients.add(hashIncorrect);
        OrderApi order = new OrderApi(ingredients);
        Response response = order.creationOfOrderWithAuth(order, token);
        response.then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void deleteUserAfterTest() {
        UserApi.deletingOfUser(token);
    }

}
