package CourseRegistration.DAO;

import CourseRegistration.POJO.Currentsemester;
import CourseRegistration.POJO.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.*;

public class CurrentSemesterDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/currentsemester";

    public static Currentsemester getCurrentSemester() {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Currentsemester> response = restTemplate.exchange(url, GET, headers, Currentsemester.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCurrentSemester();
        }
        return response.getBody();
    }

    public static void addCurrentSemester(Currentsemester sem) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Currentsemester> vars = new HttpEntity<>(sem, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, POST, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addCurrentSemester(sem);
        }
    }

    public static void removeCurrentSemester() {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Void> response = restTemplate.exchange(url, DELETE, headers, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeCurrentSemester();
        }
    }
}
