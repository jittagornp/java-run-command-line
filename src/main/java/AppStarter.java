/*
 * Copyright 2021-Current jittagornp.me
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

/**
 * @author jitta
 */
public class AppStarter {

    public static void main(final String[] args) throws IOException, InterruptedException {

        //1. Build command
        final Process process = new ProcessBuilder()
                .command("ping", "www.jittagornp.me")
                .start();

        //2. Build output function
        final Runnable printOutput = () -> {
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        };

        //3. Separate to process on new Thread
        new Thread(printOutput).start();

    }
}
