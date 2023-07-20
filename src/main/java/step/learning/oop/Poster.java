package step.learning.oop;

public class Poster extends Literature implements Expo,Copyable{

    private String description;
    private String type="Poster";
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Poster(String title,String description) {
        super.setTitle(title);
        this.setDescription(description);
    }

    @Override
    public String getCard() {
        return String.format("Постер: %s \nКороткий опис:\n %s", super.getTitle(),this.getDescription());
    }
}
