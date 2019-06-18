package com.eis.hw.service;

import com.eis.hw.model.entity.Ordernode;
import com.eis.hw.model.redisentity.ROrdernode;

public interface OrdernodeService {
    public Ordernode construct(ROrdernode rOrdernode);
}
