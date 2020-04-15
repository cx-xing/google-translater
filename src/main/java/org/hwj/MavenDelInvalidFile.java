package org.hwj;


import java.io.File;

/**
 * @Author C__xing
 * @Date 2020/2/19 11:10
 * @Version 1.0
 * <p>
 * 1.maven执行
 * 首先javac CleanMvn.java编译为.class文件。
 * 然后java CleanMvn d:.m2\repository通过后面的参数来删除本地仓库中无效的文件。
 * <p>
 * <p>
 * 2.idea直接执行
 * M2Path修改为Maven仓库直接执行
 * <p>
 **/
public class MavenDelInvalidFile {
    public static void main(String[] args) {
        /**
         * 1.maven执行
         * 首先javac CleanMvn.java编译为.class文件。
         * 然后java CleanMvn d:.m2\repository通过后面的参数来删除本地仓库中无效的文件。
         */
//        if (args.length != 1) {
//            print("使用方法错误，方法需要一个参数，参数为mvn本地仓库的路径");
//        }
//        findAndDelete(new File(args[0]));
        /**
         * 2.idea直接执行
         * M2Path修改为Maven仓库直接执行
         */
        String M2Path = "E:\\apache-maven-3.0.4\\repo";
        findAndDelete(new File(M2Path));

    }

    public static boolean findAndDelete(File file) {
        if (!file.exists()) {
        } else if (file.isFile()) {
            if (file.getName().endsWith("lastUpdated")) {
                boolean deleteFile = true;
                File[] lastLevelFiles = file.getParentFile().listFiles();
                for (File f : lastLevelFiles) {
                    if (f.getName().endsWith("jar")) {
                        deleteFile = false;
                    }
                }
                if (deleteFile) {
                    deleteFile(file.getParentFile());
                }
                return true;
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (findAndDelete(f)) {
                    break;
                }
            }
        }
        return false;
    }

    public static void deleteFile(File file) {
        if (!file.exists()) {
        } else if (file.isFile()) {
            print("删除文件:" + file.getAbsolutePath());
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            print("删除文件夹:" + file.getAbsolutePath());
            print("====================================");
            file.delete();
        }
    }

    public static void print(String msg) {
        System.out.println(msg);
    }
}
