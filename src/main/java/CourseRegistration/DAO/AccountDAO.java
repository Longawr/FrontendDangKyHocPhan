package CourseRegistration.DAO;

import CourseRegistration.POJO.Account;
import CourseRegistration.POJO.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


public class AccountDAO {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://localhost:8080/accounts";


    public static Account getAccountByID(String username){
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Account> response = restTemplate.exchange(url + "/id/{username}", GET, headers, Account.class, username);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            getAccountByID(username);
        }
        return response.getBody();
    }
    public static boolean removeAccountByID(String accountID) {
        HttpEntity<String> headers = new HttpEntity<>(TokenDAO.setHeaders(Token.getAccessToken()));
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/delete/{accountID}", POST, headers, Boolean.class, accountID);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            removeAccountByID(accountID);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean addAccount(Account acc) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Account> vars = new HttpEntity<>(acc, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/save", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            addAccount(acc);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public static boolean updateAccount(Account acc) {
        HttpHeaders headers = TokenDAO.setHeaders(Token.getAccessToken());
        HttpEntity<Account> vars = new HttpEntity<>(acc, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url + "/update", POST, vars, Boolean.class);
        if (TokenDAO.checkToken(response.getStatusCode())) {
            updateAccount(acc);
        }
        return Boolean.TRUE.equals(response.getBody());
    }

}
