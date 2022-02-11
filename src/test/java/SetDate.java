import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetDate {
    public String getDate(int add) {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        cal.add(cal.DATE, add);
        date = cal.getTime();
        return formatForDateNow.format(date);
    }
}
