package org.rpc4j.sample.server;

import com.caucho.hessian.server.HessianServlet;
import org.rpc4j.sample.api.HelloHessian;

/**
 * User: shijingui
 * Date: 2016/9/26
 */
public class HelloHessianImpl extends HessianServlet implements HelloHessian {
    @Override
    public String say(String msg) {
        return "Hello " + msg + "!";
    }
}
