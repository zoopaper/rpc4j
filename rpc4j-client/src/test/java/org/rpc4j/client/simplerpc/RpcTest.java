package org.rpc4j.client.simplerpc;

import java.net.InetSocketAddress;

/**
 * User: shijingui
 * Date: 2016/3/2
 */
public class RpcTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcExporter.exporter("localhost", 8086);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

        RpcImporter<EchoService> rpcImporter = new RpcImporter<EchoService>();

        EchoService echoService = rpcImporter.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8086));
        String result = echoService.echo("are you ok!");
        System.out.println(result);
    }
}
