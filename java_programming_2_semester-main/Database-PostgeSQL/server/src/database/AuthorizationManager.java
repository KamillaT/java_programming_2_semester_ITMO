package database;

import data.UserInfo;
import hashing.MD2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AuthorizationManager {
    private final DatabaseManager databaseManager;

    public AuthorizationManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public int getUserId(String login) {
        AtomicInteger userId = new AtomicInteger();

        try {
            databaseManager.executeQuery(
                    resultSet -> userId.set(resultSet.getInt(1)),
                    "SELECT ID FROM APPLICATION_USERS WHERE LOGIN = ? LIMIT 1",
                    login
            );
        } catch (Exception e) {
            return -1;
        }

        return userId.get();
    }

    public String getUserLogin(int userId) {
        AtomicReference<String> userLogin = new AtomicReference<>();

        try {
            databaseManager.executeQuery(
                    resultSet -> userLogin.set(resultSet.getString(1)),
                    "SELECT LOGIN FROM APPLICATION_USERS WHERE ID = ?",
                    userId
            );
        } catch (Exception e) {
            return null;
        }

        return userLogin.get();
    }

    public boolean loginExists(String login) {
        try {
            AtomicReference<Boolean> hasResult = new AtomicReference<>(false);

            databaseManager.executeQuery(
                    resultSet -> hasResult.set(resultSet.getInt(1) > 0),
                    "SELECT COUNT(LOGIN) FROM APPLICATION_USERS WHERE LOGIN = ?", login);

            return hasResult.get();
        } catch (Exception exception) {
            return false;
        }
    }

    public ActionResult registerUser(UserInfo userInfo) {
        if (loginExists(userInfo.login())) {
            return ActionResult.FAILURE;
        }

        try {
            databaseManager.executeUpdate(
                    "INSERT INTO APPLICATION_USERS (LOGIN, PASSWORD) VALUES (?, ?)",
                    userInfo.login(),
                    MD2.apply(userInfo.password())
            );
        } catch (Exception exception) {
            return ActionResult.FAILURE;
        }

        return ActionResult.SUCCESS;
    }

    public int tryLogin(UserInfo info) {
        AtomicInteger userId = new AtomicInteger(-1);

        try {
            databaseManager.executeQuery(
                    resultSet -> userId.set(resultSet.getInt(1)),
                    "SELECT ID FROM APPLICATION_USERS WHERE LOGIN = ? AND PASSWORD = ?",
                    info.login(), MD2.apply(info.password())
            );
        } catch (Exception e) {
            return -1;
        }

        if (userId.get() == -1 && !loginExists(info.login()) && registerUser(info) == ActionResult.SUCCESS) {
            return getUserId(info.login());
        }

        return userId.get();
    }
}
