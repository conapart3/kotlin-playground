package com.conal.dbops2

data class DbOpsImplWithProxy<T : DbOps>(val dbOps: T, val proxy: T)