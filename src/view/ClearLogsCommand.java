package view;

import java.io.*;

public class ClearLogsCommand extends Command {
    int nrOfLogs;
    public ClearLogsCommand(String key, String desc, int nrOfLogs){
        super(key, desc);
        this.nrOfLogs = nrOfLogs;
    }
    @Override
    public void execute() {
        String fileNameTemplate = "logs";

        for (int i = 1; i <= nrOfLogs; i++) {
            String fileName = fileNameTemplate;
            fileName += i + ".txt";
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
                writer.write("");
                writer.close();
            }
            catch (IOException e){
                System.out.println("file: " + fileName + " not found");
            }
        }
    }
}
