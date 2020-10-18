package sample.logic;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Data {
    File file = new File("src/sample/resources/data.txt");

    public void writeLoginPassToData(String login, String password) {
        try {
            FileUtils.writeStringToFile(file, login + ":" + password + ";",
                    StandardCharsets.UTF_8.name(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLoginPass(int index) {
        StringBuilder builder = new StringBuilder();
        String temp = "";

        try {
            temp = FileUtils.readFileToString(file, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] array = temp.split(";");

        for (int i = 0; i < array.length; i++) {
            if (i != index) builder.append(array[i] + ";");
        }

        String result = builder.toString();

        try {
            FileUtils.writeStringToFile(file, result, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getEncryptedLogins() {
        List<String> loginLines = new ArrayList<>();
        String logins = "";

        if (file.length() != 0) {
            try {
                logins = FileUtils.readFileToString(file, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] arrLogins = logins.split(";");

            for (String arrLogin : arrLogins) {
                loginLines.add(arrLogin.split(":")[0]);
            }
        }
        return loginLines;
    }

    public List<String> getEncryptedPasses() {
        List<String> passwordLines = new ArrayList<>();
        String pass = "";
        if (file.length() != 0) {
            try {
                pass = FileUtils.readFileToString(file, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] arrLogins = pass.split(";");
            for (String arrLogin : arrLogins) {
                passwordLines.add(arrLogin.split(":")[1]);
            }
        }
        return passwordLines;
    }
}
