package com.conal.main;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface VaultQueryRPCOps {
    /**
     * Executes the named query defined by the [queryName] and [NamedParameter]s in the [RpcVaultNamedQueryRequest] and returns
     * results in the form of [RpcVaultStateItem]s using durable stream API.
     */
    List<Object> queryVaultByNamedQuery(RpcVaultNamedQueryRequest request);
}