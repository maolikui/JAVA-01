package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.annotation.RpcfxService;
import io.kimmking.rpcfx.demo.entity.User;
import io.kimmking.rpcfx.demo.api.UserService;

@RpcfxService(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
