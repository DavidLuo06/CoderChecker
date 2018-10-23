package runner;

import checker.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class CodeChecker {

    private static String outputFileName = "StyleCheckResults.txt";

    private static int count = 1;
    public static void main(String[] args) {


        System.out.println("running Code Checker by javaParser..");
        loopFiles("codeSource/hadoop-branch-3.0.0");
        loopFiles("codeSource/cloudstack-4.9");
        loopFiles("c2");
        BaseChecker.outputFileReport(outputFileName);



    }

    public static void loopFiles(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        loopFiles(file2.getAbsolutePath());
                    } else {
                        if(file2.getName().contains(".java")) {
                            checkFile(file2);
                            System.out.print("\r"+count+" java files checked, "+ BaseChecker.bug.size()+"bugs found.");
                            count++;
                        }
                    }
                }
            }
        } else {
            System.out.println("File not exist!");
        }
    }

    public static void checkFile(File file) {
        if(!file.exists()) {
            System.out.println("file not exist");
        }
        Adapter adapter = new Adapter();

        CompilationUnit unit = null;
        try {
            unit = JavaParser.parse(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseProblemException e) {
            return;
        }
        unit.accept(adapter, null);

        BaseChecker.setCU(unit);
        BaseChecker.setTitle(file.getName());
        //bug1
        new HashChecker().parse(adapter);
        //bug2
        new StringEqualChecker().parse(adapter);
        //bug3
        new FileStreamCloseChecker().parse(adapter);
        //bug4
        new IfChecker().parse(adapter);
        //bug5
        new LogInfoChecker().parse(adapter);
        //bug6
        new LoopChecker().parse(adapter);
        //bug7
        new UnusedChecker().parse(adapter);
        //bug8
        new EmptyExceptionChecker().parse(adapter);
        //bug9
        new UnfinishedExceptionChecker().parse(adapter);
        //bug10
        new OverCatchExceptionChecker().parse(adapter);


    }


}
