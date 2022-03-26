package com.beitie.mapper;

import com.beitie.bean.Yjxx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/14
 */
@Mapper
public interface YjxxMapper {
   List<Yjxx> findYjxx();
}
