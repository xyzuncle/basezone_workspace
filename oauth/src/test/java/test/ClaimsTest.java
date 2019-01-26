/*
package test;

import com.kq.auth.OuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.post;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OuthApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClaimsTest {

  */
/*  @Autowired
    private JwtTokenStore tokenStore;
*//*

    @Test
    public void whenTokenDontContainIssuer_thenSuccess() {
        final String tokenValue = obtainAccessToken("fooClientIdPassword", "john", "123");
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        System.out.println(tokenValue);
        System.out.println(auth);
        assertTrue(auth.isAuthenticated());
        System.out.println(auth.getDetails());

        Map<String, Object> details = (Map<String, Object>) auth.getDetails();
        assertTrue(details.containsKey("organization"));
        System.out.println(details.get("organization"));
    }

    private String obtainAccessToken(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(clientId, "secret")
                .and()
                .with()
                .params(params)
                .when()
                .post("http://localhost:8086/oauth/token");
        return response.jsonPath()
                .getString("access_token");
    }

}
*/
