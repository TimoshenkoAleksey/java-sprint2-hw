import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    boolean flag = false;
    HashMap<Integer, ArrayList<MonthData>> months = new HashMap<>(); // months -> int monthNumber + ArrayList<Month> monthData

    public void loadFile(int monthNumber, String path) {
        flag = true;
        ArrayList<MonthData> monthData = new ArrayList<>();
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(","); // item_name,is_expense,quantity,sum_of_one
            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int unitPrice = Integer.parseInt(parts[3]);
            MonthData month = new MonthData(itemName, isExpense, quantity, unitPrice);
            monthData.add(month);
        }
        months.put(monthNumber, monthData);
    }

    public void printMonthStatistic() {
        for (Integer monthNumber : months.keySet()) {
            System.out.println(MonthName.monthName(monthNumber));
            HashMap<String, Integer> profiteItems = getMostProfitableItem(monthNumber);
            for (String itemName : profiteItems.keySet()) {
                System.out.println("Самый прибыльный товар, " + itemName + ", был продан на сумму: " +
                        profiteItems.get(itemName));
            }
            HashMap<String, Integer> expensiveItems = getHighestExpense(monthNumber);
            for (String itemName : expensiveItems.keySet()) {
                System.out.println("Самая большая трата пришлась на: " + itemName + ", и составила " +
                        expensiveItems.get(itemName));
            }
        }
    }

    public HashMap<String, Integer> getMostProfitableItem(Integer monthNumber) {
        HashMap<String, Integer> profitItems = new HashMap<>();
        ArrayList<MonthData> monthData = months.get(monthNumber);
        String mostProfitItem = null;
        for (MonthData monthDataDatum : monthData) {
            if (monthDataDatum.isExpense == false) {
                profitItems.put(monthDataDatum.itemName, profitItems.getOrDefault(monthDataDatum.itemName, 0) +
                        monthDataDatum.unitPrice * monthDataDatum.quantity);
            }
            for (String itemName : profitItems.keySet()) {
                if (mostProfitItem == null) {
                    mostProfitItem = itemName;
                    continue;
                }
                if (profitItems.get(mostProfitItem) < profitItems.get(itemName)) {
                    mostProfitItem = itemName;
                }
            }
        }
        Integer maxProfit = profitItems.get(mostProfitItem);
        profitItems.clear();
        profitItems.put(mostProfitItem, maxProfit);
        return profitItems;
    }

    public HashMap<String, Integer> getHighestExpense(Integer monthNumber) {
        HashMap<String, Integer> expensiveItems = new HashMap<>();
        ArrayList<MonthData> monthData = months.get(monthNumber);
        String mostExpensiveItem = null;
        for (MonthData monthDataDatum : monthData) {
            if (monthDataDatum.isExpense == true) {
                expensiveItems.put(monthDataDatum.itemName,
                        expensiveItems.getOrDefault(monthDataDatum.itemName, 0) +
                                monthDataDatum.unitPrice * monthDataDatum.quantity);
            }
            for (String itemName : expensiveItems.keySet()) {
                if (mostExpensiveItem == null) {
                    mostExpensiveItem = itemName;
                    continue;
                }
                if (expensiveItems.get(mostExpensiveItem) < expensiveItems.get(itemName)) {
                    mostExpensiveItem = itemName;
                }
            }
        }
        Integer maxExpense = expensiveItems.get(mostExpensiveItem);
        expensiveItems.clear();
        expensiveItems.put(mostExpensiveItem, maxExpense);
        return expensiveItems;
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчетом");
            return null;
        }
    }
}
