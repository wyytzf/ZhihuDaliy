package wyy.com.learningproject.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weiyuyang on 16-6-10.
 */
public class DateUtils {
    public static String getCurrentDate(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long l = System.currentTimeMillis();
        String date = sdf.format(l);
        return date;
    }

    public static String getDate(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String d = sdf.format(date);
        return d;
    }
}
