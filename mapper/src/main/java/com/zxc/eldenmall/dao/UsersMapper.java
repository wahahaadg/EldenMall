package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.Users;
import com.zxc.eldenmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

/**
 * @author wahaha
 * 查询用户信息
 */
@Repository
public interface UsersMapper extends GeneralDAO<Users> {
}