package CourseRegistration.DAO;

import CourseRegistration.POJO.Subject;
import CourseRegistration.POJO.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class SubjectDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/subjects";


    public static List<Subject> getSubjectList() {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Subject>> response = restTemplate
                .exchange(url, HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                });
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getSubjectList();
        }
        return response.getBody();
    }

    public static Subject getSubjectByID(String subjectID) {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Subject> response = restTemplate.exchange(url + "/id/{subjectID}", GET, headers, Subject.class, subjectID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getSubjectByID(subjectID);
        }
        return response.getBody();
    }

    public static boolean removeSubjectByID(String subjectID) {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete/{subjectID}"
                , POST, headers, Boolean.class, subjectID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeSubjectByID(subjectID);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addSubject(Subject sub) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Subject> vars = new HttpEntity<>(sub, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addSubject(sub);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static void updateSubject(Subject sub) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Subject> vars = new HttpEntity<>(sub, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            updateSubject(sub);
        }
    }

}
