package client;

import gui.SceneControl;
import javafx.application.Platform;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataReloader {
    private final ExecutorService executorService;

    public DataReloader() {
        executorService = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
    }

    public void runBackgroundReloading() {
        executorService.submit(() -> {
            try {
                while (!Thread.interrupted()) {
                    Platform.runLater(() -> {
                        try {
                            SceneControl.getBackendInteractor().refreshData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {}
        });
    }

    public void stopReloading() {
        executorService.shutdownNow();
    }
}
