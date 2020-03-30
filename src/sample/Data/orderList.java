package sample.Data;

public class orderList {
    private String cusName;
    private String TableNum;
    private String FoodName;
    private String BeverageName;

    public orderList(String cusName, String tableNum, String foodName, String beverageName) {
        this.cusName = cusName;
        TableNum = tableNum;
        FoodName = foodName;
        BeverageName = beverageName;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getTableNum() {
        return TableNum;
    }

    public void setTableNum(String tableNum) {
        TableNum = tableNum;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getBeverageName() {
        return BeverageName;
    }

    public void setBeverageName(String beverageName) {
        BeverageName = beverageName;
    }
}
