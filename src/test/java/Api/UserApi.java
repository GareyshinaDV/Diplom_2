package Api;

import data.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserApi extends User {

    private static String REGISTER_ENDPOINT = "api/auth/register";
    private static String LOGIN_ENDPOINT = "/api/auth/login";
    private static String USER_ENDPOINT = "/api/auth/user";

    public UserApi(String email, String password, String name) {
        super(email, password, name);
    }

    public UserApi(String email, String password) {
        super(email, password);
    }

    @Step("Создание учетной записи пользователя")
    public static Response creationOfUser(UserApi user) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT);
        return response;
    }

    @Step("Авторизация пользователем")
    public static Response authorizationOfUser(UserApi user) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(LOGIN_ENDPOINT);
        return response;
    }

    @Step("Обновление данных пользователя с указанием токена пользователя")
    public static Response updatingDataOfUserWithToken(UserApi user, String token) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(USER_ENDPOINT);
        return response;
    }


    @Step("Обновление данных пользователя без указания токена пользователя")
    public static Response updatingDataOfUserWithoutToken(UserApi user) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .patch(USER_ENDPOINT);
        return response;
    }
    @Step("Удаление пользователя")
    public static Response deletingOfUser(String token) {
        Response response = given().log().all()
                .header("Authorization", token)
                .when()
                .delete(USER_ENDPOINT);
        return response;
    }
}
