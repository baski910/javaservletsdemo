import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

abstract class FinCalc{
    public abstract double weightedAverage();
}

class PurchasePrice extends FinCalc{
    private String filename;
    String line;
    String csvSplitBy = ",";

    double totalCost = 0.0;
    double totalQuantity = 0.0;
    double weightedAvg;
    boolean isHeader = true;


    public PurchasePrice(String filename){
        this.filename = filename;
    }
    public double weightedAverage(){
        System.out.println("purchase average");

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            
            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split line by comma
                String[] data = line.split(csvSplitBy);

                try {
                    // Extract price and quantity based on column index
                    // (Index 1 = purchase_price, Index 2 = quantity)
                    double price = Double.parseDouble(data[1].trim());
                    double quantity = Double.parseDouble(data[2].trim());

                    // Accumulate values
                    totalCost += price * quantity;
                    totalQuantity += quantity;
                    
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed row: " + line);
                }
                }

            // Calculate and display the weighted average
            if (totalQuantity > 0) {
                weightedAvg = totalCost / totalQuantity;
                System.out.printf("Total Quantity: %.2f%n", totalQuantity);
                
            } else {
                System.out.println("No valid data found to calculate weighted average.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return weightedAvg;
    }
}

class DebtorTurnOver extends FinCalc{
    private String filename;
    String line;
    String csvSplitBy = ",";

    double totalWeightedTurnover = 0.0;
    double totalWeight = 0.0;
    double weightedAvg = 0.0;
    boolean isHeader = true;
    public DebtorTurnOver(String filename){
        this.filename = filename;
    }
    public double weightedAverage(){
        //System.out.println("debtor turnover");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split line by comma
                String[] data = line.split(csvSplitBy);

                if (data.length >= 3) {
                    try {
                        // Extract values (Index 1 = Ratio, Index 2 = Weight/Balance)
                        double turnoverRatio = Double.parseDouble(data[1].trim());
                        double endingBalance = Double.parseDouble(data[2].trim());

                        // Calculate weighted components
                        totalWeightedTurnover += turnoverRatio * endingBalance;
                        totalWeight += endingBalance;
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed row: " + line);
                    }
                }
            }

            // Calculate final weighted average
            if (totalWeight > 0) {
                weightedAvg = totalWeightedTurnover / totalWeight;
                System.out.printf("Total Weight (Balances): %,.2f%n", totalWeight);
                //System.out.printf("Weighted Average Debtor Turnover: %.2f%n", weightedAverage);
            } else {
                System.out.println("No valid data found to calculate weighted average.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
        return weightedAvg;
    }
}

public class Example1 {

    public static void main(String args[]){
        PurchasePrice pp=new PurchasePrice("purchases.csv");
        double result = pp.weightedAverage();
        System.out.printf("Weighted Average Purchase Price: %.4f%n", result);

        DebtorTurnOver dro=new DebtorTurnOver("debtors.csv");
        result = dro.weightedAverage();
        System.out.printf("Weighted Average of Debtor Turn Over: %.4f%n", result);
    }
    
}
