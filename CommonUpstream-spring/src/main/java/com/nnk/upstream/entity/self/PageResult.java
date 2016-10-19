package com.nnk.upstream.entity.self;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/12
 * Time: 15:03
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class PageResult {
    private List list; //װ��ҳ�б��е�����

    private Long firstPage; //��һҳ

    private Long prePage;  //��һҳ

    private Long nextPage;//��һҳ

    private Long currentPage;//��ǰҳ��

    private Long totalPage;  //��ҳ��/βҳ

    private Long count;//������

    private Long size; //ÿҳ������

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Long getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Long firstPage) {
        this.firstPage = firstPage;
    }

    public Long getPrePage() {
        return (this.currentPage - 1 == 0 ? 1 : this.currentPage - 1);
    }

    public void setPrePage(Long prePage) {
        this.prePage = prePage;
    }

    public Long getNextPage() {
        return (this.currentPage == this.totalPage ? this.totalPage : this.currentPage + 1);
    }

    public void setNextPage(Long nextPage) {
        this.nextPage = nextPage;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalPage() {
        return (this.count % this.size == 0 ? this.count / this.size : this.count / this.size + 1);
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

}
