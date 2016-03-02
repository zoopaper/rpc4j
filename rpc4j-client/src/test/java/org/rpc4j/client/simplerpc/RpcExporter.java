package org.rpc4j.client.simplerpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * RPC发布服务
 * User: shijingui
 * Date: 2016/3/2
 */
public class RpcExporter {
    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public static void exporter(String hostname, int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(hostname, port));
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new ExporterTask(socket));
            }
        } finally {
            serverSocket.close();
        }
    }

    static class ExporterTask implements Runnable {
        Socket client;

        ExporterTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;

            try {
                input = new ObjectInputStream(client.getInputStream());

                //获取接口名称
                String interfaceName = input.readUTF();

                //通过反射获取服务类
                Class<?> service = Class.forName(interfaceName);

                //获取方法名
                String methodName = input.readUTF();

                //获取参数类型
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();

                //获取参数
                Object[] arguments = (Object[]) input.readObject();

                Method method = service.getMethod(methodName, parameterTypes);

                Object result = method.invoke(service.getInterfaces(), arguments);

                output = new ObjectOutputStream(client.getOutputStream());

                output.writeObject(result);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client!=null){
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
