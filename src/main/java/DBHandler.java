import org.sqlite.JDBC;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBHandler {

    private static final String CON_STR = "jdbc:sqlite:Transactions.sqlite";
    private static DBHandler instance = null;

    public static synchronized DBHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    private Connection connection;

    private DBHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM");

    public void addSerie(Serie serie) throws SQLException {

        try (var statement = this.connection.prepareStatement(
                "INSERT INTO 'Series description'('Series reference', units, magnitude, subject, 'group', " +
                        "'title 1', 'title 2', 'title 3', 'title 4', 'title 5') " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            statement.setObject(1, serie.reference);
            statement.setObject(2, serie.units);
            statement.setObject(3, serie.magnitude);
            statement.setObject(4, serie.subject);
            statement.setObject(5, serie.group);
            statement.setObject(6, serie.title1);
            statement.setObject(7, serie.title2);
            statement.setObject(8, serie.title3);
            statement.setObject(9, serie.title4);
            statement.setObject(10, serie.title5);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(Transaction transaction) {
        try (var statement = this.connection.prepareStatement(
                "INSERT INTO Transactions('Series reference', Period, Value, Suppressed, Status) " +
                        "VALUES (?,?,?,?,?)")) {
            statement.setObject(1, transaction.reference);
            statement.setObject(2, dateFormat.format(transaction.period));
            statement.setObject(3, transaction.value);
            statement.setObject(4, transaction.suppressed);
            statement.setObject(5, transaction.status);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillDB(List<Serie> seriesList, List<Transaction> transactionsList) {
        try {
            DBHandler dbHandler = DBHandler.getInstance();
            for (var serie : seriesList)
                dbHandler.addSerie(serie);
            for (var transaction : transactionsList)
                dbHandler.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Date> fillPeriods() throws SQLException, ParseException {
        var sql = "SELECT DISTINCT Period " +
                "FROM Transactions";
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(sql);
        var periods = new ArrayList<Date>();
        while (resultSet.next())
            periods.add(dateFormat.parse(resultSet.getString("Period")));
        return periods;
    }

    public ArrayList<Double> getValuesByPeriod(Date period) {
        try {
            var values = new ArrayList<Double>();
            var sql = """
                    SELECT Value
                    FROM Transactions, "Series description"
                    WHERE Transactions."Series reference" = "Series description"."Series reference" and
                    "Series description".Units = 'Dollars' AND Transactions.Period = ? and Transactions.Value != 0""";
            var statement = connection.prepareStatement(sql);
            statement.setObject(1, dateFormat.format(period));
            var resultSet = statement.executeQuery();
            while (resultSet.next())
                values.add(resultSet.getDouble("Value"));
            return values;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public double getMaxByPeriod(Date period) throws SQLException {
        var sql = """
                    SELECT Value
                    FROM Transactions, "Series description"
                    WHERE Transactions."Series reference" = "Series description"."Series reference" and
                    "Series description".Units = 'Dollars' AND Transactions.Period = ? and Transactions.Value != 0
                    ORDER BY Value DESC
                    LIMIT 1""";
        var statement = connection.prepareStatement(sql);
        statement.setObject(1, dateFormat.format(period));
        var result = statement.executeQuery();
        return result.getDouble("Value");
    }

    public double getMinByPeriod(Date period) throws SQLException {
        var sql = """
                    SELECT Value
                    FROM Transactions, "Series description"
                    WHERE Transactions."Series reference" = "Series description"."Series reference" and
                    "Series description".Units = 'Dollars' AND Transactions.Period = ? and Transactions.Value != 0
                    ORDER BY Value
                    LIMIT 1""";
        var statement = connection.prepareStatement(sql);
        statement.setObject(1, dateFormat.format(period));
        var result = statement.executeQuery();
        return result.getDouble("Value");
    }
}