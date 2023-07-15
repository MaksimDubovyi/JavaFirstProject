package step.learning.oop;

public class Booklet extends Literature{
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Booklet(String title,String publisher) {
        super.setTitle(title);
        this.setPublisher(publisher);
    }

    @Override
    public String getCard() {
        return String.format("Booklet: %s '%s'",this.getPublisher(),super.getTitle());
    }
}
