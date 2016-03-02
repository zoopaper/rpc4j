package org.rpc4j.sample.api;

public interface HelloService {

    String hello(String name);

    String hello(Person person);
}
