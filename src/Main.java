import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                for (int i = 1; i < 4; i++) {
                    monthlyReport.loadFile(i, "resources/m.20210" + i + ".csv");
                }
            } else if (command == 2) {
                yearlyReport.loadFile(2021, "resources/y.2021.csv");
            } else if (command == 3) {
                if (monthlyReport.flag == true && yearlyReport.flag == true)
                    new Checker(monthlyReport, yearlyReport);
                else System.out.println("Для сверки отчетов необходимо сначала считать годовой и месячные отчеты.");
            } else if (command == 4) {
                if (monthlyReport.flag == true)
                    monthlyReport.printMonthStatistic();
                else System.out.println("Для вывода информации необходимо сначала считать месячные отчеты.");
            } else if (command == 5) {
                if (yearlyReport.flag == true)
                    yearlyReport.printYearStatistic(2021);
                else System.out.println("Для вывода информации необходимо сначала считать годовой отчет.");
            } else if (command == 0) {
                break;
            } else
                System.out.println("Такой команды нет.");
        }
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("Введите команду:");
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчёт.");
        System.out.println("3 - Сверить отчёты.");
        System.out.println("4 - Вывести информацию о всех месячных отчётах.");
        System.out.println("5 - Вывести информацию о годовом отчёте.");
        System.out.println("0 - Выйти из программы.");
    }
}

