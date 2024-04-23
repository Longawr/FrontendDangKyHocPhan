package CourseRegistration.DAO;

import CourseRegistration.POJO.Courseattend;
import CourseRegistration.POJO.Currentsemester;
import CourseRegistration.POJO.Semester;
import CourseRegistration.POJO.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class CourseattendDAO implements Serializable {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/courseattends";

    public static List<Courseattend> getCourseattendList(String studentId, Semester sem) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Semester> vars = new HttpEntity<>(sem, headers);
        ResponseEntity<List<Courseattend>> response = restTemplate
                .exchange(url + "/get/{studentId}", POST
                , vars, new ParameterizedTypeReference<>() {}, studentId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseattendList(studentId, sem);
        }
        return response.getBody();
    }

    public static Integer getCourseattendListCount(String studentId, Currentsemester sem) {
        HttpEntity<Currentsemester> vars = new HttpEntity<>(sem, TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Integer> response = restTemplate.exchange(url + "/countbysemester/{studentId}", GET
                , vars, Integer.class, studentId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseattendListCount(studentId, sem);
        }
        return response.getBody();
    }

    public static Integer getCourseattendListCountByStudent(String studentId) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Integer> response = restTemplate.exchange(url + "/countall/studentid/{studentId}", GET, headers, Integer.class, studentId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseattendListCountByStudent(studentId);        }
        return response.getBody();
    }
    public static Courseattend getCourseattendByID(String studentId, int courseId) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Courseattend> response = restTemplate.exchange(url + "/id/{studentId}/{courseId}"
                , GET, headers, Courseattend.class, studentId, courseId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseattendByID(studentId, courseId);
        }
        return response.getBody();
    }
    public static List<Courseattend> getCourseattendByCourseID(int courseId) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Courseattend>> response = restTemplate
                .exchange(url + "/courseid/{courseId}", GET, headers, new ParameterizedTypeReference<>() {}, courseId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getCourseattendByCourseID(courseId);
        }
        return response.getBody();
    }
    public static void removeCourseattendByID(String studentId, int id) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Void> response = restTemplate.exchange(url + "/delete/{studentId}/{id}"
                , POST, headers, void.class, studentId, id);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeCourseattendByID(studentId, id);
        }
    }

    public static void addCourseattend(Courseattend courseattend) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Courseattend> vars = new HttpEntity<>(courseattend, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url + "/save", POST, vars, void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addCourseattend(courseattend);
        }
    }

}
