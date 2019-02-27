package com.okcoin;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Supplier;

interface TestDemo {
    String run(String name);
}

/**
 * @author Liu Hailin
 * @create 2017-09-18 下午6:54
 **/
public class TestMeta2 {

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType invokedType = MethodType.methodType( TestDemo.class );
        MethodType methodType = MethodType.methodType( String.class, String.class );
        MethodType actualMethodType = MethodType.methodType( String.class );

        MethodHandle mh = MethodHandles.lookup().findVirtual( TestDemo.class, "run", methodType );

        CallSite site = LambdaMetafactory.metafactory( caller,
            "run",
            invokedType,
            methodType,
            mh,
            mh.type() );
        MethodHandle factory = site.getTarget();
        Supplier<String> r = (Supplier<String>)factory.invoke();
        System.out.println( r.get() );
    }
}
