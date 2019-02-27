package com.okcoin;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Supplier;

/**
 * @author Liu Hailin
 * @create 2017-09-18 下午6:54
 **/
public class TestMeta {

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType( Object.class );
        MethodType actualMethodType = MethodType.methodType( String.class );
        MethodType invokedType = MethodType.methodType( Supplier.class );
        CallSite site = LambdaMetafactory.metafactory( caller,
            "get",
            invokedType,
            methodType,
            caller.findStatic( TestMeta.class, "print", actualMethodType ),
            methodType );
        MethodHandle factory = site.getTarget();
        Supplier<String> r = (Supplier<String>)factory.invoke();
        System.out.println( r.get() );
    }

    private static String print() {
        return "hello world";
    }

}
