# Code Checker
> a java code static analysis tools 

- **Build Tools**:    gradle
- **dependencies**: javaparser / jdt /junit

###usage
```shell
    gradlew check
```
###result
build/reports/spotbugs/main.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<BugCollection version="3.1.7" sequence="0" timestamp="1538930916075" analysisTimestamp="1538930916286" release="">
  <Project projectName="">..</Project>
  <BugInstance type="DLS_DEAD_LOCAL_STORE" priority="2" rank="17" abbrev="DLS" category="STYLE">
    <Class classname="Test">
      <SourceLine classname="Test" start="10" end="100" sourcefile="Test.java" sourcepath="Test.java"/>
    </Class>
    <Method classname="Test" name="main" signature="([Ljava/lang/String;)V" isStatic="true">
      <SourceLine classname="Test" start="16" end="74" startBytecode="0" endBytecode="96" sourcefile="Test.java" sourcepath="Test.java"/>
    </Method>
    <LocalVariable name="fileInputStream" register="3" pc="131" role="LOCAL_VARIABLE_NAMED"/>
    <SourceLine classname="Test" start="58" end="58" startBytecode="181" endBytecode="181" sourcefile="Test.java" sourcepath="Test.java"/>
    <Property name="edu.umd.cs.findbugs.detect.DeadLocalStoreProperty.DEAD_OBJECT_STORE" value="true"/>
    <Property name="edu.umd.cs.findbugs.detect.DeadLocalStoreProperty.KILLED_BY_SUBSEQUENT_STORE" value="true"/>
    <Property name="edu.umd.cs.findbugs.detect.DeadLocalStoreProperty.LOCAL_NAME" value="fileInputStream"/>
    <Property name="edu.umd.cs.findbugs.detect.DeadLocalStoreProperty.MANY_STORES" value="true"/>
    <Property name="edu.umd.cs.findbugs.detect.DeadLocalStoreProperty.METHOD_RESULT" value="true"/>
  </BugInstance>
  <BugInstance type="HE_EQUALS_USE_HASHCODE" priority="1" rank="14" abbrev="HE" category="BAD_PRACTICE">
    <Class classname="Test">
      <SourceLine classname="Test" start="10" end="100" sourcefile="Test.java" sourcepath="Test.java"/>
    </Class>
    <Method classname="Test" name="equals" signature="(Ljava/lang/Object;)Z" isStatic="false">
      <SourceLine classname="Test" start="83" end="83" startBytecode="0" endBytecode="57" sourcefile="Test.java" sourcepath="Test.java"/>
    </Method>
  </BugInstance>
 ...
  <Errors errors="0" missingClasses="0"></Errors>
  
  <ClassFeatures></ClassFeatures>
  <History></History>
</BugCollection>

```