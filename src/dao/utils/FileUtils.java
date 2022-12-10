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
     * �������ļ�
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
     * ���ļ��ж�ȡ����
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
            System.out.println("�ļ���ȡʧ�ܣ�");
            return null;
        }
    }

    /**
     * ��Ĭ�ϵ�utf-8�����ȡ�ļ�
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
     * �г�ĳ��·���µ������ļ����������ļ��У������������ļ�����ô��������
     *
     * @param filePath ��Ҫ�������ļ�·��
     * @return �ļ�����
     */
    public static List<File> listAllFiles(String filePath) {
        return listAllFiles(filePath, null);
    }

    /**
     * �г�ĳ��·���µ������ļ����������ļ��У������������ļ�����ô��������
     *
     * @param filePath       ��Ҫ�������ļ�·��
     * @param fileNameFilter �ļ���������
     * @return �ļ�����
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
