package CourseRegistration.DAO;


import CourseRegistration.POJO.CourseRegistration;
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

public class CourseRegistrationDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/courseregistrations";


    public static List<CourseRegistration> getCourseRegistrationList() {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<CourseRegistration>> response = restTemplate
                .exchange(url, HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                });
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseRegistrationList();
        }
        return response.getBody();
    }

    public static CourseRegistration getCourseRegistrationByID(int id, int semesterId, int year) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<CourseRegistration> response = restTemplate.exchange(url + "/id?id={id}&semesterId={semesterId}&year={year}"
                , GET, headers, CourseRegistration.class,id, semesterId, year);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseRegistrationByID(id, semesterId, year);
        }
        return response.getBody();
    }
    public static CourseRegistration getCourseRegistrationBySemester(int semesterId, int year) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<CourseRegistration> response = restTemplate.exchange(url + "/semester?semesterId={semesterId}&year={year}"
                , GET, headers, CourseRegistration.class, semesterId, year);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseRegistrationBySemester(semesterId, year);
        }
        return response.getBody();
    }
    public static boolean removeCourseRegistrationByID(int id, int semesterId, int year) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete?id={id}&semesterId={semesterId}&year={year}"
                , POST, headers, Boolean.class, id, semesterId, year);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeCourseRegistrationByID(id, semesterId, year);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addCourseRegistration(CourseRegistration temp) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<CourseRegistration> vars = new HttpEntity<>(temp, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addCourseRegistration(temp);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

}
