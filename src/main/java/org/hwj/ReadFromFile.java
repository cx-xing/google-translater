package org.hwj;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFromFile {

    public static void main(String args[]) throws Exception {
        readAndWrite();
        // 普通方式初始化
//        GoogleApi googleApi = new GoogleApi();
        // 通过代理
//        GoogleApi googleApi = new GoogleApi("122.224.227.202", 3128);
//        String result = googleApi.translate("hello world", "zh");
//        System.out.println(result);
    }

    /**
     * 读入文件
     */
    public static void readAndWrite() {
        String pathname = "D:\\0413\\influxdb-java\\QUERY_BUILDER.md"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        String filename = "QUERY_BUILDER_CN.md";

        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271

        try (
                FileReader reader = new FileReader(pathname);
                BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言


        ) {
            String line;
            int line_umber = 0;
            boolean add = true;
            List<String> lines = new ArrayList<>();
            Map<Integer, String> map = new HashMap<>();
            Map<Integer, String> mapCN = new HashMap<>();

            GoogleApi googleApi = new GoogleApi();

            while ((line = br.readLine()) != null) {
                if (line.contains("```")) {
                    add = !add;
                }

                line_umber++;

                if (add && !line.equals("```") && !line.equals("")) {
                    map.put(line_umber, line);
                    System.out.println(line);
                    add(googleApi, lines, line);
                }
                // 一次读入一行数据
                lines.add(line);

            }

            //文件写入
            File writeName = new File(filename); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);

            for (String s : lines) {
                out.write(s + "\r\n");
            }

            out.flush(); // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static synchronized void add(GoogleApi googleApi, List<String> lines, String line) throws Exception {
        //允许访问控制的代码
        String result = translate(googleApi, line, "zh");
        Thread.sleep(500);
        lines.add(result + "\r\n");
    }

    static String translate(GoogleApi googleApi, String line, String zh) throws Exception {
        String result = null;
        while (result == null) {
            result = googleApi.translate(line, zh);
            Thread.sleep(500);
        }
        return result;
    }

}
