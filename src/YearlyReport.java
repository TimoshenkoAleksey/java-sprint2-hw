import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    boolean flag = false;
    HashMap<Integer, ArrayList<YearData>> years = new HashMap<>();

    public void loadFile(int yearNumber, String path) {
        flag = true;
        ArrayList<YearData> yearData = new ArrayList<>();
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");

        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(","); //month,amount,is_expense
            int mounth = Integer.parseInt(parts[0], 10);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);
            YearData year = new YearData(mounth, amount, isExpense);
            yearData.add(year);
        }
        years.put(yearNumber, yearData);
    }

    public void printYearStatistic(int yearNumber) {
        HashMap<Integer, Integer> incomeYear = new HashMap<>();
        HashMap<Integer, Integer> expensesYear = new HashMap<>();
        HashMap<Integer, Integer> profitYear = new HashMap<>();
        ArrayList<YearData> yearData = years.get(yearNumber);
        System.out.println(yearNumber);

        for (YearData yearDatum : yearData) {
            if (yearDatum.isExpense == false) {
                incomeYear.put(yearDatum.month, yearDatum.amount);
            } else {
                expensesYear.put(yearDatum.month, yearDatum.amount);
            }
        }

        for (Integer monthNumber : incomeYear.keySet()) {
            if (expensesYear.containsKey(monthNumber)) {
                profitYear.put(monthNumber, incomeYear.get(monthNumber) - expensesYear.get(monthNumber));
            } else {
                profitYear.put(monthNumber, incomeYear.get(monthNumber));
            }
        }

        for (Integer monthNumber : profitYear.keySet()) {
            System.out.println("Прибыль за " + MonthName.monthName(monthNumber) + " составила: " +
                    profitYear.get(monthNumber));
        }

        double sumExpenses = 0;
        for (Integer monthNumber : expensesYear.keySet()) {
            sumExpenses += expensesYear.get(monthNumber);
        }
        System.out.println("Средний расход за все месяцы составил: " + (sumExpenses / expensesYear.size()));

        double sumIncome = 0;
        for (Integer monthNumber : incomeYear.keySet()) {
            sumIncome += incomeYear.get(monthNumber);
        }
        System.out.println("Средний доход за все месяцы составил: " + (sumIncome / incomeYear.size()));
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчетом.");
            return null;
        }
    }
}
