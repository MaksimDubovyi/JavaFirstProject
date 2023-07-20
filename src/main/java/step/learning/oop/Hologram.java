package step.learning.oop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Hologram  extends Literature implements Expo{
    private Date date;
    private String type="Hologram";
    private static final SimpleDateFormat dateFormat =new SimpleDateFormat("dd.MM.yyyy");
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Hologram(String title,Date date) {
        super.setTitle(title);
        this.setDate(date);
    }

    @Override
    public String getCard() {
        return String.format("Hologram: '%s' %s",super.getTitle(), dateFormat.format(this.getDate()));
    }
}
