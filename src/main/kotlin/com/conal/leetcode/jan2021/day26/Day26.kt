package com.conal.leetcode.jan2021.day26

/*fun main(){
    println(Day26().minimumEffortPath(arrayOf(intArrayOf(1,2,3),intArrayOf(3,8,4),intArrayOf(5,3,5))))
}

class Day26{
    fun minimumEffortPath(heights: Array<IntArray>): Int {
        val (startNode: GraphNode, endNode: GraphNode) = buildGraph(heights)

        return dijkstrasAlgorithm(startNode, endNode)
    }

    private fun dijkstrasAlgorithm(startNode: GraphNode, endNode: GraphNode): Int {
        val remaining = startNode

    }

    private fun buildGraph(heights: Array<IntArray>): Pair<GraphNode, GraphNode> {
        val nodeArr: Array<Array<GraphNode>> = arrayOf()
        for(i in 0 until heights.size){
            for(j in 0 until heights[0].size){
                nodeArr[i][j] = GraphNode(heights[0][1])
            }
        }
        for(i in 0 until nodeArr.size){
            for(j in 0 until nodeArr[0].size){
                // left hand column
                if(i == 0){
                    if(j == 0) nodeArr[i][j].edges.addAll(listOf(nodeArr[i+1][j], nodeArr[i][j+1]))
                    else if(j == nodeArr[0].size-1) nodeArr[i][j].edges.addAll(listOf(nodeArr[i+1][j], nodeArr[i][j-1]))
                    else nodeArr[i][j].edges.addAll(listOf(nodeArr[i+1][j], nodeArr[i][j-1]))


                } else if(i == 0 && j < nodeArr[0].size - 1){
                    nodeArr[i][j].edges = listOf(nodeArr[i+1][j], nodeArr[i][j+1])
                }
                nodeArr[i][j].edges = listOf(nodeArr[i-1][j], nodeArr[i+1][j], nodeArr[i][j-1], nodeArr[i][j+1])
            }
        }
    }
}

class GraphNode(val value: Int){
    var visited = false
    var edges: MutableList<GraphNode>
}*/