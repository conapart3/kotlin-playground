package com.conal.dbops1

data class DbOpsImplWithProxy<T : DbOps>(val dbOps: T, val proxy: T)