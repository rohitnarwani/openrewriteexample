1. Setup OpenRewrite in Your Project
For Maven Projects
Step 1.1: Add OpenRewrite Plugin
Add the latest OpenRewrite Maven plugin to your pom.xml:

```xml
<plugin>
    <groupId>org.openrewrite.maven</groupId>
    <artifactId>rewrite-maven-plugin</artifactId>
    <version>5.11.1</version>
    <executions>
        <execution>
            <goals>
                <goal>run</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Step 1.2: Add OpenRewrite Recipes for Java Migration
In your pom.xml, add the Java migration recipe dependency:

```xml
<dependency>
    <groupId>org.openrewrite.recipe</groupId>
    <artifactId>rewrite-migrate-java</artifactId>
    <version>2.1.0</version>
</dependency>
```

2. Define an OpenRewrite Recipe for JDK 8 to 17 Migration
Create a rewrite.yml file in the root of your project and add the following recipe:
```yml

type: specs.openrewrite.org/v1beta/recipe
name: com.example.UpgradeJava8ToJava17
displayName: Upgrade Java 8 to Java 17
description: This recipe upgrades the project from Java 8 to Java 17 by applying necessary migrations.
recipeList:
  - org.openrewrite.java.migrate.UpgradeToJava17
  - org.openrewrite.java.migrate.JavaVersion17
  - org.openrewrite.java.migrate.UpgradeDeprecatedApis
  - org.openrewrite.java.cleanup.RemoveUnusedImports
  - org.openrewrite.java.cleanup.ExplicitTypeToVar
  - org.openrewrite.java.cleanup.FinalizeLocalVariables
```
3. CommanStaticAnalysis
```yml
type: specs.openrewrite.org/v1beta/recipe
name: com.example.CommonStaticAnalysis
displayName: Common Static Code Analysis
description: Applies static code analysis and best practices.
recipeList:
  - org.openrewrite.java.cleanup.RemoveUnusedImports
  - org.openrewrite.java.cleanup.ExplicitTypeToVar
  - org.openrewrite.java.cleanup.UnnecessaryThrows
  - org.openrewrite.java.cleanup.NoDoubleBraceInitialization
  - org.openrewrite.java.cleanup.RemoveUnusedLocalVariables
  - org.openrewrite.java.cleanup.SimplifyBooleanExpressions
  - org.openrewrite.java.cleanup.UseStringIsEmpty
  - org.openrewrite.java.cleanup.IndexOfChecksShouldUseAConstant
  - org.openrewrite.java.cleanup.UseDiamondOperator
  - org.openrewrite.java.cleanup.Cleanup
  - org.openrewrite.java.security.FindTextUseOfCreditCardNumber

   ```

Commands
```
mvn rewrite:run -Drewrite.configLocation=./rewrite.yml
```
```
mvn rewrite:run -Drewrite.activeRecipes=com.example.CommonStaticAnalysis
```
```
mvn rewrite:run -Drewrite.activeRecipes=org.openrewrite.java.migrate.UpgradeToJava17
```
```
mvn rewrite:dryRun -Drewrite.configLocation=rewrite.yml -X
```
Jdk17
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.openrewrite.maven</groupId>
            <artifactId>rewrite-maven-plugin</artifactId>
            <version>5.40.0</version>
            <configuration>
                <activeRecipes>
                    <recipe>org.openrewrite.java.migrate.Java8toJava17</recipe>
                </activeRecipes>
            </configuration>
            <executions>
                <execution>
                    <id>run-rewrite</id>
                    <phase>process-sources</phase>
                    <goals>
                        <goal>run</goal>
                    </goals>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>org.openrewrite.recipe</groupId>
                    <artifactId>rewrite-migrate-java</artifactId>
                    <version>2.9.0</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```
