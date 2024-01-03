//package executor.withcoroutines
//
//import java.util.concurrent.CompletableFuture
//
///**
// * Type of task submitted to the [TaskManager].
// */
//enum class TaskType {
//    FLOW, LONG_RUNNING, SCHEDULED
//}
//
///**
// * A task to be submitted for execution by the [TaskManager].
// */
//interface Task<T, R> {
//    /**
//     * Unique identifier of this task.
//     */
//    val id: String
//
//    /**
//     * Input of the task.
//     */
//    val input: T
//
//    /**
//     * Type of task.
//     */
//    val type: TaskType
//
//    /**
//     * Work to be executed taking input.
//     */
//    val work: (T) -> R
//}
//
//data class TaskImpl<T, R>(
//    override val id: String,
//    override val input: T,
//    override val type: TaskType,
//    override val work: (T) -> R
//) : Task<T, R>
//
///**
// * A service for managing tasks and exporting metric information about thread utilization.
// */
//interface TaskManager {
//    /**
//     * Submit [task] to be processed.
//     *
//     * It is guaranteed one invocation to this API will execute input on one thread, for example, if the input is a list, all items in
//     * the list will be executed on the same thread.
//     */
//    fun <T, R> submit(task: Task<T, R>): CompletableFuture<R>
//
//    // this API can be batched under the hood. but not necessary for now.
////    fun <T, R> submit(tasks: List<Task<T>>, work: (T) -> R): List<CompletableFuture<R>>
//    // scheduling APIS (for later)
////    fun <K, T, R> schedule(task: Task<T>, delay: Long, unit: TimeUnit, work: (T) -> R): ScheduledFuture<R>
////    fun <K, T, R> scheduleAtFixedRate(task: Task<T>, initialDelay: Long, period: Long, unit: TimeUnit, work: (T) -> R): ScheduledFuture<R>
////    fun <T, R> submit(task: Task<T>, work: (T) -> R): Future<R>
//
//    // todo remove off the API
//    fun close()
//}
//
//class TaskManagerImpl : TaskManager {
//    override fun <T, R> submit(task: Task<T>, work: (T) -> R): CompletableFuture<R> {
//        val job = Job()
//
//    }
//
//    override fun close() {
//        TODO("Not yet implemented")
//    }
//}
//
//fun main() {
//    val job = Job()
//}