import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

public class Tasks {
    private static ArrayList<Date> periods;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM");
    private DBHandler dbHandler = DBHandler.getInstance();

    public Tasks() throws SQLException, ParseException {
        periods = dbHandler.fillPeriods();
    }

    public JFreeChart createBarChart() throws SQLException, ParseException {
        var dataset = new DefaultCategoryDataset();
        var monthFormatter = new SimpleDateFormat("MM");
        Date startDate = dateFormat.parse("2020.01"),
                endDate = dateFormat.parse("2020.12");
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        while(!start.after(end)){
            var targetMonth = start.getTime();
            var transactionsSum = dbHandler.getValuesByPeriod(targetMonth).stream().reduce(0d, Double::sum);
            dataset.setValue(transactionsSum,
                    monthFormatter.format(targetMonth), "2020");
            System.out.println(transactionsSum);
            start.add(Calendar.MONTH, 1);
        }
        var chart = ChartFactory.createBarChart(
                "Суммы переводов по месяцам",
                "Месяц",
                "Сумма переводов",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);
        var plot = chart.getCategoryPlot();
        var br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0);
        var domain = plot.getDomainAxis();
        domain.setLowerMargin(0.25);
        domain.setUpperMargin(0.25);

        var frame = new JFrame("Таблица cумм переводов");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var cp = new ChartPanel(chart){

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 400);
            }
        };
        frame.add(cp);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return chart;
    }

    public void printAveragesFromAllPeriods() throws ParseException {
        for (var period : periods){
            var values = dbHandler.getValuesByPeriod(period);
            System.out.print(dateFormat.format(period) + " "
                    + String.format("%.2f",values.stream().reduce(0d, Double::sum))
            + " " + values.size() + "\n");
        }
    }

    public void printMinAndMax(String year) throws ParseException, SQLException {
        var minsAndMaxes = new ArrayList<Double>();
        Date startDate = dateFormat.parse(year + ".01"),
                endDate = dateFormat.parse(year + ".12");
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        while(!start.after(end)){
            var targetMonth = start.getTime();
            minsAndMaxes.add(dbHandler.getMinByPeriod(targetMonth));
            minsAndMaxes.add(dbHandler.getMaxByPeriod(targetMonth));
            start.add(Calendar.MONTH, 1);
        }
        Collections.sort(minsAndMaxes);
        System.out.println(minsAndMaxes.get(0) + " " + minsAndMaxes.get(minsAndMaxes.size() - 1));
    }
}
