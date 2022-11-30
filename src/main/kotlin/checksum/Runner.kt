package checksum

import java.io.ByteArrayInputStream

fun main() {
    val file1 = "text1".toByteArray()
    val stream1 = ByteArrayInputStream(file1)
    val file2 = "text2".toByteArray()
    val stream2 = ByteArrayInputStream(file2)

    val sum1 = Checksum.generateChecksum(stream1, stream2)
    val sum2 = Checksum.generateChecksum(stream2, stream1)
    val sum3 = Checksum.generateChecksum(stream1)
    val sum4 = Checksum.generateChecksum(stream2)

    println(sum1)
    println(sum2)
    println(sum3)
    println(sum4)
}