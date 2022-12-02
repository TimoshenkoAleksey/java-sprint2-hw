public class MonthData {
    String itemName;
    boolean isExpense;
    int quantity;
    int unitPrice;

    public MonthData(String itemName, boolean isExpense, int quantity, int unitPrice) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
