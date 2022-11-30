package reflection

import java.io.InputStream
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.lang.reflect.ParameterizedType

fun main(){
    NestedGenericsParameterTypeValidator(SomeRpcClass2::class.java).validate()
}


class NestedGenericsParameterTypeValidator(private val clazz: Class<*>) {

    fun validate() {
        clazz.methods.map { method ->
            validateTypeNotNestedGenerics(method)
        }
    }

    private fun validateTypeNotNestedGenerics(method: Method) {
        if (method.parameters.any { it.isNestedGenericType() }) {
            println("nested generic")
        }
    }

    private fun Parameter.isNestedGenericType(): Boolean = (this.parameterizedType is ParameterizedType
            && (this.parameterizedType as ParameterizedType).actualTypeArguments.any { nested -> nested !is Class<*> })
}

interface SomeRpcClass {
    fun upload(streams: List<InputStream>)
}

interface SomeRpcClass2 {
    fun upload(streams: List<SomeInterface>)
}

abstract class SomeAbstractClass

interface SomeInterface
