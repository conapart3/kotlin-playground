//package suspend
//
//import java.util.UUID
//import java.util.concurrent.CompletableFuture
//import kotlin.coroutines.Continuation
//import kotlin.coroutines.CoroutineContext
//import kotlin.coroutines.startCoroutine
//import kotlinx.coroutines.runBlocking
//
//fun main() {
//    runBlocking {
//        postItem(Item())
//    }
//}
//
//fun <T> future(
//    context: CoroutineContext = DefaultDispatcher,
//    block: suspend () -> T
//): CompletableFuture<T> {
//    val future = CompletableFuture<T>()
//    block.startCoroutine(completion = object : Continuation<T> {
//
//    })
//    return future
//}
//
//data class Item(val name: String = UUID.randomUUID().toString())
//data class Token(val name: String = UUID.randomUUID().toString())
//data class Post(val item: Item, val token: Token)
//
//suspend fun postItem(item: Item) {
//    val token = requestToken()
//    val post = createPost(token, item)
//    processPost(post)
//}
//
//fun requestToken(): Token {
//    return Token()
//}
//
//fun createPost(t: Token, i: Item): Post {
//    return Post(i, t)
//}
//
//fun processPost(p: Post) {
//}