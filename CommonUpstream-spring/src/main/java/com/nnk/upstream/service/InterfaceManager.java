package com.nnk.upstream.service;

import com.nnk.upstream.dao.InterfaceConfigMapper;
import com.nnk.upstream.dao.InterfaceRuleMapper;
import com.nnk.upstream.exception.NNKSQLException;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.upstream.vo.InterfaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/11
 * Time: 18:08
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Component
public class InterfaceManager {

    @Autowired
    private InterfaceConfigMapper interfaceConfigMapper;
    @Autowired
    private InterfaceRuleMapper interfaceRuleMapper;

    /**
     * ����Э������
     * @param rule
     */
    public void insertInterfaceRule(InterfaceRule rule)throws NNKSQLException {
        try{
            interfaceRuleMapper.insert(rule);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new NNKSQLException("������г���");
        }
    }

    /**
     * ��ѯ����Э���¼
     * @return
     * @throws NNKSQLException
     */
    public List<InterfaceRule> findAllRule() throws NNKSQLException {
        try {
            return interfaceRuleMapper.findAllRule();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("��ѯ����");
        }

    }

    /**
     * ��ҳ��ѯ��¼
     * @param offset
     * @param size
     * @return
     * @throws NNKSQLException
     */
    public List<InterfaceRule> findAllRulepage(Long offset,Long size) throws NNKSQLException {
        try {
            HashMap<String,Long> hashMap = new HashMap<String,Long>();
            hashMap.put("offset",(offset-1)*size );
            hashMap.put("limit",size);
            return interfaceRuleMapper.findAllRulepage(hashMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("��ѯ����");
        }

    }
    /**
     * ��ѯ����ָ������ļ�¼
     * @param merchantno
     * @return
     * @throws NNKSQLException
     */
    public List<InterfaceRule> findRule(String merchantno) throws NNKSQLException {
        try {
            return interfaceRuleMapper.findRule(merchantno);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("��ѯ����");
        }

    }

    /**
     * ������¼
     * @param config
     * @throws NNKSQLException
     */
    public void insertConfig(InterfaceConfig config) throws NNKSQLException {
        try {
            interfaceConfigMapper.insert(config);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new NNKSQLException("������г���");
        }
    }

    /**
     * ��ѯ���м�¼
     * @return
     * @throws NNKSQLException
     */
    public List<InterfaceConfig> findAll() throws NNKSQLException {
        try {
            return interfaceConfigMapper.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("��ѯ����");
        }

    }

    /**
     * ��ҳ��ѯ��¼
     * @param offset
     * @param size
     * @return
     * @throws NNKSQLException
     */
    public List<InterfaceConfig> findAllpage(Long offset,Long size) throws NNKSQLException {
        try {
            HashMap<String,Long> hashMap = new HashMap<String,Long>();
            hashMap.put("offset",(offset-1)*size );
            hashMap.put("limit",size);
            return interfaceConfigMapper.findAllpage(hashMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("��ѯ����");
        }

    }

    /**
     * ��ѯ����ָ������ļ�¼
     * @param merchantno
     * @param interfacename
     * @return
     * @throws NNKSQLException
     */
    public List<InterfaceConfig> find(String merchantno,String interfacename) throws NNKSQLException {
        try {

            return interfaceConfigMapper.find(merchantno, interfacename);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("��ѯ����");
        }

    }

    /**
     * ͳ�Ƽ�¼����
     * @return
     * @throws NNKSQLException
     */
    public Long countRule() throws NNKSQLException {
        try {

            return interfaceRuleMapper.countRule();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("ͳ�ƴ���");
        }
    }
    /**
     * ͳ�Ƽ�¼����
     * @return
     * @throws NNKSQLException
     */
    public Long count() throws NNKSQLException {
        try {

            return interfaceConfigMapper.count();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("ͳ�ƴ���");
        }
    }

    /**
     * ɾ����¼
     * @param id
     * @return
     * @throws NNKSQLException
     */
    public boolean delete(Integer id) throws NNKSQLException {
        try {

            int ret = interfaceConfigMapper.deleteByPrimaryKey(id);
            if(ret==1){
                return true;
            }else{
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("ɾ������");
        }
    }

    /**
     * �������ݱ�
     * @param interfaceConfig
     * @return
     * @throws NNKSQLException
     */
    public boolean update(InterfaceConfig interfaceConfig) throws NNKSQLException {
        try {
            System.out.println(interfaceConfig.getId());
            int ret = interfaceConfigMapper.updateByPrimaryKeySelective(interfaceConfig);
            if(ret==1){
                return true;
            }else{
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("���´���");
        }

    }

    /**
     * ��ѯ����id�ļ�¼
     * @param id
     * @return
     * @throws NNKSQLException
     */
    public InterfaceConfig selectByPrimaryKey(Integer id) throws NNKSQLException {
        try {

            return interfaceConfigMapper.selectByPrimaryKey(id);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("ɾ������");
        }
    }

    public boolean deleteRule(Integer id) throws NNKSQLException {
        try {
            int ret = interfaceRuleMapper.deleteByPrimaryKey(id);
            if(ret==1){
                return true;
            }else{
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("ɾ������");
        }
    }

    public InterfaceRule selectRuleByPrimaryKey(Integer id) throws NNKSQLException {
        try {
            return interfaceRuleMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("ɾ������");
        }
    }

    public boolean updateRule(InterfaceRule rule) throws NNKSQLException {
        try {
//            System.out.println(rule.getId());
            int ret = interfaceRuleMapper.updateByPrimaryKeySelective(rule);
            if(ret==1){
                return true;
            }else{
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NNKSQLException("���´���");
        }

    }
}
