import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class StyleChecker {
    BufferedReader programReader;
    FileReader fr;
    String filename;
    static ArrayList<String> errorRecord = new ArrayList<>();
    static ArrayList<String>progLines = new ArrayList<>();

    /*
        Default constructor.
     */
    public StyleChecker(){
        this.filename = "";
    }

    /**Creates an instance of the style checker with a given file name to check for style errors.
     */
    public StyleChecker(String filename, FileReader fileReader){
        this.filename = filename;
        this.fr = fileReader;
        this.programReader = new BufferedReader(fileReader);
    }

    /**
     * Outputs a collective style error report to a file.
     */
    public void outputFileReport(String outputFile){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date todaysDate = new Date();
        //ADD COMMENT EXTRACTION HERE
        try{
            FileWriter fileWriter = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("AUTHOR: AARON LOOMIS\nSTYLE CHECKER Vers 1.0\n");
            printWriter.printf("STYLE REPORT");
            printWriter.print(String.format("\nReport generated on: "+dateFormat.format(todaysDate)));
            printWriter.printf("\n===========================================================================\n");
            if(hasNoErrors()){
                printWriter.print("No TRACEABLE style errors were found.");
            }
            for (Iterator<String> itr = errorRecord.iterator(); itr.hasNext();) {
                String errorMsg = itr.next();
                printWriter.printf(errorMsg+"\n");
            }
            printWriter.close();
        }catch (IOException e){
            System.out.println("Error writing to file");
        }
    }

    /**
     * Concatenates a message with the line number and type of error.
     * @return: Message that contains the lines number and the type of error.
     */
    public void errorTrace(String lineNumber, String styleErrorMessage){
        String errorMsg = lineNumber+styleErrorMessage;
        errorRecord.add(errorMsg);
    }

    /*
        Adds each line of the program to an ArrayList which allows for easier access of the next and previous lines.
     */
    public void fillListWithProgLines(){
        String progLine;
        try {
            fr= new FileReader(filename);
            programReader = new BufferedReader(fr);
            while ((progLine = programReader.readLine()) != null) {
                progLines.add(progLine);
            }
        }catch (FileNotFoundException f){
            System.out.println("File not found.");
            f.printStackTrace();
        }catch(IOException fileReadError){
            System.out.println("Error reading file.");
            fileReadError.printStackTrace();
        }
    }

    /*
        Returns the line number of the start of a multi line comment. Returns 0 if no multi line comments are found
     */
    public int getStartOfMultiLineComment(){
        int startLine;
        for(int i = 0; i < progLines.size(); i++){
            if(progLines.get(i).contains("/*")){
                startLine = i;
                return  startLine;
            }
        }
        return 0; //no multiline comments
    }

    /*
        Returns the line number of the start of a multi line comment. Returns 0 if no multi line comments are found
     */
    public int getEndOfMultiLineComment(){
        int endLine;
        for(int i = 0; i < progLines.size(); i++){
            if(progLines.get(i).contains("*/")){
                endLine = i;
                return  endLine;
            }
        }
        return 0; //no multiline comments
    }

    /*
        Returns true if the program had no traceable style errors.
     */
    private boolean hasNoErrors(){
        if(errorRecord.isEmpty()){
            return true;
        }
        return false;
    }

    public BufferedReader getProgramReader(){
        return programReader;
    }

    public String getFilename(String filename){
        return this.filename;
    }

    public ArrayList getProgLinesAsList(){
        return progLines;
    }


}