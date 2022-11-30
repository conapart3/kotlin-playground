package persistence.cpx

import org.hibernate.type.AbstractSingleColumnStandardBasicType
import org.hibernate.type.MaterializedBlobType
import org.hibernate.type.descriptor.java.PrimitiveByteArrayTypeDescriptor
import org.hibernate.type.descriptor.sql.VarbinaryTypeDescriptor

// A tweaked version of `org.hibernate.type.WrapperBinaryType` that deals with ByteArray (java primitive byte[] type).
object CordaWrapperBinaryType : AbstractSingleColumnStandardBasicType<ByteArray>(VarbinaryTypeDescriptor.INSTANCE, PrimitiveByteArrayTypeDescriptor.INSTANCE) {
    override fun getRegistrationKeys(): Array<String> {
        return arrayOf(name, "ByteArray", ByteArray::class.java.name)
    }

    override fun getName(): String {
        return "corda-wrapper-binary"
    }
}

object MapBlobToNormalBlob : MaterializedBlobType() {
    override fun getName(): String {
        return "corda-blob"
    }
}
