package service;

import dao.DaoSet;

import java.io.File;
import java.io.IOException;

public class UploadService {

    /**
     * 直接上传PDF文件，不经过DAO层，文件路径为固定统一路径 C:BookManager/
     * @param file
     * @return
     */
    public boolean hasUploadFile(File file){

        return false;
    }



}
