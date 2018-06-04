package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilString {

	public Timestamp convertTimstamp(String str){
		/*
		 * SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
		 */
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
		Date parsedDate;
		try {
			parsedDate = formatter.parse(str);
			Timestamp timestamp = new Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}
		
	}
	public String getDescription(String str) {
		String description = "";
		if(str.matches(".+(</a>).+(<div).+")== true) {
			String regex = "</a>(?<description>.*?)<div";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
		         return matcher.group("description");
		    }
			return description;
		}else if(str.matches(".+(</a>).+")){
			String regex = "</a>(?<description>.*?)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
		         return matcher.group("description");
		    }
			return description;
		}
		return description;
	}
	// substringWord
    public static String substringWord(String str, int position) {
        String strNew = "";
        if (str.length() <= position) {
            strNew = str;
        } else {
            strNew = str.substring(0, position);
            if (!str.substring(position, position + 1).equals(" ") || !str.substring(position, position).equals(" ")) {
                int lastSpaceIndex = strNew.lastIndexOf(" ");
                strNew = strNew.substring(0, lastSpaceIndex).concat("...");
            }
        }
        return strNew;
    }
}
