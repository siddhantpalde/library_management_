package org.example.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface InputReader {
    static String getString() {
        return getData();
    }
    static int getNumbers() {
        return Integer.parseInt(getData());
    }
    static double getDoubleNumbers() {
        return Double.parseDouble(getData());
    }
    private static String getData() {
        BufferedReader bufferedReader = null;
        String s = "";
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            s = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Buffered reader exception:"+e);
//        } finally {
//            try {
//                if(bufferedReader != null) {
//                    bufferedReader.close();
//                }
//            } catch (IOException e) {
//                System.out.println("Exception while closing buffered reader");
//            }
        }
        return s;
    }
}
