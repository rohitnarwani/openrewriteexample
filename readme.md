1. Setup OpenRewrite in Your Project
For Maven Projects
Step 1.1: Add OpenRewrite Plugin
Add the latest OpenRewrite Maven plugin to your pom.xml:


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


Step 1.2: Add OpenRewrite Recipes for Java Migration
In your pom.xml, add the Java migration recipe dependency:


<dependency>
    <groupId>org.openrewrite.recipe</groupId>
    <artifactId>rewrite-migrate-java</artifactId>
    <version>2.1.0</version>
</dependency>


2. Define an OpenRewrite Recipe for JDK 8 to 17 Migration
Create a rewrite.yml file in the root of your project and add the following recipe:


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
