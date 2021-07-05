package com.conal.dbops4

data class DbOpsImplWithProxy<T : DbOps>(val dbOps: T, val proxy: T)