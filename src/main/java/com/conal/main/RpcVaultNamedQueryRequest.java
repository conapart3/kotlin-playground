package com.conal.main;

import java.util.Map;

public class RpcVaultNamedQueryRequest {
    public RpcVaultNamedQueryRequest(String queryName, Map<String, Object> namedParameters, VaultQueryPostProcessorFunction<? extends ContractState, ?> postProcessor) {
        this.queryName = queryName;
        this.namedParameters = namedParameters;
        this.postProcessor = postProcessor;
    }

    String queryName;
    Map<String, Object> namedParameters;
    VaultQueryPostProcessorFunction<? extends ContractState, ?> postProcessor;
}
