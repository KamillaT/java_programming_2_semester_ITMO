package database;

import org.intellij.lang.annotations.Language;
import org.postgresql.Driver;
import utility.ConsolePrinter;

import java.sql.*;

public class DatabaseManager {
    static {
        try {
            registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerDriver(Driver driver) throws SQLException {
        DriverManager.registerDriver(driver);
    }

    private Connection connection;

    public void connect(String url, String login, String password) throws SQLException {
        connection = DriverManager.getConnection(url, login, password);
    }

    private PreparedStatement prepareStatement(@Language("SQL") String query, Object... arguments) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            int i = 0;

            for (Object argument : arguments) {
                statement.setObject(++i, argument);
            }

            ConsolePrinter.printInformation(statement.toString());
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }

            throw e;
        }
    }

    public void executeUpdate(@Language("SQL") String query, Object... arguments) throws SQLException {
        try (var statement = prepareStatement(query, arguments)) {
            statement.executeUpdate();
        }
    }

    public void executeQuery(ThrowableConsumer<ResultSet> action, @Language("SQL") String query, Object... arguments) throws Exception {
        try (var statement = prepareStatement(query, arguments)) {
            var result = statement.executeQuery();

            while (result.next()) {
                action.accept(result);
            }
        }
    }
}
