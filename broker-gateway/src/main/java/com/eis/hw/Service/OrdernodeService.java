package com.eis.hw.Service;

import com.eis.hw.Model.Entity.Ordernode;
import com.eis.hw.Model.RedisEntity.ROrdernode;

public interface OrdernodeService {
    public Ordernode construct(ROrdernode rOrdernode);
}
