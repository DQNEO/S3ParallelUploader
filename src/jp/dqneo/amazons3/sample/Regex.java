package jp.dqneo.amazons3.sample;

/* Java RegExp samle code */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Regex {

    public static void main(String args[]){
        String str = "2009year";

        String regex = "(\\d+)(y)";
        Pattern p1 = Pattern.compile(regex);

        Matcher m = p1.matcher(str);

        if (m.find()){
            System.out.println("whole:" + m.group());
            for (int i = 1 ; i <= m.groupCount(); i ++){
                System.out.println("[Group" + i + "] " + m.group(i));
            }
        }else{
            System.out.println("× " + str);
        }
  }
}