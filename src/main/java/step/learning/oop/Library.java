package step.learning.oop;

import java.util.ArrayList;
import java.util.List;

public class Library
{
private List<Literature> funds;

    public Library() {
        funds=new ArrayList<>();
        funds.add(new Book("Art of Programming", "D. Knuth"));
    }

    public void showCatalog()
{
    System.out.println( "Catalog" );
}

}

/*
Проєкт "Бібліотека"

Бібліотека - сховище література різного типу: газети, журнали, книги, тощо
По кожному виду літератури має буди аркуш-каталог із назвою та іншими даними


*/

