package org.rpc4j.client.simplerpc.provider;

/**
 * User: shijingui
 * Date: 2016/3/2
 */
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String str) {
        return "echo=" + str;
    }
}
