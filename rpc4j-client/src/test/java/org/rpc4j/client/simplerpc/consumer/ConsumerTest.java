package org.rpc4j.client.simplerpc.consumer;

import org.rpc4j.client.simplerpc.RpcImporter;
import org.rpc4j.client.simplerpc.provider.EchoService;
import org.rpc4j.client.simplerpc.provider.EchoServiceImpl;

import java.net.InetSocketAddress;

/**
 * User: shijingui
 * Date: 2016/5/15
 */
public class ConsumerTest {

    public static void main(String[] args) {
        RpcImporter<EchoService> rpcImporter = new RpcImporter<EchoService>();

        EchoService echoService = rpcImporter.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8080));
        String result = echoService.echo("are you ok!");
        System.out.println(result);
    }
}
