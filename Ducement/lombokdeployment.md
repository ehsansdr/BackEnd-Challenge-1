The error "java: cannot find symbol\nsymbol:  method builder()\nlocation: class com.barook.walletmanager.Entity.User" means that the Lombok @Builder annotation is not being processed correctly by your Java compiler.  Here's a breakdown of the common causes and how to fix them:

1. Lombok Dependency:

Double-check that you have the Lombok dependency correctly included in your pom.xml (Maven) or build.gradle (Gradle) file.

Maven (pom.xml):
XML

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>  <scope>provided</scope>
        </dependency>
Gradle (build.gradle):
Gradle

        dependencies {
            compileOnly 'org.projectlombok:lombok:1.18.30' // Or a recent version
            annotationProcessor 'org.projectlombok:lombok:1.18.30' // Important for annotation processing
        }
2. Annotation Processing:

Lombok uses annotation processing to generate code at compile time.  Make sure that annotation processing is enabled in your IDE (IntelliJ IDEA or Eclipse).

IntelliJ IDEA:

Go to File -> Settings -> Build, Execution, Deployment -> Compiler -> Java Compiler.
Make sure that "Use annotation processors" is checked.
Eclipse:

Go to Project -> Properties -> Java Compiler -> Annotation Processing.
Make sure that "Enable annotation processing" is checked.
3. IDE Restart (Sometimes Necessary):

Sometimes, the IDE's annotation processing gets stuck.  Try restarting your IDE.

4. Clean and Rebuild:

Do a clean and rebuild of your project.  This will force the compiler to reprocess the annotations.

Maven: mvn clean install or mvn clean compile
Gradle: ./gradlew clean build
5. Check Lombok Version Compatibility:

While less common, there might be compatibility issues between your Lombok version, your Java version, and your IDE. Try using a recent, stable version of Lombok.

6. Check for Conflicting Dependencies:

In rare cases, other dependencies might interfere with Lombok.  If you have other annotation processors, try temporarily disabling them to see if that resolves the issue.

7. Verify the @Builder Annotation:

Make absolutely sure that the @Builder annotation is on the correct class (your User entity) and that it's spelled correctly.

8. Check Imports:

Ensure you have the correct import for the @Builder annotation:

Java

import lombok.Builder;
9. Check for other compilation errors:

Sometimes, a seemingly unrelated compilation error can mask other issues.  Address any other compilation errors in your project first.

10. Invalidate Caches and Restart (IntelliJ IDEA):

If you're using IntelliJ IDEA, sometimes invalidating caches and restarting can help:

File -> Invalidate Caches / Restart...
Select "Invalidate and Restart"
If you continue to have problems, please provide:

Your pom.xml or build.gradle file.
Your User entity class.
The exact error message you are seeing.
Your IDE (IntelliJ IDEA, Eclipse, etc.) and its version.
Your Java version.