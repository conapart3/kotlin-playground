package checksum

import java.io.InputStream
import java.security.DigestInputStream
import java.security.MessageDigest

object Checksum {
    fun generateChecksum(vararg input: InputStream): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")

        input.forEach {
            DigestInputStream(it, messageDigest).use { dis ->
                val bytes = ByteArray(1024 * 1024)
                @Suppress("EmptyWhileBlock")
                while (dis.read(bytes) != -1) {
                }
            }
        }
        return String(messageDigest.digest())
    }
}