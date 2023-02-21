import api.UserApi;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdatingUserDataTest extends BaseTest {

    private String token;


    @Before
    public void setUpUpdatingTest() {
       UserApi user = new UserApi(testEmail, testPassword, testName);
       user.creationOfUser(user);
       token = user.authorizationOfUser(user).as(UserData.class).getAccessToken();
    }

    @Test
    @DisplayName("Проверка обновления поля email (с авторизацией)")
    @Description("Тест проверяет корректность обновления поля email с указанием авторизации пользователя")
    public void updatingEmailWithAuthTest() {

        UserApi userChangeLoginWithAuth = new UserApi(testEmailSecond, testPassword, testName);
        Response response = userChangeLoginWithAuth.updatingDataOfUserWithToken(userChangeLoginWithAuth, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка обновления поля password (с авторизацией)")
    @Description("Тест проверяет корректность обновления поля password с указанием авторизации пользователя")
    public void updatingPasswordWithAuthTest() {

        UserApi userChangePasswordWithAuth = new UserApi(testEmail, testPasswordSecond, testName);
        Response response = userChangePasswordWithAuth.updatingDataOfUserWithToken(userChangePasswordWithAuth, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка обновления поля name (с авторизацией)")
    @Description("Тест проверяет корректность обновления поля name с указанием авторизации пользователя")
    public void updatingNameWithAuthTest() {

        UserApi userChangeNameWithAuth = new UserApi(testEmail, testPassword, testNameSecond);
        Response response = userChangeNameWithAuth.updatingDataOfUserWithToken(userChangeNameWithAuth, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка обновления поля email (без авторизации)")
    @Description("Тест проверяет, что невозможно обновить данные поля email без указания авторизации пользователя")
    public void updatingEmailWithoutAuthTest() {

        UserApi userChangeLoginWithoutAuth = new UserApi(testEmailSecond, testPassword, testName);
        Response response = userChangeLoginWithoutAuth.updatingDataOfUserWithoutToken(userChangeLoginWithoutAuth);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Проверка обновления поля password (без авторизации)")
    @Description("Тест проверяет, что невозможно обновить данные поля password без указания авторизации пользователя")
    public void updatingPasswordWithoutAuthTest() {

        UserApi userChangePasswordWithoutAuth = new UserApi(testEmailSecond, testPassword, testName);
        Response response = userChangePasswordWithoutAuth.updatingDataOfUserWithoutToken(userChangePasswordWithoutAuth);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Проверка обновления поля name (без авторизации)")
    @Description("Тест проверяет, что невозможно обновить данные поля name без указания авторизации пользователя")
    public void updatingNameWithoutAuthTest() {

        UserApi userChangeNameWithoutAuth = new UserApi(testEmailSecond, testPassword, testName);
        Response response = userChangeNameWithoutAuth.updatingDataOfUserWithoutToken(userChangeNameWithoutAuth);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Проверка обновления поля email на уже существующий (с авторизацией)")
    @Description("Тест проверяет, что невозможно обновить данные поля email на уже существующий")
    public void updatingEmailAlreadyExistWithAuthTest() {

        UserApi userAlreadyExists = new UserApi(testEmailAlreadyExists, testPasswordAlreadyExists, testNameAlreadyExists);
        userAlreadyExists.creationOfUser(userAlreadyExists);
        String tokenAlreadyExists = userAlreadyExists.authorizationOfUser(userAlreadyExists).as(UserData.class).getAccessToken();
        UserApi userForUpdateExistsEmail = new UserApi(testEmailAlreadyExists, testPassword, testName);
        Response response = userForUpdateExistsEmail.updatingDataOfUserWithToken(userForUpdateExistsEmail, token);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("User with such email already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
        UserApi.deletingOfUser(tokenAlreadyExists);
    }


    @After
    public void deleteUserAfterTest() {
            UserApi.deletingOfUser(token);
    }
}
