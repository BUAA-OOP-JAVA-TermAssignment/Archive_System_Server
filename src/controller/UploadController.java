package controller;

import dao.DaoSet;
import dao.domain.Document;
import dao.utils.FileUtils;
import message.DownloadRequestMsg;
import message.UploadReturnMsg;
import org.apache.pdfbox.pdmodel.interactive.form.FieldUtils;
import request.DownloadRequest;
import request.UploadRequst;
import service.UploadService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class UploadController {

    private static UploadController uc;

    private UploadService uploadService;

    private static final String FILE_SAVE_PATH = "D:\\Archive_System\\origin_pdfs";

    private UploadController() {
        setUploadService(new UploadService());
    }

    public static UploadController getInstance() {
        if (uc == null) {
            synchronized (UploadController.class) {
                if (uc == null) {
                    uc = new UploadController();
                }
            }
        }
        return uc;
    }

    public void setUploadService(UploadService us) {
        uploadService = us;
    }

    /**
     * 上传pdf文件
     */
    public void uploadFile(UploadRequst um) {
        //TODO:这里返回值需要修改！
        File file = um.getFile();
        if (file != null) {
            boolean haveUpload = uploadService.hasUploadFile(file);
            if (haveUpload) {
                um.getThread().sendMsgBack(new UploadReturnMsg(114514));
            } else {
                um.getThread().sendMsgBack(new UploadReturnMsg(114514));
            }
        } else {
            um.getThread().sendMsgBack(new UploadReturnMsg(114514));
        }
    }


    /**
     * 下载pdf文件
     */
    public void download(DownloadRequest dr) {
        System.out.println("hello!");
        String filePath = FILE_SAVE_PATH + "\\" + dr.getDocId() + ".pdf";
        dr.getThread().downloadFile(filePath);
        System.out.println("下载成功！");
    }

    //TODO:上传完文件之后使用此方法

    /**
     * 用于将上传的文件和文件属性合并，并保存在数据库
     *
     * @param doc     欲保存的文档，其中 content == null
     * @param pdfPath 本地保存的pdf文件地址，其中包含待提取的content
     * @return true 添加成功 false 添加失败
     */
    public boolean saveDocument(Document doc, String pdfPath) {
        String text = null;
        try {
            text = FileUtils.readPdf(pdfPath);
        } catch (IOException e) {
            System.out.println("读取PDF文件失败！");
            e.printStackTrace();
            return false;
        }
        doc.setContent(text);
        DaoSet.docDao.add(doc);
        return true;
    }

}
