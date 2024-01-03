//package executor.builderstyle
//
//import executor.CordaConsumerRecord
//import java.util.concurrent.Future
//
//interface TaskManager {
//    fun <T, R> submit(items: List<T>, work: (T) -> R): List<Future<R>>
//    fun <K, T, R> submit(items: List<T>, threadGroupingStrategy: (List<T>) -> Map<K, List<T>>, work: (T) -> R): List<Future<R>>
//    fun <K, T, R> submit(taskBuilder: TaskBuilder): List<Future<R>>
//
//    fun close()//todo remove
//}
//
//interface TaskBuilder {
//    fun <T, R> withWork(work: (T) -> R): TaskBuilder
//    fun <T, R> withTasks(tasks: List<T>): TaskBuilder
//    fun <K, T, R> withGrouping(groupingStrategy: (List<T>) -> Map<K, List<T>>): TaskBuilder
//    fun <K, T, R> withPriority(comparator: Comparator<T>): TaskBuilder
//    fun build(): TaskManager
//}
//
//class TaskBuilderImpl<K, T, R> : TaskBuilder {
//
//    private lateinit var work: (T) -> R
//    private lateinit var tasks: List<T>
//    private var groupingStrategy: ((List<T>) -> Map<K, List<T>>)? = null
//    private var priorityStrategy: Comparator<T>? = null
//
//    override fun <T, R> withWork(work: (T) -> R): TaskBuilder {
//        TODO("Not yet implemented")
//    }
//
//    override fun <T, R> withTasks(tasks: List<T>): TaskBuilder {
//        TODO("Not yet implemented")
//    }
//
//    override fun <K, T, R> withGrouping(groupingStrategy: (List<T>) -> Map<K, List<T>>): TaskBuilder {
//        this.groupingStrategy = groupingStrategy
//    }
//
//    override fun <K, T, R> withPriority(comparator: Comparator<T>): TaskBuilder {
//        this.priorityStrategy = comparator
//        return this
//    }
//
//    override fun build(): TaskManager {
//        TODO("Not yet implemented")
//    }
//}
//
//class TaskManagerImpl : TaskManager {
//    override fun <T, R> submit(items: List<T>, work: (T) -> R): List<Future<R>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun <K, T, R> submit(items: List<T>, threadGroupingStrategy: (List<T>) -> Map<K, List<T>>, work: (T) -> R): List<Future<R>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun close() {
//        TODO("Not yet implemented")
//    }
//}
//
//fun main() {
//    TaskBuilderImpl<String, CordaConsumerRecord<String, String>, String>()
//        .withWork {  }
//}