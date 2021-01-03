package main.downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class Downloader {

    private static final String FILE_URL = "https://covid.ourworldindata.org/data/owid-covid-data.csv";
    private static final String FILE_NAME = "/owid-covid-data.csv";
    private static final String DIR_NAME = "src/main/resources/csv";


    public static void getFile() throws IOException {
        File theDir = new File(DIR_NAME);
        theDir.mkdir();

        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(FILE_URL).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(DIR_NAME+FILE_NAME);
        fileOutputStream.getChannel()
                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();
        readableByteChannel.close();
    }
}
