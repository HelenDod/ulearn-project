import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Parser {
    public static List<String[]> lines = new ArrayList<>();

    public static void parseCSV() {
        try (var reader = new CSVReader(new FileReader("Переводы.csv"))) {
            lines = reader.readAll();
        } catch (CsvException | IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Transaction> getTransactionsFromCSV() throws IOException, ParseException {
        var headerFlag = true;
        var dateFormat = new SimpleDateFormat("yyyy.MM");
        var transactions = new ArrayList<Transaction>();
        for (String[] info : lines) {
            if (headerFlag) {
                headerFlag = false;
                continue;
            }
            transactions.add(new Transaction(info[0], dateFormat.parse(info[1]),
                    Objects.equals(info[2], "") ? 0 : Double.parseDouble(info[2]),
                    info[3],
                    info[4]));
        }
        return transactions;
    }

    public static ArrayList<Serie> getSeriesFromCSV() throws IOException, ParseException {
        var referencesMap = new HashMap<String, String[]>();
        var series = new ArrayList<Serie>();
        var headerFlag = true;
        for (String[] info : lines) {
            if (headerFlag) {
                headerFlag = false;
                continue;
            }
            if (!referencesMap.containsKey(info[0]))
                referencesMap.put(info[0], new String[]{info[5],
                        info[6], info[7], info[8],
                        info[9], info[10], info[11], info[12], info[13]});
        }
        for (var reference : referencesMap.keySet()) {
            var info = referencesMap.get(reference);
            series.add(new Serie(reference, info[0], Integer.parseInt(info[1]), info[2],
                    info[3], info[4], info[5], info[6], info[7], info[8]));
        }
        return series;
    }
}
