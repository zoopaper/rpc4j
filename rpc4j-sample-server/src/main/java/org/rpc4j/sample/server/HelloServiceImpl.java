package org.rpc4j.sample.server;

import org.rpc4j.sample.api.HelloService;
import org.rpc4j.sample.api.Person;
import org.rpc4j.server.annotation.RPCService;


@RPCService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public String hello(Person person) {
        return "Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
