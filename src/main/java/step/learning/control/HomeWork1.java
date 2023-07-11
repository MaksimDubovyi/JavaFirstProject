package step.learning.control;

public class HomeWork1 {
    public void run() {

        int[]arr1= new int []{2,3,4,5,6,7,8,9,10};
        int[][]showArray=new int [9][9];
        for(int i=0;i<arr1.length;i++)
            for(int j=0;j<arr1.length;j++)
                showArray[i][j]=arr1[i]*arr1[j];

        System.out.print("----- start Реалізувати виведення таблиці множення  \n");


        System.out.print("     | 2 | 3 | 4 | 5 | 6 | 7 | 8| 9 | 10|\n");
        System.out.print("     _________________________________________\n");
        for(int i=0;i<arr1.length;i++)
        {
            if(arr1[i]<10)
            System.out.print("  "+arr1[i]+"  | ");
            else
                System.out.print("  "+arr1[i]+" | ");

            for(int j=0;j<arr1.length;j++)
            {
                 if(showArray[i][j]>9)
                System.out.print(" "+showArray[i][j]+" ");
                 else
                     System.out.print("  "+showArray[i][j]+" ");

            }
            if(arr1[i]<10)
            System.out.print("  |\n");
            else
                System.out.print(" |\n");
        }
        System.out.print("     -----------------------------------------\n");
        System.out.print("----- stop Реалізувати виведення таблиці множення  \n\n\n");

    }
}
