package CourseRegistration.DAO;

import CourseRegistration.POJO.Semester;
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

public class SemesterDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/semesters";

    public static List<Semester> getSemesterList() {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Semester>> response = restTemplate
                .exchange(url, HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                });
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getSemesterList();
        }
        return response.getBody();
    }

    public static Semester getSemesterByID(int year, int id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Semester> response = restTemplate.exchange(url + "/id?year={year}&id={id}", GET, headers, Semester.class, year, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getSemesterByID(year, id);
        }
        return response.getBody();
    }

    public static boolean removeSemesterByID(int year, int id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete?year={year}&id={id}", POST, headers, boolean.class, year, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeSemesterByID(year, id);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addSemester(Semester sem) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Semester> vars = new HttpEntity<>(sem, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addSemester(sem);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

}
