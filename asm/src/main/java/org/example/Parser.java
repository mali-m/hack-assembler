package org.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Parser {

    public static void firstPass(InputStream input) {
        Scanner sc = new Scanner(input);
        int cln = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner nscnr = new Scanner(line);
            try {
                if (!nscnr.hasNext()) continue;//if empty

                String t1 = nscnr.next();
                if (t1.charAt(0) == '/') continue;

                if (t1.charAt(0) == '(') {
                    String symbol = t1.substring(1, t1.length() - 1);
                    SymbolTable.addEntry(symbol, cln);
                } else {
                    cln++;
                }

            } finally {
                nscnr.close();
            }
        }

        sc.close();
    }

    public static void secondPass(InputStream input, OutputStream output) {
        Scanner sc = new Scanner(input);
        int cln = 16;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner nscnr = new Scanner(line);

            try {
                if (!nscnr.hasNext()) continue;

                String t1 = nscnr.next();// we will pass t1 without any change if this is a C inst.
                boolean isA=false;//is C
                if (t1.charAt(0) == '/' || t1.charAt(0) == '(') continue;


                if(t1.charAt(0)=='@'){
                    isA=true;
                    t1=t1.substring(1);
                    if (!SymbolTable.contains(t1)) {
                        SymbolTable.addEntry(t1, cln);
                        cln++;
                    }
                }
                //if C do not change anything

                String binary = Translator.translate(t1, isA);


                output.write((binary + "\n").getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                nscnr.close();
            }
        }

        sc.close();
    }
}
