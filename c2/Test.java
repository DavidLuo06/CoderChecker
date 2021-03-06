package testcode;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author i
 * @time 2018.10.1
 */
public class Test {
    public static Date dNow = new Date( );
    public static SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");


    /**
     * 7. Unused methods.
     */
    public void unusedMethod2(){
        System.out.println("unused method");
    }

    public static void main(String[] args) throws IOException {

        usedMethod();
        //Bugs 2.  Comparison of String objects using == or !=.
        String a="a";
        String b="b";
        System.out.println(a==b);
        System.out.println(a!=b);

        //Bugs 3. Method may fail to close stream on exception.
        FileInputStream fileInputStream =null;
        FileInputStream errorFileInputStream =null;
        try {
            new FileInputStream("Test.java");
            errorFileInputStream = new FileInputStream("Not-Exist-file.java");
            fileInputStream.close();
            errorFileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            errorFileInputStream.close();

        }

        //Bugs 4. Condition has no effect.
        if(true){
            System.out.println(ft.format(dNow));
        } else if(true){
            //asdf
        }else {

        }
        boolean flag=true;
        if (flag){
            System.out.println(ft.format(dNow));
        }

        // Bugs 5. Inadequate logging information in catch blocks.
        try {
            fileInputStream=new FileInputStream("Test.java");

        } catch (RuntimeException e) {
            Logger.getLogger("sssss");
        } catch (IOException e) {
            Logger.getLogger("sssss");
        }


        //Bugs 6.  Unneeded computation in loops
        for (int i = 0; i < 100; i++){
            // unneeded computation
            Math.abs(i);
            System.out.println(i+ 123);
        }
        // Bugs 8. Empty exception.
        try {
            fileInputStream=new FileInputStream("Test.java");

        }catch (Exception e) {
            /*
            * sadfas
            * */
        }

        try {
            fileInputStream=new FileInputStream("Test.java");

        }catch (Exception e) {
            // Bugs 9. Unfinished exception handling code
            // TODO: something
            // FIXME
        }


        // Bugs 10. Over-catching an exception with system-termination
        try {
            fileInputStream=new FileInputStream("Test.java");

        }catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Bugs 1. Class defines equals()
     * @param obj
     * @return super method
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Bugs 1. Class not defines hashCode()
     * @return
     */
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }

    /**
     * 7. Unused methods.
     */
    public void unusedMethod(){
        System.out.println("unused method");
    }

    public static void usedMethod(){
        System.out.println("unused method");
    }

}
