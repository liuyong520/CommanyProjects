package com.nnk.template.entity;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/10/12
 * Time: 14:13
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

import java.io.OptionalDataException;

/**
 * ������������
 */
public class OperationDescprition {
    //����Action����
    private String optName;
    //��������
    private String OptType;
    //����Ԫ��λ��
    private String optElement;
    //����Ԫ�ص�ֵ
    private String optElementValue;
    //����Ԫ��2
    private String optElementValue2;
    //�Ƿ��и�����������
    private boolean cascade;
    //����������һ������
    private OperationDescprition next;
    private OperationDescprition last;
    //�Ƿ�����������
    private boolean condition;
    //��������ִ�е���һ��������
    private OperationDescprition right;
    //��������������һ��������
    private OperationDescprition left;
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public OperationDescprition(String optName, String optType, String optElement, String optElementValue, String optElementValue2,boolean cascade, OperationDescprition next, OperationDescprition last, boolean condition, OperationDescprition right, OperationDescprition left) {
        this.optName = optName;
        OptType = optType;
        this.optElement = optElement;
        this.optElementValue = optElementValue;
        this.optElementValue2 = optElementValue2;
        this.cascade = cascade;
        this.next = next;
        this.last = last;
        this.condition = condition;
        this.right = right;
        this.left = left;
    }

    public OperationDescprition(String optName, String optType, String optElement, String optElementValue,String OptElementValue2) {
       this(optName,optType,optElement,optElementValue,OptElementValue2,false,null,null,false,null,null);
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getOptType() {
        return OptType;
    }

    public void setOptType(String optType) {
        OptType = optType;
    }

    public String getOptElement() {
        return optElement;
    }

    public void setOptElement(String optElement) {
        this.optElement = optElement;
    }

    public String getOptElementValue() {
        return optElementValue;
    }

    public void setOptElementValue(String optElementValue) {
        this.optElementValue = optElementValue;
    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }

    public OperationDescprition getNext() {
        return next;
    }

    public void setNext(OperationDescprition next) {
        this.next = next;
    }

    public OperationDescprition getLast() {
        return last;
    }

    public void setLast(OperationDescprition last) {
        this.last = last;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public OperationDescprition getRight() {
        return right;
    }

    public void setRight(OperationDescprition right) {
        this.right = right;
    }

    public OperationDescprition getLeft() {
        return left;
    }

    public void setLeft(OperationDescprition left) {
        this.left = left;
    }

    public String getOptElementValue2() {
        return optElementValue2;
    }

    public void setOptElementValue2(String optElementValue2) {
        this.optElementValue2 = optElementValue2;
    }

    @Override
    public String toString() {
        return "OperationDescprition{" +
                "optName='" + optName + '\'' +
                ", OptType='" + OptType + '\'' +
                ", optElement='" + optElement + '\'' +
                ", optElementValue='" + optElementValue + '\'' +
                ", optElementValue2='" + optElementValue2 + '\'' +
                ", cascade=" + cascade +
                ", condition=" + condition +
                '}';
    }
}
