package dao.utils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pcpas
 */
public class FileUtils {
    public static boolean rename(File src, String newName) {
        return src.renameTo(new File(src.getParent() + "\\" + newName));
    }

    /**
     * 重命名文件
     *
     * @param src
     * @param newName
     * @return
     */
    public static boolean rename(String src, String newName) {
        File file = new File(src);
        return rename(file, newName);
    }

    /**
     * 从文件中读取内容
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath, String encoding) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding));
            String s = "";
            StringBuffer sb = new StringBuffer();
            while ((s = br.readLine()) != null) {
                sb.append(s + "\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println("文件读取失败！");
            return null;
        }
    }

    /**
     * 以默认的utf-8编码读取文件
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        return readFile(filePath, "utf-8");
    }


    public String readPdf(String src) throws IOException {
        //Loading an existing document
        File file = new File(src);
        PDDocument document = Loader.loadPDF(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        //System.out.println(text);
        //Closing the document
        document.close();
        return text;
    }


    /**
     * 列出某个路径下的所有文件，包括子文件夹，如果本身就是文件，那么返回自身
     *
     * @param filePath 需要遍历的文件路径
     * @return 文件集合
     */
    public static List<File> listAllFiles(String filePath) {
        return listAllFiles(filePath, null);
    }

    /**
     * 列出某个路径下的所有文件，包括子文件夹，如果本身就是文件，那么返回自身
     *
     * @param filePath       需要遍历的文件路径
     * @param fileNameFilter 文件名过滤器
     * @return 文件集合
     */
    public static List<File> listAllFiles(String filePath, FilenameFilter fileNameFilter) {
        List<File> files = new ArrayList<File>();
        try {
            File root = new File(filePath);
            if (!root.exists()) return files;
            if (root.isFile()) files.add(root);
            else {
                for (File file : root.listFiles(fileNameFilter)) {
                    if (file.isFile()) files.add(file);
                    else if (file.isDirectory()) {
                        files.addAll(listAllFiles(file.getAbsolutePath(), fileNameFilter));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

}
