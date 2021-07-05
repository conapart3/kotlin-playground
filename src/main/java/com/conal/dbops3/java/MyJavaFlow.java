package com.conal.dbops3.java;

import com.conal.corda.*;
import com.conal.dbops3.*;
import com.conal.main.ContractState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class MyJavaFlow implements Flow<List<ContractState>> {

    @DbOpsInject(impl = MyJavaDbOps.class)
    private DbOps<NamedQueryRequest, List<ContractState>> dbOps;

    @Override
    public List<ContractState> call() {
        NamedQueryRequestImpl request = new NamedQueryRequestImpl("queryName", new HashMap<>(), null, null, 200);
        List<ContractState> results = dbOps.execute(request);
        return results;
    }
}

class MyJavaDbOps implements DbOps<NamedQueryRequest, List<ContractState>> {

    @CordaInject
    private PersistenceService persistenceService;

    @Nullable
    @Override
    public List<ContractState> execute(@Nullable NamedQueryRequest args) {
        return persistenceService.withNamedQueryService((NamedQueryService nqs) -> nqs.query(args, new IdentityContractStatePostProcessor()));
    }
}