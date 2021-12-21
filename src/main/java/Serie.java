import java.util.ArrayList;

public class Serie {
    public String reference;
    public String units;
    public int magnitude;
    public String subject;
    public String group;
    public String title1;
    public String title2;
    public String title3;
    public String title4;
    public String title5;

    public Serie(String reference, String units,
                 int magnitude, String subject, String group, String title1,
                 String title2, String title3, String title4, String title5){
        this.reference = reference;
        this.units = units;
        this.magnitude = magnitude;
        this.subject = subject;
        this.group = group;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.title4 = title4;
        this.title5 = title5;
    }
}
