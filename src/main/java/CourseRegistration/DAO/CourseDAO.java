package CourseRegistration.DAO;

import CourseRegistration.POJO.Course;
import CourseRegistration.POJO.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class CourseDAO implements Serializable {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/courses";


    public static List<Course> getCourseListBySemester(int id, int year) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Course>> response = restTemplate
                .exchange(url + "/semester?id={id}&year={year}", HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                }, id, year);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseListBySemester(id, year);
        }
        return response.getBody();
    }

    public static Integer getCourseStudentCount(int id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Integer> response = restTemplate.exchange(url + "/studentcount/{id}", GET, headers, Integer.class, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseStudentCount(id);
        }
        return response.getBody();
    }

    public static boolean removeCourseByID(int id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete/{id}", POST, headers, Boolean.class, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeCourseByID(id);        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static void addCourse(Course temp) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Course> vars = new HttpEntity<>(temp, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url + "/save", POST, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addCourse(temp);
        }
    }

    public static void updateCourse(Course temp) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Course> vars = new HttpEntity<>(temp, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url + "/update", POST, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            updateCourse(temp);
        }
    }

    public static Course getUniqueCourseBySubject(String subjectId) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Course> response = restTemplate.exchange(url + "/subjectid/{subjectId}", GET, headers, Course.class, subjectId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getUniqueCourseBySubject(subjectId);
        }
        return response.getBody();
    }

}
