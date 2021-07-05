package com.conal.main;

import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class VaultQueryCaller {
    public static void main(String[] args) {
        VaultQueryRPCOps api = new VaultQueryRPCOpsImpl();

//        api.queryVaultByNamedQuery(new RpcVaultNamedQueryRequest("name", new HashMap<>(),
//                new VaultQueryPostProcessorFunction<ContractState, Object>() {
//                    @NotNull
//                    @Override
//                    public Sequence<GridState> postProcess(@NotNull Sequence<? extends GridState> inputs) {
//                        return (Sequence<GridState>) inputs;
//                    }
//                }));

        api.queryVaultByNamedQuery(new RpcVaultNamedQueryRequest("name", new HashMap<>(),
                (VaultQueryPostProcessorFunction<GridState, GridState> & Serializable) inputs -> (Sequence<GridState>) inputs));

    }
}
