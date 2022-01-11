package top.meethigher.servletembedded.common.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 抄袭自 com.alibaba.druid.util.Utils
 */
public class Utils {

    public final static int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static String read(InputStream in) {
        if (in == null) {
            return null;
        }

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return read(reader);
    }

    public static void close(Closeable x) throws IOException {
        if (x != null) {
            x.close();
        }
    }

    public static String readFromResource(String resource) throws IOException {
        if (resource == null
                || resource.isEmpty()
                || resource.contains("..")
                || resource.contains("?")
                || resource.contains(":")) {
            return null;
        }

        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (in == null) {
                in = Utils.class.getResourceAsStream(resource);
            }

            if (in == null) {
                return null;
            }

            String text = Utils.read(in);
            return text;
        } finally {
            close(in);
        }
    }

    public static byte[] readByteArrayFromResource(String resource) throws IOException {
        if (resource == null
                || resource.isEmpty()
                || resource.contains("..")
                || resource.contains("?")
                || resource.contains(":")) {
            return null;
        }

        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (in == null) {
                return null;
            }

            return readByteArray(in);
        } finally {
            close(in);
        }
    }

    public static byte[] readByteArray(InputStream input) throws IOException {
        if (input == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        byte[] bytes = output.toByteArray();
        output.close();
        return bytes;
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        final int EOF = -1;

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static String read(Reader reader) {
        if (reader == null) {
            return null;
        }

        try {
            StringWriter writer = new StringWriter();

            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }

            return writer.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }

    public static String read(Reader reader, int length) {
        if (reader == null) {
            return null;
        }

        try {
            char[] buffer = new char[length];

            int offset = 0;
            int rest = length;
            int len;
            while ((len = reader.read(buffer, offset, rest)) != -1) {
                rest -= len;
                offset += len;

                if (rest == 0) {
                    break;
                }
            }

            return new String(buffer, 0, length - rest);
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }

    public static String toString(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(date);
    }

    public static String getStackTrace(Throwable ex) {
        StringWriter buf = new StringWriter();
        ex.printStackTrace(new PrintWriter(buf));
        return buf.toString();
    }

    public static String toString(StackTraceElement[] stackTrace) {
        StringBuilder buf = new StringBuilder();
        for (StackTraceElement item : stackTrace) {
            buf.append(item.toString());
            buf.append("\n");
        }
        return buf.toString();
    }

    public static Boolean getBoolean(Properties properties, String key) {
        String property = properties.getProperty(key);
        if ("true".equals(property)) {
            return Boolean.TRUE;
        } else if ("false".equals(property)) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static Integer getInteger(Properties properties, String key) {
        String property = properties.getProperty(key);

        if (property == null) {
            return null;
        }
        try {
            return Integer.parseInt(property);
        } catch (NumberFormatException ex) {
            // skip
        }
        return null;
    }

    public static Long getLong(Properties properties, String key) {
        String property = properties.getProperty(key);

        if (property == null) {
            return null;
        }
        try {
            return Long.parseLong(property);
        } catch (NumberFormatException ex) {
            // skip
        }
        return null;
    }

    public static Class<?> loadClass(String className) {
        Class<?> clazz = null;

        if (className == null) {
            return null;
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // skip
        }

        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader != null) {
            try {
                clazz = ctxClassLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                // skip
            }
        }

        return clazz;
    }


    public static void loadFromFile(String path, Set<String> set) throws IOException {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(is));
            for (; ; ) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                line = line.trim().toLowerCase();

                if (line.length() == 0) {
                    continue;
                }
                set.add(line);
            }
        } catch (Exception ex) {
            // skip
        } finally {
            close(is);
            close(reader);
        }
    }

    /**
     * 通过反射将map转换为对应的对象
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapToBean(Map<String, ? extends Object> map, Class<T> clazz) {
        try {
            T o = clazz.newInstance();
            for (Map.Entry<String, ? extends Object> mapEntry : map.entrySet()) {
                String key = mapEntry.getKey();
                Field field = o.getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(o, map.get(key));
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }


}
