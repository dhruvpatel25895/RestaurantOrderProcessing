package sample.Data;

public class ItemDetails {

    private String ItemName;
    private String Energy;
    private String Protien;
    private String Carbohydrate;
    private String TotalFat;
    private String Fibre;
    private String Price;


    public ItemDetails(String itemName, String energy, String protien, String carbohydrate, String totalFat, String fibre, String price) {
        ItemName = itemName;
        Energy = energy;
        Protien = protien;
        Carbohydrate = carbohydrate;
        TotalFat = totalFat;
        Fibre = fibre;
        Price = price;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getEnergy() {
        return Energy;
    }

    public void setEnergy(String energy) {
        Energy = energy;
    }

    public String getProtien() {
        return Protien;
    }

    public void setProtien(String protien) {
        Protien = protien;
    }

    public String getCarbohydrate() {
        return Carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        Carbohydrate = carbohydrate;
    }

    public String getTotalFat() {
        return TotalFat;
    }

    public void setTotalFat(String totalFat) {
        TotalFat = totalFat;
    }

    public String getFibre() {
        return Fibre;
    }

    public void setFibre(String fibre) {
        Fibre = fibre;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
