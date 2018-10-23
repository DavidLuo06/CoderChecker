import checker.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;

public class Tester {

    @Test
    public void test_001() {
        BaseChecker.bug.clear();
        loopFiles("c2", new HashChecker());
        assertEquals("hash checker error count not equals", 1, BaseChecker.bug.size());
    }

    @Test
    public void test_002() {
        BaseChecker.bug.clear();
        loopFiles("c2", new EmptyExceptionChecker());
        assertEquals("emptyException checker error count not equals", 2, BaseChecker.bug.size());
    }

    @Test
    public void test_003() {
        BaseChecker.bug.clear();
        loopFiles("c2", new FileStreamCloseChecker());
        assertEquals("file stream checker error count not equals", 5, BaseChecker.bug.size());
    }

    @Test
    public void test_004() {
        BaseChecker.bug.clear();
        loopFiles("c2", new IfChecker());
        assertEquals("if checker error count not equals", 3, BaseChecker.bug.size());
    }

    @Test
    public void test_005() {
        BaseChecker.bug.clear();
        loopFiles("c2", new LogInfoChecker());
        assertEquals("log info checker error count not equals", 1, BaseChecker.bug.size());
    }

    @Test
    public void test_006() {
        BaseChecker.bug.clear();
        loopFiles("c2", new LoopChecker());
        assertEquals("loop checker error count not equals", 0, BaseChecker.bug.size());
    }

    @Test
    public void test_007() {
        BaseChecker.bug.clear();
        loopFiles("c2", new OverCatchExceptionChecker());
        assertEquals("overcatch checker error count not equals", 1, BaseChecker.bug.size());
    }

    @Test
    public void test_008() {
        BaseChecker.bug.clear();
        loopFiles("c2", new StringEqualChecker());
        assertEquals("string equal checker error count not equals", 2, BaseChecker.bug.size());
    }

    @Test
    public void test_009() {
        BaseChecker.bug.clear();
        loopFiles("c2", new UnfinishedExceptionChecker());
        assertEquals("unfinished Exception checker error count not equals", 1, BaseChecker.bug.size());
    }

    @Test
    public void test_010() {
        BaseChecker.bug.clear();
        loopFiles("c2", new UnusedChecker());
        assertEquals("unused checker error count not equals", 0, BaseChecker.bug.size());
    }

    public static void loopFiles(String path, BaseChecker baseChecker) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        loopFiles(file2.getAbsolutePath(), baseChecker);
                    } else {
                        if(file2.getName().contains(".java")) {
                            checkFile(file2, baseChecker);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static void checkFile(File file, BaseChecker baseChecker) {
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

        baseChecker.parse(adapter);


    }
}
