package com.jmn.logman.service.util.file;

/*import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;*/

/*
 * Some file contents are validated by looking at the file signature
 * For more Details on file Signature see https://en.wikipedia.org/wiki/List_of_file_signatures
 * */
public class FileUtils {
/*
    public static final String USER_AGENT = " Mozilla/4.0";
    public static final String CONTENT_TYPE_CSV = "text/csv,application/csv,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENT_TYPE_XML = "text/xml,application/xml";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_PLAIN_TEXT = "text/plain";
    public static final String CONTENT_TYPE_SVG = "image/svg+xml";

    public static String getRemoteFileContentType(String fileUri) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(fileUri).openConnection();
        try {
            // DEVNOTE: Apply a 'User-Agent' to simulate a browser calling the endpoint to avoid unnecessary security headaches.
            connection.addRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestMethod("HEAD");
            if (isRedirect(connection.getResponseCode())) {
                // get redirect url from "location" header field
                String redirectUri = connection.getHeaderField("Location");
                return getRemoteFileContentType(redirectUri);
            } else {
                return connection.getContentType();
            }
        } finally {
            connection.disconnect();
        }
    }

    public static InputStream getFileStream(String fileUrl) throws IOException {
        if (fileUrl.toLowerCase().startsWith("http")) {
            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);
            return new BufferedInputStream(connection.getInputStream());
        } else {
            URLConnection connection = new URL(fileUrl).openConnection();
            return new BufferedInputStream(connection.getInputStream());
        }
    }

    public static boolean isCSV(String fileContentType) {
        for (String type : fileContentType.toLowerCase().split(";")) {
            if (CONTENT_TYPE_CSV.toLowerCase().contains(StringUtils.trimToEmpty(type))) return true;
        }
        return false;
    }

    public static boolean isXML(String fileContentType) {
        for (String type : fileContentType.toLowerCase().split(";")) {
            if (CONTENT_TYPE_XML.toLowerCase().contains(StringUtils.trimToEmpty(type))) return true;
        }
        return false;
    }

    public static boolean isJSON(String fileContentType) {
        for (String type : fileContentType.toLowerCase().split(";")) {
            if (CONTENT_TYPE_JSON.toLowerCase().contains(StringUtils.trimToEmpty(type))) return true;
        }
        return false;
    }

    public static boolean isPlainText(String fileContentType) {
        for (String type : fileContentType.toLowerCase().split(";")) {
            if (CONTENT_TYPE_PLAIN_TEXT.toLowerCase().contains(StringUtils.trimToEmpty(type))) return true;
        }
        return false;
    }

    public static boolean isSVG(String fileContentType) {
        for (String type : fileContentType.toLowerCase().split(";")) {
            if (CONTENT_TYPE_SVG.toLowerCase().contains(StringUtils.trimToEmpty(type))) return true;
        }
        return false;
    }

    private static boolean isRedirect(int statusCode) {
        if (statusCode != HttpURLConnection.HTTP_OK) {
            return statusCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || statusCode == HttpURLConnection.HTTP_MOVED_PERM
                    || statusCode == HttpURLConnection.HTTP_SEE_OTHER;
        }
        return false;
    }


    public static boolean isValidPNG(String fileUrl) throws IOException {
        return isValidPNG(getFileStream(fileUrl));
    }

    *//**
     * Check if the image fileStream is a PNG. The first eight bytes of a PNG file always
     * contain the following values: 89 50 4E 47 0D 0A 1A 0A
     *//*
    public static boolean isValidPNG(InputStream fileStream) {
        try {
            byte[] b = IOUtils.toByteArray(fileStream);
            if ((b[0] & 0xff) != 0x89 || (b[1] & 0xff) != 0x50 || (b[2] & 0xff) != 0x4E || (b[3] & 0xff) != 0x47 ||
                    (b[4] & 0xff) != 0x0D || (b[5] & 0xff) != 0x0A || (b[6] & 0xff) != 0x1A || (b[7] & 0xff) != 0x0A) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static boolean isValidJPEG(String fileUrl) throws IOException {
        return isValidJPEG(getFileStream(fileUrl));
    }

    *//**
     * Check if the image fileStream is a JPEG/JPG. JPEG/JPG image files begin with FF D8 FF
     *//*
    public static boolean isValidJPEG(InputStream fileStream) {
        try {
            byte[] b = IOUtils.toByteArray(fileStream);
            if ((b[0] & 0xff) != 0xff || (b[1] & 0xff) != 0xd8 || (b[2] & 0xff) != 0xff) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    *//**
     * Check if the file is an executable. exe/dll files begin with 4D 5A
     *//*
    public static boolean isExecutableFile(InputStream fileStream) {
        try {
            byte[] b = IOUtils.toByteArray(fileStream);
            if ((b[0] & 0xff) != 0x4d || (b[1] & 0xff) != 0x5a) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isExecutableFile(String fileUrl) throws IOException {
        return isExecutableFile(getFileStream(fileUrl));
    }*/
}
