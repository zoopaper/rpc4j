package org.rpc4j.sample.client;

import com.caucho.hessian.client.HessianProxyFactory;
import org.rpc4j.sample.api.HelloHessian;

import java.net.MalformedURLException;

/**
 * User: shijingui
 * Date: 2016/9/28
 */
public class HelloHessianTest {

    public static String url = "http://127.0.0.1:8080/Hello";

    public static void main(String[] args) {
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            HelloHessian helloHessian = (HelloHessian) factory.create(HelloHessian.class, url);
            String msg = helloHessian.say("Hessian");
            System.out.println(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
