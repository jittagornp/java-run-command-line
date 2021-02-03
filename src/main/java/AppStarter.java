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

        System.out.println("start...");

        //1. Build command
        final Process process = new ProcessBuilder()
                .command("ping", "www.jittagornp.me")
                .start();

        //2. Separate to process on new Thread
        Executors.newSingleThreadExecutor().submit(() -> {
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

                //3. Print output
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        //4. Wait until finished
        int exitCode = process.waitFor();
        assert exitCode == 0;

        System.out.println("end...");

    }
}
