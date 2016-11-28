package com.example.scotty.realdistance;

/**
 * Created by scotty on 11/27/2016.
 */

public class PreviousDay {

    public String giveMeThePreviousDay(String CurrentDay){
        String prev = "";
        String[] parse= CurrentDay.split(" ");
        String DAY = parse[1];
        DAY = DAY.replace(',', ' ');
        DAY = DAY.trim();
        if (!DAY.equals("1")){
            int day = Integer.parseInt(DAY)-1;
            prev = parse[0]+ " " + day + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Dec")){
            prev = "Nov"+ " " + "30" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Nov")){
            prev = "Oct"+ " " + "31" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Oct")){
            prev = "Sept"+ " " + "30" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Sept")){
            prev = "Aug"+ " " + "31" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Aug")){
            prev = "July"+ " " + "31" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("July")){
            prev = "Jun"+ " " + "30" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Jun")){
            prev = "May"+ " " + "31" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("May")){
            prev = "Apr"+ " " + "30" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Apr")){
            prev = "Mar"+ " " + "31" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Mar")){
            prev = "Feb"+ " " + "28" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Feb")){
            prev = "Jan"+ " " + "31" + ", " + parse[2];
            return prev;
        }
        else if (parse[0].equals("Jan")){
            int year = Integer.parseInt(parse[2])-1;
            prev = "Dec"+ " " + "31" + ", " + year;
            return prev;
        }
        return prev;
    }
}
