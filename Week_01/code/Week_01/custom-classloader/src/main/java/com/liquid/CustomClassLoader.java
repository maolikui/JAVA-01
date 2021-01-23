package com.liquid;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义class loader
 *
 * @author Liquid
 */
public class CustomClassLoader extends ClassLoader {
    private String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = readBytes(name);
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
        if (StrUtil.isBlank(name)) {
            throw new Exception("IllegalName: " + name);
        }
        String path = (StrUtil.isBlank(classPath) ? "" : classPath) + name.replaceAll("\\.", "/");
        String encryClassPath = path + ".xlass";
        //优先使用类名加密的字节码文件
        if (FileUtil.exist(encryClassPath)) {
            @Cleanup
            FileInputStream in = new FileInputStream(encryClassPath);
            @Cleanup
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1];
            int size = 0;
            while ((size = in.read(buffer)) != -1) {
                //逐个字节解析
                buffer[0] = (byte) (255 - buffer[0]);
                out.write(buffer, 0, size);
            }
            return out.toByteArray();
        }
        return FileUtil.readBytes(path + FileUtil.CLASS_EXT);
    }

    public static void main(String[] args) {
        CustomClassLoader loader = new CustomClassLoader("D:\\Documents\\project\\Week_01\\custom-classloader\\");
        //路径为null 时代表类路径
//        CustomClassLoader loader = new CustomClassLoader(null);
        try {
            Class<?> hello = loader.findClass("Hello");
            System.out.println("Hello 类加载器：" + hello.getClassLoader());
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
