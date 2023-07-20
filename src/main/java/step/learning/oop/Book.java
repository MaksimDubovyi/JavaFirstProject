package step.learning.oop;

public class Book extends Literature implements Copyable,Books{
    private String  author;
    private String type="Book";

    public String getAuthor() {
        return author;
    }

    public Book(String title,String author) {
        super.setTitle(title);
        this.setAuthor(author);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getCard() {
        return String.format("Book: %s '%s'",this.getAuthor(),super.getTitle());
    }
    @Override
    public String getAuthors() {
        return String.format("Author: %s ",this.getAuthor());
    }
}
