package step.learning.oop;

public class Journal extends Literature implements Copyable,Periodic{
    public Journal(String title, int number) {
        super();//делегування конструктора - в даному випадку не обовязково просто демонстрація
        this.setNumber(number);
        super.setTitle(title);
    }
    private  int number;

    public int getNumber() {
        return number;
    }



    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getCard() {
        return String.format("Journal: %s No. %d", super.getTitle(),this.getNumber());
    }

    @Override
    public String getPeriod() {
        return "Monthly";
    }
}
