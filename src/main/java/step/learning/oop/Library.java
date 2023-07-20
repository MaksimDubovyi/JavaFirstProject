package step.learning.oop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
public class Library
{
private List<Literature> funds;

    public Library() {
        funds=new ArrayList<>();
        funds.add(new Book("Art of Programming", "D. Knuth"));
        funds.add(new Journal("Nature", 123));
        funds.add(new Newspaper("Daily Mail", new Date(2000-1900, 4-1, 10)));
        funds.add(new Hologram("Cat", new Date(2000-1900, 4-1, 10)));
        funds.add(new Hologram("Dog", new Date(2002-1900, 2-1, 2)));
        funds.add(new Poster("Экстремальное программирование", "Книга о тестировании программы до написания. Автор делится приемами, паттернами и рефакторингами с пользованием методики «разработка на тестировании». Книга подходит, если хотите заниматься программированием в удовольствие."));
        funds.add(new Poster("Python. Экспресс-курс", "Практическое руководство о том, как освоить Python 3 от основ до структур данных. Издание раскрывает особенности языка и подсвечивает его объектно-ориентированные способности."));
        funds.add(new Poster("Программирование на C# для начинающих", "Книга об основах, структуре, типам данных, циклах, операторах и другой важной информации, которая потребуется, чтобы подружиться с языком С#. Автор дает подробные разъяснения для студентов и начинающих программистов."));

    }

    public void showCatalog()
{

    System.out.println( "Catalog" );
    for(Literature literature:funds)
    {
       System.out.println(literature.getCard());
    }
    System.out.println("-------------------showCopyable---------------------");
    this.showCopyable();
    System.out.println("\n-------------------showNonCopyable---------------------\n");
    this.showNonCopyable();
    System.out.println("\n-------------------showPeriodic---------------------\n");
    this.showPeriodic();
    System.out.println("\n-------------------Books  Authors---------------------\n");
    this.showBooksAuthors();
    System.out.println("\n-------------------Exposed   funds---------------------\n");
    this.showExposedFunds();
    System.out.println("\n-------------------Non Exposed Funds---------------------\n");
    this.showNonExposedFunds();
    System.out.println("\n-------------------Poster---------------------\n");
    this.showPosterFunds();
}

public void showPosterFunds()
{

    for(Literature literature:funds)
    {
        if(literature instanceof Expo && literature instanceof Copyable)
            System.out.println(literature.getCard());
    }
}
 public void showExposedFunds()
 {

     for(Literature literature:funds)
     {
         if(literature instanceof Expo)
             System.out.println(literature.getCard());
     }
 }
    public void showNonExposedFunds()
    {

        for(Literature literature:funds)
        {
            if(!(literature instanceof Expo))
                System.out.println(literature.getCard());
        }
    }
   public void showCopyable()
   {
       for(Literature literature:funds)
      {
       if(literature instanceof Copyable)
        System.out.println(literature.getCard());
      }
   }
    public void showNonCopyable()
    {
        for(Literature literature:funds)
        {
            if(!(literature instanceof Copyable))
            System.out.println(literature.getCard());
        }
    }

    public  void showPeriodic()
    {
        for(Literature literature:funds)
        {
            if(literature instanceof Periodic) {
                System.out.print(literature.getCard());
                System.out.printf("   Periodic with period: %s\n",((Periodic) literature).getPeriod());
            }
        }
    }

    public void showBooksAuthors()
    {
        for(Literature literature:funds)
        {
            if(literature instanceof Books) {
                Book book =(Book)literature;
                System.out.println(book.getAuthors());
            }
        }
    }

}

/*
Проєкт "Бібліотека"

Бібліотека - сховище література різного типу: газети, журнали, книги, тощо
По кожному виду літератури має буди аркуш-каталог із назвою та іншими даними
Розширення:
Послуга ксерокопіювання - чи може видання скопійоване.
Книги, журнали -копійовані, Газеті - ні

Періодічність:
журнали, газети - періодичні видання, книги -ні.
періодичні видання повинні мати відомості про період(String - 'daily', 'monthly' тощо )


*/

