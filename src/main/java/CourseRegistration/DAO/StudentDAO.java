package CourseRegistration.DAO;

import CourseRegistration.POJO.Student;
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

public class StudentDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/students";

    public static Student getStudentByID(String studentID) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Student> response = restTemplate.exchange(url + "/id/{studentID}", GET, headers, Student.class, studentID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getStudentByID(studentID);
        }
        return response.getBody();
    }

    public static Student getStudentByUsername(String username) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Student> response = restTemplate.exchange(url + "/name/{username}", GET, headers, Student.class, username);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getStudentByUsername(username);
        }
        return response.getBody();
    }

    public static boolean removeStudentByID(String studentID) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete/{studentID}"
                , POST, headers, Boolean.class, studentID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeStudentByID(studentID);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addStudent(Student st) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Student> vars = new HttpEntity<>(st, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addStudent(st);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static void updateStudent(Student st) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Student> vars = new HttpEntity<>(st, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url + "/update", POST, vars, Void.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            updateStudent(st);
        }
    }

    public static List<Student> getStudentListByClass(String classId) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<List<Student>> response = restTemplate
                .exchange(url + "/classid/{classId}", HttpMethod.GET, headers, new ParameterizedTypeReference<>() {
                },classId);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getStudentListByClass(classId);
        }
        return response.getBody();
    }
}
