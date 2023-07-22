package step.learning.db;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class DbDemo {

    private String url;
    private String user;
    private String password;
    private  com.mysql.cj.jdbc.Driver mysqlDriver;
    private  java.sql.Connection connection;
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


        //Створення таблиці для об'єкта якщо вона не існує, то вона буде створена
        ensureCreated();


        useDb();

        //Відключення
        this.disconnect();

    }
    private void useDb()
    {

        do {

            int choice= IntCheck("Оберіть операцію (Введіть номер та натисніть Enter): \n1# Додавання об'єктів до БД :\n2# Видалення обєкта по Id :\n3# Пошук обєкта по Id :\n4# Перегляд всіх об'єктів з таблиці jpu121_randoms : \n");

            if (choice<5&&choice>0)
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
                        selectObjects();
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

        String sql = "DELETE FROM jpu121_randoms WHERE id= '"+scanner.nextLine()+"'";

        try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
        {
            int rowsDeleted = statement.executeUpdate(sql);

            if (rowsDeleted > 0) {
                System.out.println("Об'єкт успішно видалено.");
            } else {
                System.out.println("Об'єкт з вказаним id не знайдено. Видалення не виконано.");
            }

            System.out.println(sql + "\n_________________________________________________________________________________________________________________");

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *Пшук по id
     */
    private  void selectObjectId()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть id: ");

        String sql = "SELECT * FROM jpu121_randoms WHERE id= '"+scanner.nextLine()+"'";

        try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
        {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("Об'єкт успішно знайдено.");
                String id = resultSet.getString("id");
                int val_int = resultSet.getInt("val_int");
                String val_str = resultSet.getString("val_str");
                float val_float = resultSet.getFloat("val_float");

                System.out.println("id: " + id + ", val_int: " + val_int + ", val_str: " + val_str + ", val_float: " + val_float);
            } else {
                System.out.println("Об'єкт з вказаним id не знайдено.");
            }

            System.out.println("\n_________________________________________________________________________________________________________________");

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *Перегляд всіх об'єктів з таблиці jpu121_randoms
     */
    private void  selectObjects()
    {
        String sql = "SELECT * FROM jpu121_randoms";

        try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
        {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                int val_int = resultSet.getInt("val_int");
                String val_str = resultSet.getString("val_str");
                float val_float = resultSet.getFloat("val_float");

                System.out.println("id: " + id + ", val_int: " + val_int + ", val_str: " + val_str + ", val_float: " + val_float);
            }
            System.out.println("_________________________________________________________________________________________________________________");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
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
           String sql = "INSERT INTO jpu121_randoms(`id`, `val_int`, `val_str`, `val_float`) VALUES( UUID(), " + val_int + ", '" + val_str + "', " + val_float + ")";

           try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
           {
               statement.executeUpdate(sql); //executeUpdate -для запитів без повернення
               System.out.println(sql+"\n_________________________________________________________________________________________________________________");
           }
           catch (SQLException ex)
           {
               System.out.println(ex.getMessage());
           }
       }
        System.out.println("Вітання об'єкти додані до бази успішно!");

    }

    /**
     * Додавання об'єктів до БД рандом
     */
    private  void addRandomObj()
    {
        String countStr="Скільки ви бажаєте додати об'єктів в таблицю? ";

        Random random = new Random();
        int Count= IntCheck(countStr);
        for (int i=0;i<Count;i++)
        {
            float val_float =random.nextInt(101);
            int val_int=random.nextInt(101);
            String val_str="val_int: "+val_int+" val_float: "+val_float;

            String sql = "INSERT INTO jpu121_randoms(`id`, `val_int`, `val_str`, `val_float`) VALUES( UUID(), " + val_int + ", '" + val_str + "', " + val_float + ")";

            try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
            {
                statement.executeUpdate(sql); //executeUpdate -для запитів без повернення
                System.out.println(sql+"\n_________________________________________________________________________________________________________________");
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Вітання об'єкти додані до бази успішно!");

    }

    /**
     * Створення таблиці для об'єкта якщо вона не існує, то вона буде створена
     */
    private void  ensureCreated() ///IF NOT EXISTS пишеться для перевірки е таблиця чи ні якщо є то створюватись не буде
    {
        String sql="CREATE TABLE IF NOT EXISTS jpu121_randoms("+
                "`id`        CHAR(36) PRIMARY KEY,"+
                "`val_int`   INT,"+
                "`val_str`   VARCHAR(256),"+
                "`val_float` FLOAT"+
                ")";

        try(Statement statement = this.connection.createStatement()) //ADO.NET : SqlCommand
        {
         statement.executeUpdate(sql); //executeUpdate -для запитів без повернення
            System.out.println("OK");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

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