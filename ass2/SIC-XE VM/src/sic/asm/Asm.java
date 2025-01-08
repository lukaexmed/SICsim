package sic.asm;

import java.io.*;
import java.util.Arrays;

import sic.asm.code.Code;
import sic.asm.code.SemanticError;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class Asm {

    public static String readFile(File file) {
        byte[] buf = new byte[(int) file.length()];
        try {
            InputStream s = new FileInputStream(file);
            try {
                s.read(buf);
            } finally {
                s.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(buf);
    }


    public static void main(String[] args) {
//        String filename = "inp/arith.asm";
        String filename = args[0];
        String input;
        byte[] buff = new byte[4096];

        // TODO
        File file = new File(filename);
        input = readFile(file);
//        System.out.println("Pot: " + file.getAbsolutePath());
//        System.out.println("Obstaja " + file.exists());
//        System.out.println("Berljiva: " + file.canRead());
//        System.out.println("Dolzina: " + file.length());
//
//        input = "prog  \tSTART  0\n" +
//                "\tLDA x\t\t";


        Parser parser = new Parser();
        Code code;
        //prvi prehod
        try {
            code = parser.parse(input);
//            code.print();
        } catch (SyntaxError e) {
            System.err.println(e);
            System.exit(1);
            return;
        } catch (SemanticError e) {
            System.err.println(e);
            System.exit(1);
            return;
        }

        //prvi prehod, dodajanje znakov v symtab!
        code.begin();
        code.prviPrehod();
      //System.out.print(code.symtab.toString());//simboli imajo location counter v kurcu!
        code.end();


        //drugi prehod
        code.begin();
        try {
            code.resolve();
        } catch (SemanticError e) {
            System.err.println(e);
            System.exit(1);
            return;
        }
        code.end();
        //zapis v spomin;
        code.begin();
        buff = code.emitCode();
        code.end();

        //zapis v lst in obj
        String fileGetName = file.getName();
        fileGetName = fileGetName.substring(0, fileGetName.lastIndexOf("."));
        RandomAccessFile obj;
        RandomAccessFile lst;
        String outputDir = "../inp/";
        try {
            obj = new RandomAccessFile(outputDir+ fileGetName+".obj","rw");
            lst = new RandomAccessFile(outputDir+fileGetName+".lst","rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        code.begin();
        try {
            obj.writeChars(code.obj(buff));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.print(code.lst(buff));
        //System.out.println(code.obj(buff));
        try {
            lst.writeChars(code.lst(buff));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        code.end();

        code.begin();

        code.end();
    }

}
