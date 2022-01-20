import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HzzfUpload {

    public static void main(String[] args) throws IOException {
        String uploadFile = "svn.txt";
        File file = new File(uploadFile);
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String bzFilePath = new String();// 标准目录
            String outFilePaht = new String();// 输出目录
            String localWorkSpace = new String();// 本地工程目录
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmm");
            while ((lineTxt = bufferedReader.readLine()) != null) {
                System.out.println(lineTxt);
                if (lineTxt.startsWith("标准目录")) {
                    String[] ss = lineTxt.split("\\|");
                    System.out.println(ss);
                    bzFilePath = ss[1].trim();
                    System.out.println(bzFilePath);
                }
                if (lineTxt.startsWith("输出目录")) {
                    outFilePaht = lineTxt.split("\\|")[1].trim() + "\\" + sd.format(new Date()) + "_";
                }
                if (lineTxt.startsWith("系统工程目录")) {
                    localWorkSpace = lineTxt.split("\\|")[1].trim();
                }

                if (lineTxt.startsWith(bzFilePath)) {
                    String[] array = lineTxt.split("\\.");
                    // 如果只是文件夹,创建
                    if (array.length <= 1) {
                        continue;
                    }
                    // 如果是src下的文件，从/WEB-INF/classes/文件中获取
                    System.out.println("bzFilePath-->" + bzFilePath);
                    System.out.println("1-->" + array[0]);
                    if (array[0].startsWith(bzFilePath + "/src/main/java/")) {
                        String sourceFile = lineTxt.replace(bzFilePath + "/src/", localWorkSpace + "\\src\\main\\webapp\\WEB-INF\\classes\\").replace(".java", ".class").replace("main/java/","").replace("/", "\\");
                        // 工程下路径
                        String targetFile = lineTxt.replace(bzFilePath + "/src/", outFilePaht + "\\src\\main\\webapp\\WEB-INF\\classes\\").replace(".java", ".class").replace("main/java/","").replace("/", "\\");
                        // 目标路径
                        File tFile = new File(targetFile);
                        if (!tFile.getParentFile().exists()) {
                            // System.out.println("目标文件所在路径不存在，准备创建。。。");
                            if (!tFile.getParentFile().mkdirs()) {
                                System.out.println("创建目录文件所在的目录失败！!");
                            }
                        }
                        if (tFile.isFile() && !tFile.exists()) {
                            tFile.createNewFile();
                        }
                        copyFile(new File(sourceFile), tFile);
                        System.out.println("save  ok:" + tFile);

                        //获取匿名内部类
                        List<File> innerClassPathList = new ArrayList<File>();
                        //String[] iArray = sourceFile.split("\\.");
                        String baseDirName = new File(sourceFile).getParent();//获取文件路径


                        String targetFileName = new String();//文件名
                        String temp[] = sourceFile.split("\\\\");
                        if (temp.length > 1) {
                            targetFileName = temp[temp.length - 1];
                        }
                        findFiles(baseDirName, targetFileName.split("\\.")[0] + "$*" + targetFileName.split("\\.")[1], innerClassPathList);

                        if (innerClassPathList.size() > 0) {//复制匿名内部类
                            for (File innerfile : innerClassPathList) {
                                String outFilePath = innerfile.getAbsolutePath().replace(localWorkSpace, outFilePaht);
                                File ttFile = new File(outFilePath);
                                if (!ttFile.getParentFile().exists()) {
                                    // System.out.println("目标文件所在路径不存在，准备创建。。。");
                                    if (!ttFile.getParentFile().mkdirs()) {
                                        System.out.println("匿名内部类创建目录文件所在的目录失败！---" + ttFile.getParentFile().getPath());
                                    }
                                }
                                if (ttFile.isFile() && !ttFile.exists()) {
                                    ttFile.createNewFile();
                                }
                                copyFile(innerfile, ttFile);
                                System.out.println("save innerclasser ok:" + ttFile);
                            }
                        }
                    }
                    // 如果是conf下的文件，从conf文件中获取
                    else if (array[0].startsWith(bzFilePath + "/conf/")) {
                        String sourceFile = lineTxt.replace(bzFilePath + "/conf/", localWorkSpace + "\\webapp\\WEB-INF\\classes\\").replace("/", "\\");// 工程下路径
                        String targetFile = lineTxt.replace(bzFilePath + "/conf/", outFilePaht + "\\webapp\\WEB-INF\\classes\\").replace("/", "\\");// 目标路径
                        File tFile = new File(targetFile);
                        if (!tFile.getParentFile().exists()) {
                            // System.out.println("目标文件所在路径不存在，准备创建。。。");
                            if (!tFile.getParentFile().mkdirs()) {
                                System.out.println("创建目录文件所在的目录失败！---" + tFile.getParentFile().getPath());
                            }
                        }
                        if (tFile.isFile() && !tFile.exists()) {
                            tFile.createNewFile();
                        }
                        copyFile(new File(sourceFile), tFile);
                        System.out.println("save  ok" + tFile.getAbsolutePath());


                    } else {// 如果是其他文件，目录不变照样复制
                        String sourceFile = lineTxt.replace(bzFilePath, localWorkSpace).replace("/", "\\");// 工程下路径
                        String targetFile = lineTxt.replace(bzFilePath, outFilePaht).replace("/", "\\");// 目标路径
                        File tFile = new File(targetFile);
                        if (!tFile.getParentFile().exists()) {
                            // System.out.println("目标文件所在路径不存在，准备创建。。。");
                            if (!tFile.getParentFile().mkdirs()) {
                                System.out.println("创建目录文件所在的目录失败！---" + tFile.getParentFile().getPath());
                            }
                        }
                        if (tFile.isFile() && !tFile.exists()) {
                            tFile.createNewFile();
                        }
                        copyFile(new File(sourceFile), tFile);
                        System.out.println("save  ok:" + tFile);
                    }
                }
            }
            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }
        //List fileList = new ArrayList();// 所需更新目录List
    }

    // 复制文件
    private static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * * 递归查找文件 * * @param baseDirName * 查找的文件夹路径 * @param targetFileName *
     * 需要查找的文件名 * @param fileList * 查找到的文件集合
     */
    private static void findFiles(String baseDirName, String targetFileName, List<File> fileList) {
        /**
         * * 算法简述： 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件， *
         * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。 队列不空，重复上述操作，队列为空，程序结束，返回结果。
         */
        String tempName = null; // 判断目录是否存在
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
        } else {
            String[] filelist = baseDir.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(baseDirName + "\\" + filelist[i]);
                // System.out.println(readfile.getName());
                if (!readfile.isDirectory()) {
                    tempName = readfile.getName();
                    if (wildcardMatch(targetFileName, tempName)) {
                        // 匹配成功，将文件名添加到结果集
                        fileList.add(readfile.getAbsoluteFile());
                    }
                } else if (readfile.isDirectory()) {
                    //不使用递归
                    findFiles(baseDirName + "\\" + filelist[i], targetFileName, fileList);
                }
            }
        }
    }

    /**
     * * 通配符匹配 * * @param pattern * 通配符模式 * @param str * 待匹配的字符串 * @return
     * 匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                // 通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                // 通配符问号?表示匹配任意一个字符 strIndex++;
                if (strIndex > strLength) {
                    // 表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

}
