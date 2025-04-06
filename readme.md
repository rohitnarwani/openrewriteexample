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

FAQ
1. Q: What exactly is OpenRewrite?

A: OpenRewrite is an open-source, automated code refactoring tool. It parses your source code into a detailed Abstract Syntax Tree (AST) – specifically, its own Lossless Semantic Tree (LST) format – applies programmed transformations (called "recipes") to this tree, and then regenerates the modified source code while preserving the original formatting as much as possible. It's designed for making large-scale, consistent changes across many files or repositories.

2. Q: Why should I use OpenRewrite instead of just find/replace or IDE refactoring?

A:

vs. Find/Replace: OpenRewrite understands code semantically. It knows about types, methods, scopes, imports, etc. Simple text replacement (like regex) doesn't have this understanding and can easily make mistakes or miss cases.
vs. IDE Refactoring: IDE refactoring is great for targeted changes while you're coding. OpenRewrite excels at applying the same change consistently across hundreds or thousands of files automatically, often as part of a build process, which is impractical with an IDE. It's built for automation and scale.
3. Q: What are "Recipes"?

A: Recipes are the core of OpenRewrite – they define the specific refactoring operations to be performed. They can range from simple changes (like adding @Override annotations) to complex transformations (like migrating from JUnit 4 to JUnit 5, upgrading Spring Boot versions, or patching security vulnerabilities). Recipes can be written in Java (imperative) or defined declaratively in YAML files (rewrite.yml).

4. Q: How do I tell OpenRewrite which recipes to run?

A: The most common way is using a rewrite.yml file, typically placed in your project's root directory or a .rewrite/ subdirectory. This YAML file lists the recipes (or other YAML recipes composed of multiple recipes) you want to activate. Alternatively, you can configure activeRecipes directly within the OpenRewrite plugin configuration in your pom.xml (Maven) or build.gradle (Gradle), though YAML is often preferred for better organization.

5. Q: How do I actually run OpenRewrite on my project?

A: You typically use a build plugin:

Maven: Add the rewrite-maven-plugin to your pom.xml. Run goals like mvn rewrite:dryRun (to see proposed changes) and mvn rewrite:run (to apply changes).
Gradle: Add the rewrite-gradle-plugin to your build.gradle. Run tasks like gradlew rewriteDryRun (to see proposed changes) and gradlew rewriteRun (to apply changes).
There's also a Command Line Interface (CLI), but build tool integration is more common for typical project workflows.
6. Q: Why aren't my files being changed when I run OpenRewrite?

A: Several possibilities:

Dry Run: You might be running rewrite:dryRun or rewriteDryRun, which only shows changes, it doesn't apply them. Use rewrite:run or rewriteRun to modify files.
No Applicable Code: The recipe might not find any code patterns it's designed to change in your project.
Recipe Not Active: The specific recipe wasn't listed correctly in your rewrite.yml or build file configuration. Check for typos.
Recipe Conditions Not Met: Some recipes only apply under certain conditions (e.g., migrating a switch statement requires it to be safely convertible).
Configuration Issues: Errors in your rewrite.yml format or plugin setup. Check build logs.
Target Version Incompatibility: Trying to apply a recipe that introduces features newer than your project's configured target Java version (e.g., adding Java 17 features to a Java 11 project).
Scope: The plugin might be configured to only run on main sources (src/main/java) and the code you want to change is in test sources (src/test/java), or vice-versa.
7. Q: Is it safe? Will OpenRewrite mess up my code?

A: OpenRewrite recipes are generally designed to make safe, behavior-preserving changes based on semantic understanding. However:

Complexity: Refactoring complex code always carries some risk.
Testing is Crucial: ALWAYS run your project's full test suite after applying OpenRewrite changes.
Version Control: ALWAYS commit your code to version control before running rewrite:run or rewriteRun so you can easily revert if needed.
Dry Run First: Use dryRun to preview changes before applying them.
8. Q: Can OpenRewrite upgrade my Java version or migrate frameworks (e.g., Spring Boot)?

A: Yes! This is one of its major strengths. There are dedicated recipes and modules for tasks like:

Migrating between Java versions (e.g., 8 to 11, 11 to 17) using recipes like org.openrewrite.java.migrate.JavaVersion.
Upgrading Spring Boot versions (org.openrewrite.java.spring.boot* recipes).
Migrating testing frameworks (JUnit 4 to 5).
Updating dependencies and fixing CVEs. Check the OpenRewrite Recipe Catalog for available migration paths.
9. Q: Where can I find available recipes?

A: The official OpenRewrite documentation hosts a Recipe Catalog which lists publicly available recipes, describes what they do, and shows how to configure them.

10. Q: Can I write my own custom recipes?

**A:** Yes. You can write recipes in Java using the OpenRewrite API (extending `Recipe`, using Visitors to traverse and modify the LST). This allows you to automate refactoring specific to your organization's codebase or standards. It requires understanding the OpenRewrite LST model and visitor pattern.
11. Q: OpenRewrite seems slow on my large project. How can I speed it up?

**A:** Large projects can take time to parse and process.
* **Be Specific:** Only activate the recipes you actually need for a given run. Avoid running overly broad recipes unnecessarily.
* **Memory:** Ensure the build process has enough memory allocated (e.g., via `MAVEN_OPTS` for Maven or `org.gradle.jvmargs` in `gradle.properties` for Gradle).
* **Caching:** Subsequent runs are often faster due to caching, but the initial parse can be intensive.
* **Up-to-date Versions:** Use recent versions of the OpenRewrite plugin and recipes
