import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class main {

    public static void main(String[] args) {
        try {
            boolean fillerFlag = false; // Смените этот флаг на true, чтобы заполнить БД
            var dbHandler = DBHandler.getInstance();
            Parser.parseCSV();
            if (fillerFlag)
                dbHandler.fillDB(Parser.getSeriesFromCSV(), Parser.getTransactionsFromCSV());//, Parser.getTransactionsFromCSV());
            var taskHandler = new Tasks();
            taskHandler.printAveragesFromAllPeriods();
            taskHandler.printMinAndMax("2014");
            taskHandler.printMinAndMax("2016");
            taskHandler.printMinAndMax("2020");
            taskHandler.createBarChart();
        } catch (SQLException | IOException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }
}