package com.conal.dbops4.java.java;

import com.conal.corda.*;
import com.conal.dbops4.DbOps;
import com.conal.dbops4.DbOpsInject;
import com.conal.main.ContractState;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class MyJavaFlow implements Flow<List<ContractState>> {

    @DbOpsInject(impl = MyJavaDbOps.class)
    private IMyJavaDbOps myDbOps;

    @Override
    public List<ContractState> call() {
        NamedQueryRequestImpl request = new NamedQueryRequestImpl("queryName", new HashMap<>(), null, null, 200);
        List<ContractState> results = myDbOps.getMyStates(request);
        return results;
    }
}

interface IMyJavaDbOps extends DbOps {
    List<ContractState> getMyStates(@NotNull NamedQueryRequest request);
}

class MyJavaDbOps implements IMyJavaDbOps {

    @CordaInject
    private PersistenceService persistenceService;

    @Override
    public List<ContractState> getMyStates(@NotNull NamedQueryRequest request) {
        Consumer<EntityManager> entityManagerConsumer = (EntityManager em) -> em.persist(new Object());
        persistenceService.withEntityManager(entityManagerConsumer);

        return persistenceService.withNamedQueryService(
                (NamedQueryService nqs) -> nqs.query(request, new IdentityContractStatePostProcessor())
        );
    }
}