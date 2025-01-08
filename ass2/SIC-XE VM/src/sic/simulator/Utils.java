package sic.simulator;

import java.io.*;

public class Utils {


    public static String readString(Reader r, int len) throws IOException {
        StringBuilder sb = new StringBuilder();
        while(len-- > 0){
            sb.append((char)r.read());
        }
        return sb.toString();
    }
    public static int readByte(Reader r) throws IOException {
        return Integer.parseInt(readString(r, 2), 16);
        //preberemo dve hex vrednosti, jo pretvorimo!
    }
    public static int readWord(Reader r) throws IOException {
        return Integer.parseInt(readString(r, 6), 16);
        //tu preberemo cel word
    }
    /*
    H prog(ime programa)   000000(zacetna lokacija) 00004E(dolzina)
    T 000000(lokacija) 1E(dolzina segmenta) 032033 1B2033 0F2033 03202A 1F202A 0F202D 032021 272021 0F2027 23201B
    T 00001E 1E 0F2024 032012 1F201E 0F2021 032009 232009 0F2015 3F2FFD 000018 000005
    E 000000 lokacija PC
    */
    public static boolean loadSection(Machine m, Reader r) {
        int location, lenght;
        location = lenght = 0;
        try{
            int prebrano = r.read();
            if(prebrano != 'H'){
                return false;
            }
            //nareto za object dense!!! za slack odkomentiraj spejse pa dodaj kakega se v while
//            r.read(); // space
            readString(r, 6); // prebere ime
//            r.read(); // space
            location = readWord(r); //start
//            r.read(); // space
            lenght = readWord(r); //len
            r.read(); // EOL

            prebrano = r.read(); // T
            while(prebrano == 'T'){
//                r.read(); // space
                int currLocation = readWord(r);
//                r.read(); // space
                int len = readByte(r);
//                r.read(); // space
                int bajt;
                while(len > 0){
                    bajt = readByte(r);
                    if(currLocation < location || currLocation >= location + lenght){
                        return false;
                    }
                    m.mem.setByte(currLocation++, bajt);
                    len--;
                }
                r.read();//eol
                prebrano = r.read();
            }
            while(prebrano != 'E'){
                prebrano = r.read();
            }
            m.regs.setPC(readWord(r));


        } catch (IOException e){
            System.err.println("Error reading section");
            return false;
        }

        return true;
    }
}
