
# Password-keeper

##### Program works with 11 version of java or higher

###### To run project in IntelliJ IDEA do these simple steps: 

1. Create javaFX project in IDEA and delete a folder "sample" from "src" folder.
2. Paste the "sample" from this repository instead of deleted.
3. Download JavaFX SDK for your OS from [JavaFX website](https://gluonhq.com/products/javafx/), unzip and save.
4. Add downloaded folder to project like java library in "Project Structure".
Also add "common-io" and "commons-codec" as jar-files .
5. Add next text to "VM options input" in "Edit Configurations":
```bash
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml
``` 
6. Add to your bash_profile PATH TO "JavaFX SDK" that you save earlier something like this:
```bash
export PATH_TO_FX=/Downloads/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib
``` 
7.Then run method main as usual. 
8.The program can be modified. You can take part in improving the program.
