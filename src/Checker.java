import java.util.ArrayList;
import java.util.HashMap;

public class Checker {
    public MonthlyReport monthlyReport;
    public YearlyReport yearlyReport;
    HashMap<Integer, Integer> monthlyIncome = new HashMap<>();
    HashMap<Integer, Integer> monthlyExpenses = new HashMap<>();
    HashMap<Integer, Integer> yearlyIncome = new HashMap<>();
    HashMap<Integer, Integer> yearlyExpenses = new HashMap<>();

    public Checker(MonthlyReport monthlyReport, YearlyReport yearlyReport) {
        this.monthlyReport = monthlyReport;
        this.yearlyReport = yearlyReport;
        check();
    }

    public void fillDataByMonth() {
        for (Integer monthNumber : monthlyReport.months.keySet()) {
            ArrayList<MonthData> monthData = monthlyReport.months.get(monthNumber); // MonthData(itemName, isExpense, quantity, unitPrice)
            for (MonthData monthDatum : monthData) {
                if (monthDatum.isExpense == false) {
                    monthlyIncome.put(monthNumber, monthlyIncome.getOrDefault(monthNumber, 0) +
                            monthDatum.quantity * monthDatum.unitPrice);
                } else {
                    monthlyExpenses.put(monthNumber, monthlyExpenses.getOrDefault(monthNumber, 0) +
                            monthDatum.quantity * monthDatum.unitPrice);
                }
            }
        }
        for (Integer yearNumber : yearlyReport.years.keySet()) {
            ArrayList<YearData> yearData = yearlyReport.years.get(yearNumber); // YearData(mounth, amount, isExpense)
            for (YearData yearDatum : yearData) {
                if (yearDatum.isExpense == false) {
                    yearlyIncome.put(yearDatum.month, yearDatum.amount);
                } else {
                    yearlyExpenses.put(yearDatum.month, yearDatum.amount);
                }
            }
        }
    }

    public void check() {
        fillDataByMonth();
        boolean isTrueComparison = true;
        for (Integer monthNumber : monthlyIncome.keySet()) {
            if (!monthlyIncome.get(monthNumber).equals(yearlyIncome.get(monthNumber))) {
                isTrueComparison = false;
                System.out.println("При проверке отчетов о доходах, обнаружено несоответствие за " +
                        MonthName.monthName(monthNumber));
            }
        }
        for (Integer monthNumber : monthlyIncome.keySet()) {
            if (!monthlyExpenses.get(monthNumber).equals(yearlyExpenses.get(monthNumber))) {
                isTrueComparison = false;
                System.out.println("При проверке отчетов о расходах, обнаружено несоответствие за " +
                        MonthName.monthName(monthNumber));
            }
        }
        if (isTrueComparison == true)
            System.out.println("Проверка прошла успешно.");
    }
}
