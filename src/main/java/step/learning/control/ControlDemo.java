package step.learning.control;
//import step.learning.control.HomeWork1;
public class ControlDemo {
    public void run()
    {



        new HomeWork1().run();
        System.out.println("ControlDemo run");
        /*
        Типи даних: reference-type, але є primitives
        * */
        byte    xb;   //    всі числові типи - знакові
        short   xs;   //    без знакових не існує
        int     xi;   //     0000 0001 ==1(byte)
        long    xl;   //     1111 1111 ==-1(byte)

        float   xf;
        double  xd;
        char    ch;
        boolean b;
       // з примітивними типами можуть виникати обмеження,
        // наприклад, при організації колекцій чи інших generic<T>
        //
        Byte yb=1;
        Short ys=2;
        Integer yi=3;
        Long yl=4l;
        Float yf=0.1F;
        Double yd =0.01;
        Character c='A';
        Boolean bb=true;
        //масиви


        int[]arr1={1,2,3,4,5,6};
        int[]arr2= new int []{1,2,3,4,5,6};
        int[]arr3=new int [5];

        for(int i=0;i<arr1.length;i++)
        {
            System.out.print(" "+arr1[i]);
        }
        System.out.println();

        for(int x:arr2) {//foreach
            System.out.println(" " + x);
        }
        System.out.println();



        // String
        String str1 = "Hello";  // String pool - одне значення стає
        String str2 = "Hello";  // одним об'єктом
        String str3 = new String("Hello");
        String str4 = new String("Hello");
        if( str1 == str2 ) {  // через пул рядків це насправді один і той самий об'єкт
            System.out.println("str1 == str2");
        }
        else {
            System.out.println("str1 != str2");
        }
        if( str3 == str4 ) {  // об'єкти рівні тільки якщо це один об'єкт (за посиланням)
            System.out.println("str3 == str4");
        }
        else {
            System.out.println("str3 != str4");
        }
        if( str3.equals( str4 ) ) {  // порівняння за контентом
            System.out.println("str3 equals str4");
        }
        else {
            System.out.println("str3 !equals str4");
        }
    }
}
/*
Control flow instructions - шнструкції управління виконання
оператори умовного та ціклічного виконання, а також переходів
між інструкціями
* */
