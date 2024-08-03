package data;

public record UserInfo(String login, String password) {
    private static final String regexForLoginAndPassword = "\\w{4,128}";

    public UserInfo {
        if (!isLoginValid(login)) {
            throw new IllegalArgumentException(
                    "Login's length must be between 4 and 128 symbols" +
                            "and login can contain only digits and letters"
            );
        }

        if (!isPasswordValid(password)) {
            throw new IllegalArgumentException(
                    "Password's length must be between 4 and 128 symbols" +
                            "and password can contain only digits and letters"
            );
        }
    }

    public static boolean isLoginValid(String login) {
        return login.matches(regexForLoginAndPassword);
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(regexForLoginAndPassword);
    }
}