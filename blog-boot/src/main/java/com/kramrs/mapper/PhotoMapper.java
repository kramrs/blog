package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.Photo;
import com.kramrs.model.vo.query.PhotoQuery;
import com.kramrs.model.vo.response.PhotoBackResp;
import com.kramrs.model.vo.response.PhotoResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 照片 Mapper
 */
@Mapper
public interface PhotoMapper extends BaseMapper<Photo> {

    /**
     * 查询后台照片列表
     *
     * @param photoQuery 照片查询条件
     * @return 后台照片列表
     */
    List<PhotoBackResp> selectBackPhotoList(@Param("param") PhotoQuery photoQuery);

    /**
     * 查询照片列表
     *
     * @param albumId 相册id
     * @return 后台照片列表
     */
    List<PhotoResp> selectPhotoVOList(@Param("albumId") Integer albumId);
}
