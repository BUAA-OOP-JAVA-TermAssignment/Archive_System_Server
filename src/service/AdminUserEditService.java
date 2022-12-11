package service;

import dao.DaoSet;
import dao.domain.User;

import java.util.List;

/**
 * �����ṩ����Ա���û���Ϣ�ı༭����
 */
public class AdminUserEditService {
    /**
     * ����û�
     *
     * @param id          �û�id
     * @param userName    �û���
     * @param password    ����
     * @param email       ����
     * @param time        ע��ʱ��
     * @param downloadCnt �û�������
     * @return true �ɹ� false ʧ��
     */
    public boolean hasAdminAdd(String id, String userName, String password, String email, String time, int downloadCnt) {
        System.out.println("hasAdminAdd");
        return DaoSet.userDao.addUser(new User(id, userName, password, email, downloadCnt, time));
    }

    /**
     * ɾ���û�
     *
     * @param id �û�ID
     * @return true �ɹ� false ʧ��
     */
    public boolean hasAdminDelete(String id) {
        System.out.println("hasAdminDelete");
        return DaoSet.userDao.deleteUser(id);
    }

    /**
     * �ı��û���Ϣ
     *
     * @param id          �û�id
     * @param userName    �û���
     * @param password    ����
     * @param email       ����
     * @param time        ע��ʱ��
     * @param downloadCnt �û�������
     * @return true �ɹ� false ʧ��
     */
    public boolean hasAdminChange(String id, String userName, String password, String email, String time, int downloadCnt) {
        System.out.println("hasAdminChange");
        return DaoSet.userDao.adminChangeUser(id, userName, password, email, time, downloadCnt);
    }

    /**
     * ��ȡ�����û�
     *
     * @return List<>User</>
     */
    public List<User> hasUserList() {
        System.out.println("hasUserList");
        return DaoSet.userDao.getAllUser();
    }
}
