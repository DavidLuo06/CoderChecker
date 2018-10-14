package checker;

import com.github.javaparser.ast.CompilationUnit;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/***
 * This is a basic Checker class , any checker must extend this
 * class and run in runner.CodeChecker
 */
public abstract class BaseChecker {

    public static CompilationUnit cu;
    public static String title;
    public static ArrayList<String> bug = new ArrayList();
    public Adapter adapter;

    public BaseChecker(){

    }

    public static void setCU(CompilationUnit cu2) {
        cu = cu2;
    }

    public static void setTitle(String ti) {
        title = ti;
    }

    public void parse(Adapter ada) {
        adapter = ada;
        check();
    }

    abstract void check();

    protected void error(String bug) {
        this.bug.add(title+"=>"+bug);
    }

    /**
     * Outputs a collective style error report to a file.
     */
    public static void outputFileReport(String outputFile){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date todaysDate = new Date();
        try{
            FileWriter fileWriter = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("STYLE CHECKER Vers 1.0\n");
            printWriter.printf("STYLE REPORT");
            printWriter.print(String.format("\nReport generated on: "+dateFormat.format(todaysDate)));
            printWriter.printf("\n===========================================================================\n");
            if(hasNoErrors()){
                printWriter.print("No TRACEABLE style errors were found.");
            }
            for (Iterator<String> itr = bug.iterator(); itr.hasNext();) {
                String errorMsg = itr.next();
                printWriter.printf(errorMsg+"\n");
            }
            printWriter.close();
        }catch (IOException e){
            System.out.println("Error writing to file");
        }
    }

    private static boolean hasNoErrors(){
        if(bug.isEmpty()){
            return true;
        }
        return false;
    }
}
