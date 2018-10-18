package com.sz.example.dao.mapper;

import com.sz.example.pojo.Idpid;
import com.sz.example.pojo.IdpidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IdpidMapper {
    int countByExample(IdpidExample example);

    int deleteByExample(IdpidExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Idpid record);

    int insertSelective(Idpid record);

    List<Idpid> selectByExample(IdpidExample example);

    Idpid selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Idpid record, @Param("example") IdpidExample example);

    int updateByExample(@Param("record") Idpid record, @Param("example") IdpidExample example);

    int updateByPrimaryKeySelective(Idpid record);

    int updateByPrimaryKey(Idpid record);
}