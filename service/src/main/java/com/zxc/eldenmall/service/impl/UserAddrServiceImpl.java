package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.UserAddrMapper;
import com.zxc.eldenmall.entity.UserAddr;
import com.zxc.eldenmall.service.UserAddrService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wahaha
 */
@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listAddrsByUid(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("status",1);

        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        return new ResultVO(ResStatus.OK, "success", userAddrs);
    }
}
