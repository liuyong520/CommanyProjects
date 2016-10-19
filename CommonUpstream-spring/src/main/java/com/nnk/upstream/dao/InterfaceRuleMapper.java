package com.nnk.upstream.dao;

import com.nnk.upstream.vo.InterfaceRule;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface InterfaceRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interfaceRule
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interfaceRule
     *
     * @mbggenerated
     */
    int insert(InterfaceRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interfaceRule
     *
     * @mbggenerated
     */
    int insertSelective(InterfaceRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interfaceRule
     *
     * @mbggenerated
     */
    InterfaceRule selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interfaceRule
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(InterfaceRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interfaceRule
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(InterfaceRule record);


    List<InterfaceRule> findAllRule();

    List<InterfaceRule> findAllRulepage(HashMap<String, Long> hashMap);

    List<InterfaceRule> findRule(@Param("merchantno")String merchantno);

    Long countRule();
}