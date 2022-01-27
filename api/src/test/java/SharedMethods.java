import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class SharedMethods{


    public static Integer VerifyCreationOfNewUsersAndReturnsItsID() {
        Response res = given()
                .contentType("application/json")
                .when()
                .post(Constants.LIST_USERS)
                .then()
                .statusCode(201)
                .log().body().extract().response();
        System.out.println(res);
        int id = Integer.parseInt(res.jsonPath().get("id"));
        System.out.println(id);
        return id;
    }


    public void GetBaseURL() {

        RestAssured.baseURI = Constants.BASE_URL;
    }

    public void CheckListUsersHasInputParameter(String Corrine) {

        given().when().get(Constants.LIST_USERS).then()
                .statusCode(200)
                .body("name", hasItems(Corrine));
    }

    public void GetSpecificUserAndVerifyName(Integer userID ,String name) {

        given().when().get(Constants.LIST_USERS + "/" + userID).then()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    public void GetMissingUser(Integer userID) {
        String res = given().when().get(Constants.LIST_USERS + "/" + userID).then()
                .statusCode(404)
                .extract()
                .asString();

        Assert.assertEquals("Not found",res.replace("\"", ""));
    }

    public void VerifyUpdateUserWithNewName(Integer userID, String name) {
        JSONObject request = new JSONObject();
        request.put("name", name);

        given()
                .contentType("application/json")
                .body(request.toString())
                .when()
                .put(Constants.LIST_USERS + "/" + userID)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));

        given()
                .when()
                .get(Constants.LIST_USERS + "/" + userID)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    public void CheckStatusCodeOnAlreadyDeletedUser(Integer userID) {

        given().when().delete(Constants.LIST_USERS + "/" + userID).then()
                .statusCode(404);
    }

    public void CheckPageLimitIsEqualToBodySize(int pageLimit) {
        given().when().get(Constants.LIST_USERS + "/?page=1&limit=" + pageLimit).then()
                .statusCode(200)
                .body("size()", is(pageLimit));
    }

    @SuppressWarnings("rawtypes")
    public String GetFirstValueOnSortAndOrder(String sortedBy, String order) {

        Response res = given().when().get(Constants.LIST_USERS + "/?sortBy=" + sortedBy + "&order=" + order).then()
                .statusCode(200).log().body().extract().response();

        ArrayList sortedByValueArrList = res.jsonPath().get(sortedBy);

        return (String) sortedByValueArrList.get(0);
    }

}