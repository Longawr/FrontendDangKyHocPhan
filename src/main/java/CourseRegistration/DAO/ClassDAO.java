package CourseRegistration.DAO;

import CourseRegistration.POJO.Classname;
import CourseRegistration.POJO.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

public class ClassDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/classes";

    public static List<Classname> getClassList() {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Classname>> response = restTemplate
                .exchange(url, HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                });
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getClassList();
        }
        return response.getBody();
    }

    public static boolean removeClassByID(String id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete/{id}", POST, headers, Boolean.class, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeClassByID(id);        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addClass(Classname cls) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Classname> vars = new HttpEntity<>(cls, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addClass(cls);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static Long getClassMemberCount(String id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Long> response = restTemplate.exchange(url + "/total/{id}", HttpMethod.GET, headers, Long.class, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getClassMemberCount(id);
        }
        return response.getBody();
    }

    public static Long getClassMaleCount(String id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Long> response = restTemplate.exchange(url + "/male/{id}", HttpMethod.GET, headers, Long.class, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getClassMaleCount(id);
        }
        return response.getBody();
    }

    public static Long getClassFemaleCount(String id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Long> response = restTemplate.exchange(url + "/female/{id}", HttpMethod.GET, headers, Long.class, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getClassFemaleCount(id);
        }
        return response.getBody();

    }

    public static void updateClass(Classname currentClass) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Classname> vars = new HttpEntity<>(currentClass, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url + "/update", HttpMethod.POST, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            updateClass(currentClass);
        }
    }

}
