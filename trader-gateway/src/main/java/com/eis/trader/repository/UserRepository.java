package com.eis.trader.repository;

import com.eis.trader.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kaclarpt on 2019/6/10
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
