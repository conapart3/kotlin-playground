package com.conal.main;

import kotlin.sequences.Sequence;

import java.io.Serializable;

@FunctionalInterface
public interface VaultQueryPostProcessorFunction<T extends ContractState, R> extends Serializable {
    Sequence<R> postProcess(Sequence<T> input);
}