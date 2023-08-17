package step.learning.db;
import org.json.JSONObject;
import step.learning.db.dao.RecordDao;
import step.learning.db.dto.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class DbDemo {
    private  int rangeFrom=0;
    private  int rangeTo=0;
     private  Random random = new Random();
    private String url;
    private String user;
    private String password;
    private  com.mysql.cj.jdbc.Driver mysqlDriver;
    private  java.sql.Connection connection;
    private RecordDao recordDao;
    public  void run()
    {
        System.out.println("_______________Database Demo_____________________");
        JSONObject conf= config();

        JSONObject dbConf=conf
                .getJSONObject("DataProviders")  //Заглиблюємося в PlanetScale
                .getJSONObject("PlanetScale");   // отримувати об'єкт PlanetScale

        //Заповнюємо поля з об'єкту dbConf
         url=dbConf.getString("url");
         user=dbConf.getString("user");
         password=dbConf.getString("password");

        if((connection=this.connect())==null)  // Якщо все добре this.connect() поверне java.sql.Connection, а якщо ні, то null (підключення)
        {
            return ;
        }
        recordDao= new RecordDao(random,connection);

        //Створення таблиці для об'єкта якщо вона не існує, то вона буде створена
        if(recordDao.ensureCreated())
        {
            System.out.println("Ensure OK");
        }





        useDb();

        //Відключення
        this.disconnect();

    }
    private void useDb()
    {

        do {

            int choice= IntCheck("Оберіть операцію (Введіть номер та натисніть Enter): \n1# Додавання об'єктів до БД :\n2# Видалення обєкта по Id :\n3# Пошук обєкта по Id :\n4# Перегляд всіх об'єктів з таблиці jpu121_randoms : \n5# Кількість : \n6# Кількість яка знаходитья у заданому діапазоні :\n");

            if (choice<7&&choice>0)
            {
                switch (choice)
                {
                    case 1:
                        addObj();
                        break;
                    case 2:
                        deleteObj();
                        break;
                    case 3:
                        selectObjectId();
                        break;
                    case 4:
                        showAll();
                        break;
                    case 5:
                        ShowCount();
                        break;
                    case 6:
                        rowsCountInSegment();
                        break;
                }
                //break;
            }
            else
            {
                System.out.println("Некорректний ввід.");
            }
        } while (true);

    }

    /**
     * Видалення обєкта по Id
     */
    private  void deleteObj()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть id: ");

        boolean res=recordDao.deleteRecordById(UUID.fromString(scanner.nextLine()));

            if (res) {
                System.out.println("Об'єкт успішно видалено.");
            } else {
                System.out.println("Об'єкт з вказаним id не знайдено. Видалення не виконано.");
            }

            System.out.println( "\n_________________________________________________________________________________________________________________");

    }

    /**
     *Пшук по id
     */
    private  void selectObjectId()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть id: ");

        Record record =recordDao.getById(UUID.fromString(scanner.nextLine()));
        System.out.println(record);


        if(record!=null)
        deleteOrUpdate(record);
        else {
            System.out.println("Об'єкт з вказаним id не знайдено.");
        }
        System.out.println("\n_________________________________________________________________________________________________________________");
    }
private void deleteOrUpdate(Record record)
{
    do {

        int choice= IntCheck("Оберіть операцію:\n1# Видалення обєкта:\n2#: Редагування обєкта");

        if (choice<3&&choice>0)
        {
            switch (choice)
            {
                case 1:
                    deleteRecord(record);
                    break;
                case 2:
                    updateRecord(record);
                    break;
            }
        }
        else
        {
            System.out.println("Некорректний ввід.");
        }
    } while (true);
}
 private void deleteRecord(Record record)
 {
     boolean res= recordDao.delete(record);

     if (res) {
         System.out.println("Об'єкт успішно видалено.");
     } else {
         System.out.println("Об'єкт з вказаним id не знайдено. Видалення не виконано.");
     }

     System.out.println( "\n_________________________________________________________________________________________________________________");
 }
 private  void updateRecord(Record record)
 {
         String val_str=getStr();
         float val_float =FloatCheck();
         int val_int=IntCheck("Введіть val_int:");
         Record recordNew=new Record(val_int,val_str,val_float);
         recordNew.setId(record.getId());

          boolean res= recordDao.update(recordNew);

            if (res) {
                 System.out.println("Об'єкт успішно оновлено.");
          } else {
                 System.out.println("Об'єкт з вказаним id не знайдено. Оновлення не виконано.");
              }
     System.out.println( "\n_________________________________________________________________________________________________________________");
 }
    /**
     *Перегляд всіх об'єктів з таблиці jpu121_randoms
     */
    private void showAll() {
        List<Record> records = recordDao.getAll() ;
        if( records == null ) {
            System.out.println( "Error getting list" ) ;
        }
        else {
            for( Record record : records ) {
                System.out.println( record ) ;
            }
        }
    }


private  void ShowCount()
{
    int cnt = recordDao.getCount();
    if(cnt==-1)
    {
        System.out.println("Counter error ");
    }
    else {
        System.out.println("Rows count: "+ cnt);
    }



}
private void rowsCountInSegment()
{

    do {
        rangeFrom=IntCheck("Задайте діапазон\nВід:");
        rangeTo=IntCheck("До:");
        if(rangeFrom==rangeTo||rangeFrom>rangeTo||rangeFrom<0||rangeTo<0)
        {
            System.out.println("Некоректний ввід.");
        }
        else
            break;
    } while (true);

    try(PreparedStatement prep = this.connection.prepareStatement("SELECT COUNT(*) FROM jpu121_randoms WHERE val_int > ? AND val_int < ?" ))
    {
        prep.setInt(1, rangeFrom); // Передаємо значення для першого параметра
        prep.setInt(2, rangeTo);   // Передаємо значення для другого параметра

        ResultSet res = prep.executeQuery();
        res.next();
        System.out.println("Count" + res.getInt(1));


        do {
            int choice= IntCheck("\n1# Логувати:\n2# Вихід :\n");
            if (choice<7&&choice>0)
            {
                switch (choice)
                {
                    case 1:
                        ShowRange();
                        break;
                    case 2:
                        useDb();
                        break;
                }
            }
            else
            {
                System.out.println("Некорректний ввід.");
            }
        } while (true);
    }
    catch (SQLException ex)
    {
        System.err.println(ex.getMessage());
    }
}
private  void ShowRange()
{
    try(PreparedStatement prep = this.connection.prepareStatement("SELECT `id`, `val_int`, `val_str`,`val_float` FROM jpu121_randoms WHERE val_int > ? AND val_int < ?" ))
    {
        prep.setInt(1, rangeFrom); // Передаємо значення для першого параметра
        prep.setInt(2, rangeTo);   // Передаємо значення для другого параметра

        ResultSet res = prep.executeQuery();
        int i=1;
        while (res.next()) {
            System.out.printf(
                    "%d) id - (%s) val_int - (%d) val_str - (%s) val_float - (%f)  %n",
                    i,
                    res.getString("id") ,
                    res.getInt("val_int"),
                    res.getString("val_str"),
                    res.getFloat("val_float")
            );
            i++;
        }
        res.close();
        System.out.println("_________________________________________________________________________________________________________________");
    }
    catch (SQLException ex)
    {
        System.err.println(ex.getMessage());
    }
}
    /**
     * Перевірка на ціле число
     * @param str
     * @return
     */
    private int IntCheck(String str)
    {
        Scanner scanner = new Scanner(System.in) ;
        int count = 0;

        do {
            System.out.print(str);

            if (scanner.hasNextInt())
            {
                count = scanner.nextInt();
                if (count < 0)
                    System.out.println("Введіть додатнє число.");
                 else
                    break; // Вихід із циклу, якщо введено коректне значення

            }
            else
            {
                System.out.println("Некорректний ввід. Введіть ціле число.");
                scanner.nextLine(); // Очищаем буфер ввода
            }
        } while (true);
        return count;
    }

    /**
     *  Перевірка на float
     * @return
     */
    private float FloatCheck()
    {
        Scanner scanner = new Scanner(System.in) ;
        float count = 0;

        do {
            System.out.print("Введіть val_float (наприклад 2,1)");

            if (scanner.hasNextFloat())
            {
                count = scanner.nextFloat();
                if (count < 0)
                    System.out.println("Введіть додатнє число.");
                else
                    break; // Вихід із циклу, якщо введено коректне значення
            }
            else
            {
                System.out.println("Некорректний ввід.");
                scanner.nextLine(); // Очищаем буфер ввода
            }
        } while (true);
        return count;
    }

    /**
     * Запит у користувача для val_str
     * @return
     */
    private String getStr()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть val_str: ");
        return scanner.nextLine();
    }

    /**
     *  Додавання об'єктів до БД
     */
    private void addObj()
    {

        do {

            int choice= IntCheck("Як бажаєте добавити?\n1# Рандомно:\n2#: Не рандомно");

            if (choice<3&&choice>0)
            {
                switch (choice)
                {
                    case 1:
                        addRandomObj();
                        break;
                    case 2:
                        addObject();
                        break;
                }
                //break;
            }
            else
            {
                System.out.println("Некорректний ввід.");
            }
        } while (true);

    }

    /**
     * Додавання об'єктів до БД не рандом
     */
    private void addObject()
    {
        String countStr="Скільки ви бажаєте додати об'єктів в таблицю? ";
        String intStr="Введіть val_int: ";

       int Count= IntCheck(countStr);
       for (int i=0;i<Count;i++)
       {
           int x=i+1;
           System.out.println("Введіть "+x+" обє'кт");
           String val_str=getStr();
           float val_float =FloatCheck();
           int val_int=IntCheck(intStr);
           Record record=new Record(val_int,val_str,val_float);
           recordDao.create(record);
       }
        System.out.println("Вітання об'єкти додані до бази успішно!");

    }

    /**
     * Додавання об'єктів до БД рандом
     */
    private  void addRandomObj()
    {
        String countStr="Скільки ви бажаєте додати об'єктів в таблицю? ";

        int Count= IntCheck(countStr);
        recordDao.insertPrepared(Count);
        System.out.println("Вітання об'єкти додані до бази успішно!");
    }

    /**
     * Створення таблиці для об'єкта якщо вона не існує, то вона буде створена
     */
//    private void  ensureCreated() ///IF NOT EXISTS пишеться для перевірки е таблиця чи ні якщо є то створюватись не буде
//    {
//        String sql="CREATE TABLE IF NOT EXISTS jpu121_randoms("+
//                "`id`        CHAR(36) PRIMARY KEY,"+
//                "`val_int`   INT,"+
//                "`val_str`   VARCHAR(256),"+
//                "`val_float` FLOAT"+
//                ")";
//
//        try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
//        {
//         statement.executeUpdate(sql); //executeUpdate -для запитів без повернення
//            System.out.println("OK");
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage());
//        }
//    }

    /**
     * Читання файлу appsetings.json в StringBuilder і при поверненні перетворюємо його в JSONObject
     * @return
     */
    private  JSONObject  config()
    {
        StringBuilder sb=new StringBuilder();
        try(BufferedReader reader = new BufferedReader( new FileReader("appsetings.json")))
        {
            int c;
            while ((c=reader.read())!=-1)
            {
                sb.append((char)c);
            }

        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
       return new JSONObject(sb.toString());
    }

    private  java.sql.Connection connect()
    {
          try {
              //реєструємо драйвер
              //а) через Class.forName("com.mysql.cj.jdbc.Driver");
              //б) через пряме створення драйвера
              mysqlDriver = new com.mysql.cj.jdbc.Driver();
               DriverManager.registerDriver(mysqlDriver);
               //Підключаємось, маючи зареєстрований драйвер
              return DriverManager.getConnection(url,user,password);
             }
        catch (SQLException ex)
         {
             System.out.println( ex.getMessage());
             return null;
         }
    }

    private void disconnect()
    {
        try {
                if(connection!=null)
                {connection.close();}

                if(mysqlDriver!=null)
                {
                    DriverManager.deregisterDriver(mysqlDriver);
                }

        }
        catch (SQLException ignored) { }

    }


    /*
    DTO, DAO
    DTO- Data Transfer Object - обєкт для передачі даних - структураб яка
    містить поля (або властивості), їх аксесори, конструктори та утіліти
    (toString(), toJson(), тощо). Не містить логіку. Аналог -сутності(Entity)

    DAO - Data Access Object - обє'кт доступу до даних - логіка роботи з
    об'єктами DTO. Аналог LINQ

    Наприклад

    UserDTO
    {
    private UUID id;
    private string name;

    public UUID getId();
    public string getName();
    }

    UerDAO
    {
    public UserDTO getUserById(UUID id){.....}
    }

    */
}
/*
Робота з базами даних. JDBC

1. Конфігурація. Робота з варіативним JSON       https://mvnrepository.com/artifact/org.json/json
- підключаємо пакет (залежность) org.json
- деталі роботи - у методах config() та run()
2.Конектор (драйвер підключення)
- на Mavem шукаємо відповідний двайвер (MySQL) для JDBC
3. Робота з БД
- підключення


Налаштування IDE
 Database (tool-window) - + - DataSource - MySQL -
  option: URL Only
  вводимо дані з конфігурації
  Test Connection - Apply - OK
*/