import Api.OrderApi;
import Api.UserApi;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetUserOrdersTest extends BaseTest{

    private String token;

    @Before
    public void setUpGetOrdersTest() {
        UserApi user = new UserApi(testEmail, testPassword, testName);
        user.creationOfUser(user);
        token = user.authorizationOfUser(user).as(UserData.class).getAccessToken();
    }

    @Test
    @DisplayName("Проверка получения списка заказов авторизованным пользователем")
    @Description("Тест проверяет, что авторизованный пользователь может получить список своих заказов")
    public void getListOfOrdersWithAuthTest() {

        Response response = OrderApi.getOrderWithAuth(token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .body("orders",notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка получения списка заказов неавторизованным пользователем")
    @Description("Тест проверяет, что авторизованный пользователь не может получить список заказов")
    public void getListOfOrdersWithoutAuthTest() {

        Response response = OrderApi.getOrderWithoutAuth();
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteUserAfterTest() {
        UserApi.deletingOfUser(token);
    }

}
