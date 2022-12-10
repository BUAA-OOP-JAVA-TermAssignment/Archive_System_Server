package controller;

import dao.DaoSet;
import dao.domain.Document;
import dao.utils.FileUtils;
import message.UploadReturnMsg;
import org.apache.pdfbox.pdmodel.interactive.form.FieldUtils;
import request.UploadRequst;
import service.UploadService;

import java.io.File;
import java.io.IOException;

public class UploadController {

    private static UploadController uc;

    private UploadService uploadService;

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
     * �ϴ�pdf�ļ�
     */
    public void uploadFile(UploadRequst um) {
        //TODO:���ﷵ��ֵ��Ҫ�޸ģ�
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

    //TODO:�ϴ����ļ�֮��ʹ�ô˺���

    /**
     * ���ڽ��ϴ����ļ����ļ����Ժϲ��������������ݿ�
     *
     * @param doc     ��������ĵ������� content == null
     * @param pdfPath ���ر����pdf�ļ���ַ�����а�������ȡ��content
     * @return true ��ӳɹ� false ���ʧ��
     */
    public boolean saveDocument(Document doc, String pdfPath) {
        String text = null;
        try {
            text = FileUtils.readPdf(pdfPath);
        } catch (IOException e) {
            System.out.println("��ȡPDF�ļ�ʧ�ܣ�");
            e.printStackTrace();
            return false;
        }
        doc.setContent(text);
        DaoSet.docDao.add(doc);
        return true;
    }

}
