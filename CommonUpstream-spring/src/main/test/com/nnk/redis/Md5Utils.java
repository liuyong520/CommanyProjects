package com.nnk.redis;

import com.nnk.interfacetemplate.common.DESUtil;
import com.nnk.interfacetemplate.common.MD5Util;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/24
 * Time: 17:33
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class Md5Utils {
    @Test
    public void testMd5(){
        String md5 = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><xsdl><mappings><mapping/><mapping name=\"orderid\" nnkname=\"sendorderid\" defaultvalue=\"\" type=\"String\" constant=\"0\"/><mapping name=\"money\" nnkname=\"value\" defaultvalue=\"\" type=\"String\" constant=\"0\"/><mapping name=\"rechargNumber\" nnkname=\"mob\" defaultvalue=\"\" type=\"String\" constant=\"0\"/><mapping name=\"rechargeType\" nnkname=\"\" defaultvalue=\"DX\" type=\"Constant\" constant=\"0\"/></mappings></xsdl>";
        System.out.println(DESUtil.encryptToDES(md5,"4735764763345C3B"));

    }
    @Test
    public void testMd51(){
        String md5 = "UserID=4553&PayPass=142375&OrderNo=5454544654654544&OpValue=0&Phone=13267191379&Amount=100&Ret_URL=http://112.95.172.89:19801/Upstream/coustomizeCallback?merid=2000000210E1D4F7B1655A4CB41011E44B61F36F5E";
        System.out.println(MD5Util.getMD5String(md5));

    }
}
