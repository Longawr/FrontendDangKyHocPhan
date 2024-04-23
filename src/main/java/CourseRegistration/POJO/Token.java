package CourseRegistration.POJO;

public class Token {
    private static String accessToken = "";
    private static String refreshToken = "";

    public Token() {
    }

    public Token(String accessToken, String refreshToken) {
        Token.accessToken = accessToken;
        Token.refreshToken = refreshToken;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        Token.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        Token.refreshToken = refreshToken;
    }
}
