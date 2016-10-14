package org.rpc4j.client.simplerpc;

/**
 * User: shijingui
 * Date: 2016/3/2
 */
public class BootstrapRpc {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcExporter.exporter("localhost", 8080);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
