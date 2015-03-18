package org.rpc4j.sample.client;

public interface HelloService {

    String hello(String name);

    String hello(Person person);
}
