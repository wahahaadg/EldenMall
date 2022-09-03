package com.zxc.eldenmall.general;

        import tk.mybatis.mapper.common.Mapper;
        import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author wahaha
 */
public interface GeneralDAO<T> extends Mapper<T> , MySqlMapper<T> {
}
