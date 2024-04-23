package CourseRegistration.DAO;

import CourseRegistration.POJO.Teacher;
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
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class TeacherDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/teachers";

    public static List<Teacher> getTeacherList() {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Teacher>> response = restTemplate
                .exchange(url, HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                });
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getTeacherList();
        }
        return response.getBody();
    }

    public static Teacher getTeacherByID(String teacherID) {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Teacher> response = restTemplate.exchange(url + "/id/{teacherID}", GET, headers, Teacher.class, teacherID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getTeacherByID(teacherID);
        }
        return response.getBody();
    }

    public static boolean removeTeacherByID(String teacherID) {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete/{teacherID}"
                , POST, headers, Boolean.class, teacherID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeTeacherByID(teacherID);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addTeacher(Teacher tch) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Teacher> vars = new HttpEntity<>(tch, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addTeacher(tch);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static void updateTeacher(Teacher tch) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Teacher> vars = new HttpEntity<>(tch, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            updateTeacher(tch);
        }
    }

    public static Teacher getTeacherByUsername(String username) {
        HttpEntity< String > headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Teacher> response = restTemplate.exchange(url + "/username/{username}", GET, headers, Teacher.class, username);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getTeacherByUsername(username);
        }
        return response.getBody();
    }

}
