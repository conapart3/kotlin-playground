package com.conal.reflectiveutilsjava;

import com.conal.reflectiveutils.CordaInject;
import com.conal.reflectiveutils.Flow;
import com.conal.reflectiveutils.SignTransactionFlow;
import com.conal.reflectiveutils.SignedTransaction;
import com.conal.reflectiveutils.SignedTransactionImpl;

import java.lang.reflect.Field;
import java.util.Collection;

public class Runner {
    public static void main(String[] args) {
        Collection<Field> fields = new com.conal.reflectiveutils.Runner().injectDependencies(
                new Flow<String>() {
                    @CordaInject
                    private String someString;
                    private String nonImportableString;
                    @Override
                    public String call() {
                        return "str";
                    }
                }
        );
        System.out.println("Successfully obtained fields for anonymous impl of abstract class SignTransactionFlow in Java:");
        System.out.println(fields);

        Collection<Field> fields2 = new com.conal.reflectiveutils.Runner().injectDependencies(
                new SignTransactionFlow() {
                    @CordaInject
                    private String someString;
                    private String nonImportableString;
                    @Override
                    public SignedTransaction call() {
                        return new SignedTransactionImpl();
                    }
                }
        );
        System.out.println("Successfully obtained fields for anonymous impl of abstract class SignTransactionFlow in Java:");
        System.out.println(fields2);
    }
}
