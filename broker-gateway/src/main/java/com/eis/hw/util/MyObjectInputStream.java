package com.eis.hw.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Created by kaclarpt on 2019/6/6
 */

public class MyObjectInputStream extends ObjectInputStream {
    protected MyObjectInputStream() throws IOException, SecurityException {
        super();
    }

    public MyObjectInputStream(InputStream arg0) throws IOException {
        super(arg0);
    }

    public Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String name = desc.getName();
        try {
            if (name.startsWith("com.eis.hw.model.redisentity.ROrderbook"))
                name = name.replace("com.eis.hw.model.redisentity.ROrderbook", "com.eis.trader.entity.ROrderbook");
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return super.resolveClass(desc);
    }
}