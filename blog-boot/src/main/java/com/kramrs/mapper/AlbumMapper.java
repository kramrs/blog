package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.Album;
import com.kramrs.model.vo.query.AlbumQuery;
import com.kramrs.model.vo.request.AlbumReq;
import com.kramrs.model.vo.response.AlbumBackResp;
import com.kramrs.model.vo.response.AlbumResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 相册 Mapper
 */
@Mapper
public interface AlbumMapper extends BaseMapper<Album> {

    /**
     * 查询后台相册列表
     *
     * @param albumQuery 相册查询条件
     * @return 后台相册列表
     */
    List<AlbumBackResp> selectBackAlbumList(@Param("param") AlbumQuery albumQuery);

    /**
     * 根据id查询相册信息
     *
     * @param albumId 相册id
     * @return 相册
     */
    AlbumReq selectAlbumById(@Param("albumId") Integer albumId);

    /**
     * 根据id查询照片相册信息
     *
     * @param albumId 相册id
     * @return 照片相册信息
     */
    AlbumBackResp selectAlbumInfoById(Integer albumId);

    /**
     * 查看相册列表
     *
     * @return 相册列表
     */
    List<AlbumResp> selectAlbumVOList();
}
