package com.conal.corda

import java.lang.RuntimeException

class DbOpsException(message: String?) : RuntimeException(message)