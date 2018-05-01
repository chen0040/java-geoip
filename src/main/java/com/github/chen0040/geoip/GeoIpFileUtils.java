package com.github.chen0040.geoip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by memeanalytics on 12/8/15.
 */
public class GeoIpFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeoIpFileUtils.class);

    public static File copyToLocal(String filename, String localFilePath) throws IOException {
        File file = new File(localFilePath);
        if(file.exists()){
            return file;
        }

        InputStream inStream = getResourceStream(filename);

        FileOutputStream outStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];

        int length;
        while ((length = inStream.read(buffer)) > 0){
            outStream.write(buffer, 0, length);
        }

        inStream.close();
        outStream.close();

        return file;
    }

    public static InputStream getResourceStream(String filename) throws  IOException {
        ClassLoader classLoader = GeoIpFileUtils.class.getClassLoader();
        URL dataFile = classLoader.getResource(filename);
        return dataFile.openStream();
    }

    public static InputStreamReader getResource(String filename) throws IOException {
        return new InputStreamReader(getResourceStream(filename), "UTF-8");
    }

    public static void lines(String filename, Consumer<Stream<String>> callback) {
        if(callback == null) {
            logger.error("There is not callback for lines");
            return;
        }

        try(BufferedReader reader = new BufferedReader(getResource(filename))){
            callback.accept(reader.lines());
        }
        catch (IOException e) {
            logger.error("Failed to read the file " + filename, e);
        }
    }


    public static void forEachLine(String filename, Consumer<String> callback) {
        lines(filename, lines -> lines.forEach(callback));
    }


    public static String readToEnd(String filename) {
        StringBuilder sb = new StringBuilder();
        lines(filename, stringStream -> sb.append(stringStream.collect(Collectors.joining("\n"))));
        return sb.toString();
    }
}
