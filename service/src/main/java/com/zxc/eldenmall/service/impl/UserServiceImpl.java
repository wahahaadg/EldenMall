package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.UsersMapper;
import com.zxc.eldenmall.entity.Users;
import com.zxc.eldenmall.service.UserService;
import com.zxc.eldenmall.utils.MD5Utils;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wahaha
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @SuppressWarnings("AlibabaTransactionMustHaveRollback")
    @Override
    @Transactional
    public ResultVO userRegister(String name, String pwd) {
        synchronized (this) {
            //1.查询是否已经注册
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",name);
            List<Users> users = usersMapper.selectByExample(example);

            if (users.size() == 0) {
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int i = usersMapper.insertUseGeneratedKeys(user);
                if (i > 0) {
                    return new ResultVO(ResStatus.OK, "注册成功！", user);
                } else {
                    return new ResultVO(ResStatus.NO, "注册失败！", null);
                }
            } else {
                //2.如果没有被注册则进行保存操作
                return new ResultVO(ResStatus.NO, "此用户名已经被注册！", null);
            }
        }
    }

    @Override
    public ResultVO checkLogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",name);
        List<Users> users = usersMapper.selectByExample(example);
        if(users.size() == 0) {
            return new ResultVO(10001,"用户名不存在",null);
        } else {
            String md5Pwd = MD5Utils.md5(pwd);
            if(md5Pwd != null && md5Pwd.equals(users.get(0).getPassword())){

                //用于验证的token,使用jwt生成
                JwtBuilder builder = Jwts.builder();
                Map<String, Object> map = new HashMap<>(8);
                //主题，token中携带的数据
                String token = builder.setSubject(name)
                        //token的生成时间
                        .setIssuedAt(new Date())
                        //设置token的id为用户id
                        .setId(users.get(0).getUserId() + "")
                        //可以存放用户的权限信息
                        .setClaims(map)
                        //设置过期时间，一天
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        //设置加密方式和秘钥
                        .signWith(SignatureAlgorithm.HS256, "zhanxiaochen")
                        .compact();

                return new ResultVO(ResStatus.OK,token,users.get(0));
            } else {
                return new ResultVO(ResStatus.NO,"密码错误",null);
            }
        }
    }
}
