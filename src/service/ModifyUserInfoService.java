package service;

import dao.DaoSet;

public class ModifyUserInfoService {
    /**
     * �û��޸�������Ϣ
     *
     * @param id          id
     * @param password    ����
     * @param email       ����
     * @param downloadCnt ������
     * @return true �ɹ� false ʧ��
     */
    public boolean hasModify(String id, String password, String email, int downloadCnt) {
        System.out.println("hasModify");
        return DaoSet.userDao.editUser(id, password, email, downloadCnt);
    }
}
