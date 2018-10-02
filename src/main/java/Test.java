import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author i
 * @time 2018.10.1
 */
public class Test {
    public static Date dNow = new Date( );
    public static SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    public static void main(String[] args) {
        boolean flag=true;
        if (true){
            System.out.println(ft.format(dNow));
        }

    }

}
