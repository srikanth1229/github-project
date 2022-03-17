package com.kodemate.tutorials.downloadfile;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadFile {
    static final String FILE_URL = "https://kodemate.com/wp-content/uploads/2018/08/kodemate.com-logo-design-header-retina-300x99.png";

    public static void main(String... args) {
        usingJavaIO();

        usingFilesCopy();

        usingJavaNIO();
    }

    public static void usingJavaIO() {
        BufferedInputStream in;
        FileOutputStream fileStream;

        try {
            in = new BufferedInputStream(new URL(FILE_URL).openStream());
            fileStream = new FileOutputStream("./logo.png");
            byte buffer[] = new byte[1024];
            int bRead;

            while((bRead = in.read(buffer, 0, 1024)) != -1) {
                fileStream.write(buffer, 0, bRead);
            }

            fileStream.close();
            in.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void usingFilesCopy() {
        InputStream in = null;
        try {
            in = new URL(FILE_URL).openStream();
            Files.copy(in, Paths.get("./logo.png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void usingJavaNIO() {
        try {
            ReadableByteChannel readChannel = Channels.newChannel(new URL(FILE_URL).openStream());
            FileOutputStream fileStream = new FileOutputStream("./logo.png");
            FileChannel writeChannel = fileStream.getChannel();
            writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
            fileStream.close();
            readChannel.close();
            writeChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
