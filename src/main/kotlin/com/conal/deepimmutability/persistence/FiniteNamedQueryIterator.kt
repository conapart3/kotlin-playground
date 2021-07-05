package com.conal.deepimmutability.persistence

import com.conal.corda.uncheckedCast
import java.util.*
import java.util.function.Consumer
import kotlin.NoSuchElementException

class FiniteNamedQueryIterator<T>(
    val queue: Queue<T>,
    val resultClass: Class<T>
) : Iterator<T> {

    /**
     * Iterates over the items in the named query result set.
     *
     * Handles remote fetching of data from remote persistence APIs.
     *
     * Applies action to each item.
     */
    fun myForEach(action: Consumer<in T?>) {
        while (hasNext()) {
            action.accept(next())
        }
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * If the iteration's queue has ran out of elements and there are more items to fetch, we fetch them to ensure there are actually more
     * items.
     *
     * If the queue is empty and there are no more items to fetch, we know this is the end of the iteration.
     */
    override fun hasNext(): Boolean {
        return when {
            queue.isNotEmpty() -> {
                true
            }
            else -> {
                false
            }
        }
    }

    /**
     * Returns the next element in the iteration, and performs any remote fetching if necessary.
     */
    override fun next(): T {
//        return try {
            return if (hasNext()) {
                queue.poll()
            } else {
                throw NoSuchElementException("Reached end of named query iterator. No more elements.")
            }
//        } catch (e: ClassCastException) {
//            val message = "Query Iterator failure to cast "
//            println(message)
//            throw Exception(message, e)
//        }
    }

    /**
     * Perform the action on all remaining items, including the rest of the pages, and repopulate the queue.
     */
    override fun forEachRemaining(action: Consumer<in T>) {
        while (hasNext()) {
            action.accept(next())
        }
    }

    /**
     * This code should call the remotePersistenceService with a request to fetch the next batch of data.
     * It should suspend the current fiber (or sleep the current thread).
     * It should reawaken the fiber with the results from the remote persistence service.
     */
    fun repopulateQueue(list: List<Any?>) {
        queue.addAll(uncheckedCast(list))
//        if(list.filterIsInstance(resultClass).size != list.size) {
//            throw Exception("Fetched named query results contains instances that are not '$resultClass'")
//        }
        // most promising:
//        list.mapNotNull {
//            if(resultClass.isAssignableFrom(it!!.javaClass) || resultClass.isPrimitive) {
//                println("CONAL - adding ${it.javaClass}")
//                queue.add(uncheckedCast(it))
//            } else {
//                throw Exception("NOPE")
//            }
//        }

        // does not work, doesn't allow java.lang.Integer to go into Int field
        /*queue.addAll(
            uncheckedCast(
                list.mapNotNull {
                    if(!it!!.javaClass.isAssignableFrom(resultClass)){
                        throw Exception("Item of type ${it.javaClass} is not assignable into the QueryIterator resultClass type $resultClass")
                    }
                }
            )
        )*/
    }
}