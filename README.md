# Vaadin + openEHR Demo

This project demonstrates how to combine the **openEHR SDK** with **Vaadin** to create a clinical forms application.

## 🚀 Running the Application
You need at least **Java 17** and **Maven** to build the project.  

After checking out the code, run:
```sh
mvn clean package
```
to build the project.

We recommend using **IntelliJ Community Edition** to work with the code.

---

## ⚙️ openEHR Code Generator

This example relies on the [openEHR SDK](https://github.com/ehrbase/openEHR_SDK).  
Using the **Maven Plugin**, it automatically generates Java classes from an **openEHR Template**.
You find this plugin example in the pom.xml in the root folder of the project.

The example demonstrates how this works for a template with **respiratory frequency ("Atemfrequenz")**.

### **Maven Plugin Configuration**
```xml
<plugin>
    <groupId>org.ehrbase.openehr.sdk</groupId>
    <artifactId>generator-maven-plugin</artifactId>
    <version>${ehrbase.openehr.sdk.version}</version>
    <configuration>
        <!-- default is 'true' -->
        <addNullFlavor>true</addNullFlavor>
        <!-- default is 'false' -->
        <choicesForSingleEvent>false</choicesForSingleEvent>
        <!-- ALL, NONE, SECTION, default is 'SECTION' -->
        <optimizerSetting>SECTION</optimizerSetting>
        <!-- optional, takes generators defaults if not set -->
        <replaceChars>
            <ö>oe</ö>
            <ä>ae</ä>
        </replaceChars>
        <!-- mandatory to have at least one template file configured -->
        <templateFiles>
            <templateFile>src/main/java/org/vaadin/example/template/Atemfrequenz.opt</templateFile>
            <templateFile>src/main/java/org/vaadin/example/template/implant_registry_v0.0.1.opt</templateFile>
        </templateFiles>
        <!-- mandatory -->
        <packageName>org.vaadin.example.template</packageName>
    </configuration>
</plugin>
```

### 🛠 Running the Generator
Execute the **generator plugin** in Maven to create the Java classes:
```sh
mvn generator:codegen
```
After running the generator, you may need to **move the generated files** to the appropriate folder in your project.

In the ExampleFormService.java you can see how the generated classes are used along with vaadin form elements to create an 
openEHR composition.
