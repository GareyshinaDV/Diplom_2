package Api;

import data.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class OrderApi extends Order {

    private static String ORDER_ENDPOINT = "api/orders";

    public OrderApi(ArrayList<String> ingredients) {
        super(ingredients);
    }

    @Step("Создание заказа с авторизацией")
    public static Response creationOfOrderWithAuth(OrderApi order, String token){
        Response response = given().log().all()
                .header("Authorization", token)
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_ENDPOINT);
        return response;
    }

    @Step("Создание заказа без авторизации")
    public static Response creationOfOrderWithoutAuth(OrderApi order){
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_ENDPOINT);
        return response;
    }

    @Step("Получение списка заказов авторизованным пользователем")
    public static Response getOrderWithAuth(String token){
        Response response = given().log().all()
                .header("Authorization", token)
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_ENDPOINT);
        return response;
    }

    @Step("Получение списка заказов неавторизованным пользователем")
    public static Response getOrderWithoutAuth(){
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_ENDPOINT);
        return response;
    }
}
