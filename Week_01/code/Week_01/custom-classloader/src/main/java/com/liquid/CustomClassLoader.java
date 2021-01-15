package com.liquid;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义class loader
 *
 * @author Liquid
 */
public class CustomClassLoader extends ClassLoader {
    private final String path;

    public CustomClassLoader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        bytes = readBytes(name);
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 通过文件名读取字节码并完成转换
     *
     * @param name
     * @return byte[]
     */
    @SneakyThrows
    private byte[] readBytes(String name) {
        String xlassFilePath = path + name + ".xlass";
        byte[] bytes = FileUtil.readBytes(xlassFilePath);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return bytes;
    }

    public static void main(String[] args) {
        CustomClassLoader loader = new CustomClassLoader("D:\\Documents\\project\\Week_01\\custom-classloader\\");
        try {
            Class<?> hello = loader.findClass("Hello");
            Object o = hello.newInstance();
            Method method = hello.getMethod("hello");
            method.invoke(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
