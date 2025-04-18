package ru.transport.rent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.concurrent.ThreadLocalRandom;
import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;


public class CommonUtils {

    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer TEN = 10;
    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public static Integer getRandomNumber() {
        return ThreadLocalRandom.current()
                .nextInt(ONE, TEN);
    }


    public static Short getRandomShortNumber() {
        return (short) ThreadLocalRandom.current()
                .nextInt(ONE, TEN);
    }


    public static Long getRandomLongNumber() {
        return ThreadLocalRandom.current()
                .nextLong(ONE, TEN);
    }


    public static String getRandomString() {
        return getRandomString(TEN);
    }


    public static String getRandomString(int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = LETTERS.charAt(ThreadLocalRandom.current()
                    .nextInt(LETTERS.length()));
        }
        return new String(text);
    }


    public static String getJsonFromResource(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static byte[] getByteArrayFromResource(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        try (InputStream inputStream = resource.getInputStream()) {
            return FileCopyUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
