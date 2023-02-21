import api.UserApi;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class AuthTest extends BaseTest{
    private String token;

    private boolean isDeleted;

    @Test
    @DisplayName("Проверка авторизации с корректным логином и паролем")
    @Description("Тест проверяет позитивный сценарий авторизации пользователя с корректной парой логин-пароль")
    public void authPositiveTest() {

        UserApi user = new UserApi(testEmail, testPassword, testName);
        user.creationOfUser(user);
        UserApi userLogin = new UserApi(testEmail, testPassword);
        Response response = userLogin.authorizationOfUser(userLogin);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
        token = response.as(UserData.class).getAccessToken();
        this.isDeleted = true;
    }

    @Test
    @DisplayName("Проверка невозможности авторизации с несуществующим логином и паролем")
    @Description("Тест проверяет корректность возврата ответа в случае отсутствия пары логин-пароль (несуществующий пользователь)")
    public void authNegativeTest() {

        UserApi userLogin = new UserApi(testEmailSecond, testPasswordSecond);
        Response response = userLogin.authorizationOfUser(userLogin);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
        this.isDeleted = false;
    }

    @After
    public void deleteUserAfterTest() {
        if(isDeleted == true){
            UserApi.deletingOfUser(token);
        }
    }

}
