package com.conal.dbops3

data class DbOpsImplWithProxy<T : DbOps<*,*>>(val dbOps: T, val proxy: T)