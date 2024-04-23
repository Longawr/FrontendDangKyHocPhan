package CourseRegistration.DAO;

import CourseRegistration.Main.App;
import CourseRegistration.POJO.Token;
import CourseRegistration.POJO.TokenPOJO;
import javafx.scene.control.Alert;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;


import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;


public class TokenDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/token";


    public static boolean checkToken(HttpStatus responseStatus){
        try {
            if (responseStatus == UNAUTHORIZED) {
                if (refreshToken()) {
                    return true;
                } else {
                    Alert loginSessionTimeout = new Alert(Alert.AlertType.WARNING);
                    loginSessionTimeout.setTitle("Login session timeout");
                    loginSessionTimeout.setContentText("Phiên đăng nhập đã hết hạn vui lòng đăng nhập lại");
                    loginSessionTimeout.setHeaderText(null);
                    loginSessionTimeout.showAndWait();
                    Thread.sleep(300);
                    App.changeScene("Login");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static HttpStatus getToken(String username, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(username));
        map.put("password", Collections.singletonList(password));
        HttpEntity<Object> vars = new HttpEntity<>(map, headers);
        ResponseEntity<TokenPOJO> response = restTemplate.exchange(url + "/login", HttpMethod.POST, vars, TokenPOJO.class);
        Token.setAccessToken(Objects.requireNonNull(response.getBody()).getAccess_token());
        Token.setRefreshToken(response.getBody().getRefresh_token());
        return response.getStatusCode();
    }

    public static boolean refreshToken(){
        HttpEntity< String > var = new HttpEntity<>(TokenDAO.setHeaders(Token.getRefreshToken()));
        ResponseEntity<TokenPOJO> response = restTemplate.exchange(url + "/refresh", HttpMethod.GET, var, TokenPOJO.class);
        Token.setAccessToken(Objects.requireNonNull(response.getBody()).getAccess_token());
        Token.setRefreshToken(Objects.requireNonNull(response.getBody().getRefresh_token()));
        return response.getStatusCode() == OK;
    }


    public static HttpHeaders setHeaders(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(
                HttpHeaders.AUTHORIZATION, "Author " + token);
        return headers;
    }

}
