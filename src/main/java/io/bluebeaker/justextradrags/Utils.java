package io.bluebeaker.justextradrags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
    public static List<Integer> getIntsFromString(String string){
        try {
            List<Integer> integers = new ArrayList<>();
            if(string.contains("-")){
                String[] split = string.split("-");
                int start = Integer.parseInt(split[0]);
                int end = Integer.parseInt(split[1]);
                for (int i=start;i<=end;i++){
                    integers.add(i);
                }
            }else {
                integers.add(Integer.parseInt(string));
            }
            return integers;
        }catch (NumberFormatException e){
            JustExtraDrags.getLogger().error("Error parsing numbers: ",e);
            return Collections.emptyList();
        }
    }
    public static List<Integer> getIntsFromCommaSeparatedString(String string){
        List<Integer> integers = new ArrayList<>();
        for (String s : string.split(",")) {
            integers.addAll(getIntsFromString(s));
        }
        return integers;
    }
}
