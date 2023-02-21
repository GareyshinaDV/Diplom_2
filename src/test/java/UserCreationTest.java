import api.UserApi;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreationTest extends BaseTest {

    private String token;

    private boolean isDeleted;

    @Test
    @DisplayName("Проверка создания нового пользователя")
    @Description("Тест проверяет корректность создания нового пользователя с валидными данными")
    public void creationNewUserTest() {

        UserApi user = new UserApi(testEmail, testPassword, testName);
        Response response = user.creationOfUser(user);
        token = user.authorizationOfUser(user).as(UserData.class).getAccessToken();
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
        this.isDeleted = true;
    }


    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых пользователей")
    @Description("Тест проверяет, что невозможно создать двух пользователей с одинаковыми данными")
    public void impossibilityOfCreationTwoEqualsUsersTest() {

        UserApi user = new UserApi(testEmail, testPassword, testName);
        user.creationOfUser(user);
        token = user.authorizationOfUser(user).as(UserData.class).getAccessToken();
        Response response = user.creationOfUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
        this.isDeleted = true;
    }

    @Test
    @DisplayName("Проверка невозможности создания пользователя без обязательного поля имя")
    @Description("Тест проверяет, что невозможно создать пользователя без обязательного поля имя")
    public void impossibilityOfCreationWithoutNameUserTest() {

        UserApi user = new UserApi(testEmail, testPassword);
        Response response = user.creationOfUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
        this.isDeleted = false;
    }


   @After
    public void deleteUserAfterTest() {
            if(isDeleted == true){
                UserApi.deletingOfUser(token);
            }
       }

    }

