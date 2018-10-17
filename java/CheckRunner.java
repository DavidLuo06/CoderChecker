package java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


//
public class CheckRunner {
    static ArrayList<String>filenameList = new ArrayList<>();
    static Scanner fileEntry = new Scanner(System.in);
    public static void main() {
        String progLineCurrent;
        String outputFileName = "StyleCheckResults.txt";
        int lineIndexer = 0;
        int lineNum = 1;
        String file = "";
        System.out.println("ENTER A FILE PATH NAME BELOW");
        file = fileEntry.nextLine();

        if(!file.isEmpty()) {
            filenameList.add(file);
            System.out.println("File: " + file + "\n\n");
            System.out.println("ENTER A FILE PATH NAME BELOW\n");
            file = fileEntry.nextLine();
        }
        else{
            throw new InputMismatchException("File is empty: ");
        }
        System.out.println("Input finished...");
        System.out.println("Now processing file for style errors..");
        System.out.println("_____Files to process____");
        for (int i = 0; i < filenameList.size(); i++) {
            System.out.println(": " + filenameList.get(i));
        }
        //CHECKER OBJECT INITIALIZATIONS
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ClassHashCodeChecker bug1Checker = new ClassHashCodeChecker();
        StringComparisonChecker bug2Checker = new StringComparisonChecker();
        MethodCloseStreamChecker bug3Checker = new MethodCloseStreamChecker();
        ConditionChecker bug4Checker = new ConditionChecker();
        CatchBlockLoggingChecker bug5Checker = new CatchBlockLoggingChecker();
        LoopsChecker  bug6Checker= new LoopsChecker();
        UnusedMethodChecker  bug7Checker = new UnusedMethodChecker();
        EmptyExceptionChecker bug8Checker = new EmptyExceptionChecker();
        UnfinishedExceptionChecker bug9Checker = new UnfinishedExceptionChecker();
        OverCatchingChecker bug10Checker = new OverCatchingChecker();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        StyleChecker styleChecker;
        for (int j = 0; j < filenameList.size(); j++) {
            try {
                styleChecker = new StyleChecker(filenameList.get(j), new FileReader(filenameList.get(j)));
                styleChecker.fillListWithProgLines();
                for (int i = 0; i < StyleChecker.progLines.size(); i++) {
                    progLineCurrent = StyleChecker.progLines.get(i);
                    lineIndexer = i;
                    bug1Checker.getError(progLineCurrent,lineIndexer,lineNum);
                    bug2Checker.getError(progLineCurrent,lineIndexer,lineNum);
                    bug3Checker.getError(progLineCurrent, lineIndexer, lineNum);
                    bug5Checker.getError(progLineCurrent, lineIndexer,lineNum);
                    bug6Checker.getError(progLineCurrent, lineIndexer,lineNum);
                    bug7Checker.getError(progLineCurrent,lineIndexer,lineNum);
                    bug8Checker.getError(progLineCurrent,lineIndexer,lineNum);
                    bug9Checker.getError(progLineCurrent,lineIndexer,lineNum);
                    bug10Checker.getError(progLineCurrent,lineIndexer,lineNum);

                    lineNum += 1;
                }
                styleChecker.outputFileReport(outputFileName);
            } catch (FileNotFoundException f) {
                System.out.println("File not found.");
                f.printStackTrace();
            }
        }
    }
}