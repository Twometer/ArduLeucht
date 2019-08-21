package de.twometer.arduleucht.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleProcess {

    private Process process;

    private BufferedReader stdIn;

    private Listener listener;

    private ConsoleProcess(Process process) {
        this.process = process;
        this.stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
        startReading();
    }

    public static ConsoleProcess create(String commandLine) throws IOException {
        Process process = Runtime.getRuntime().exec("cmd /c " + commandLine);
        return new ConsoleProcess(process);
    }

    public void waitFor() throws InterruptedException {
        process.waitFor();
    }

    private void startReading() {
        new Thread(() -> {
            try {
                String line;
                while ((line = stdIn.readLine()) != null) {
                    if (listener != null)
                        listener.stdin(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, "ReaderThread").start();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        void stdin(String line);

    }
}
