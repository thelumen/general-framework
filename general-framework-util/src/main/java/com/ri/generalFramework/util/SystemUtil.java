package com.ri.generalFramework.util;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SystemUtil {

    /**
     * 获取绝对路径(项目路径中不可以有空格)
     *
     * @param relPath 相对路径
     */
    public static String getAbsPath(String relPath) {
        String rootPath = SystemUtil.class.getResource("/").getPath().substring(1).replaceFirst("target/test-classes/", "");
        return rootPath + relPath;
    }

    /**
     * 获取本地IP地址
     *
     * @return String
     */
    public static String getLocalHost() {

        String localhost = "";
        InetAddress net;
        try {
            net = InetAddress.getLocalHost();
            localhost = net.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return localhost;
    }

    /**
     * 根据目录获取文件列表
     *
     * @return List
     */
    public static List getFiles(String path) {

        List<File> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            return list;
        }
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        for (File value : files) {
            if (value.isFile()) {
                list.add(value);
            }
        }

        list.sort((f1, f2) -> {
            if (f1.lastModified() - f2.lastModified() < 0) {
                return 1;
            } else if (f1.lastModified() - f2.lastModified() > 0) {
                return -1;
            } else {
                return 0;
            }
        });

        if (list.size() > 30) {
            return list.subList(0, 30);
        }
        return list;
    }


    /**
     * 删除指定路径的文件
     *
     * @param path
     * @return boolean 删除成功返回true，删除失败返回false
     */
    public static boolean deleteFile(String path) {

        File file = new File(path);
        return file.delete();
    }

    /**
     * 获取当前操作系统
     *
     * @return 操作系统名
     */
    public static String getOS() {
        Properties properties = System.getProperties();
        String osName = properties.getProperty("os.name");
        if (osName.contains("Windows")) {
            return "windows";
        } else if (osName.contains("Linux")) {
            return "linux";
        } else {
            return osName;
        }
    }
}
