import java.util.Date;

public class Transaction {
    public String reference;
    public Date period;
    public double value;
    public String suppressed;
    public String status;

    public Transaction(String reference, Date period, double value, String suppressed, String status){
        this.reference = reference;
        this.period = period;
        this.value = value;
        this.suppressed = suppressed;
        this.status = status;
    }
}
