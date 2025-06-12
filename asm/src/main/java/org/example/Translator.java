package org.example;

public class Translator {

    public static String translate(String target, boolean isA) {
        String binaryCode = "111";

        if (isA) {

            if (Character.isDigit(target.charAt(0))) {
                int number = Integer.parseInt(target);
                binaryCode = String.format("%15s", Integer.toBinaryString(number)).replace(' ', '0');
            } else {
                binaryCode= String.format("%15s", Integer.toBinaryString(SymbolTable.getAddress(target))).replace(' ', '0');
            }

            binaryCode = "0" + binaryCode;

        } else {//is C
            String a = "0", dest = "000", comp = "000000", jump = "000";
            String targetComp = "", targetDest = "", targetJump = "";

            if (target.contains(";")) {
                targetJump = target.substring(target.length() - 3);
                targetComp = target.substring(0, target.indexOf(";"));
            } else if (target.contains("=")) {
                targetDest = target.substring(0, target.indexOf("="));
                targetComp = target.substring(target.indexOf("=") + 1);
            } else {
                targetComp = target;
            }

            if (targetComp.contains("M")) a = "1";

            if (targetDest.contains("A")) dest = "1" + dest.substring(1);
            if (targetDest.contains("D")) dest = dest.substring(0, 1) + "1" + dest.substring(2);
            if (targetDest.contains("M")) dest = dest.substring(0, 2) + "1";

            switch (targetJump) {
                case "JGT": jump = "001"; break;
                case "JEQ": jump = "010"; break;
                case "JGE": jump = "011"; break;
                case "JLT": jump = "100"; break;
                case "JNE": jump = "101"; break;
                case "JLE": jump = "110"; break;
                case "JMP": jump = "111"; break;
            }

            switch (targetComp) {
                case "0": comp = "101010"; break;
                case "1": comp = "111111"; break;
                case "-1": comp = "111010"; break;
                case "D": comp = "001100"; break;
                case "A": case "M": comp = "110000"; break;
                case "!D": comp = "001101"; break;
                case "!A": case "!M": comp = "110001"; break;
                case "-D": comp = "001111"; break;
                case "-A": case "-M": comp = "110011"; break;
                case "D+1": comp = "011111"; break;
                case "A+1": case "M+1": comp = "110111"; break;
                case "D-1": comp = "001110"; break;
                case "A-1": case "M-1": comp = "110010"; break;
                case "D+A": case "D+M": comp = "000010"; break;
                case "D-A": case "D-M": comp = "010011"; break;
                case "A-D": case "M-D": comp = "000111"; break;
                case "D&A": case "D&M": comp = "000000"; break;
                case "D|A": case "D|M": comp = "010101"; break;
            }

            binaryCode= binaryCode+ a + comp + dest + jump;
        }
        return binaryCode;
    }


}
