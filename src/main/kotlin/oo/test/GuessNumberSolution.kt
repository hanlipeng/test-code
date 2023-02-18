package oo.test

import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors
import kotlin.math.log2

val totalList = buildArray()

private fun buildArray(): List<List<Int>> {
    val result = ArrayList<List<Int>>()
    for (a1 in 0..9) {
        for (a2 in 0..9) {
            for (a3 in 0..9) {
                for (a4 in 0..9) {
                    val element = listOf(a1, a2, a3, a4)
                    if (element.toSet().size != 4) {
                        continue
                    }
                    result.add(element)
                }
            }
        }
    }
    return result
}

val idGenerator = AtomicInteger(1)


fun caculateE(result: Map<String, List<*>>, totalNumber: Int) = result.values.map {
    val p = it.size.toDouble() / totalNumber
    0 - p * log2(p)
}.reduceRight { a, b -> a + b }


fun filterWhichNumberFollowTheRule(
    numberCombine: List<Int>,
    lastNumberCombine: List<List<Int>>
): Map<String, List<List<Int>>> {
    return lastNumberCombine.parallelStream()
        .map {
            compareNumber(numberCombine, it) to it
        }.collect(Collectors.groupingBy({ it.first }, Collectors.mapping({ it.second }, Collectors.toList())))
}

fun compareNumber(
    numberCombine: List<Int>,
    needCompareCombine: List<Int>
): String {
    var correct = 0
    var positionCorrect = 0
    for (i in 0..3) {
        val number = numberCombine[i]
        if (number == needCompareCombine[i]) {
            correct++
        } else if (number in needCompareCombine) {
            positionCorrect++
        }
    }
    return "${correct}A${positionCorrect}B"
}


fun init(): GuessNumberSolution {
    return GuessNumberSolution("|", totalList)
}

class GuessNumberSolution(val name: String, val lastList: List<List<Int>>) {

    val lastCount = lastList.size

    val step = lastList.parallelStream().map {
        val rule = filterWhichNumberFollowTheRule(it, lastList)
        it to rule
    }.collect(Collectors.toMap({ it.first }, { it.second }))

    val p = step.entries.stream().map { caculateE(it.value, lastCount) to it.key }
        .sorted(
            Comparator.comparingDouble<Pair<Double, List<Int>>?> { it.first }.reversed()
                .thenComparing(Comparator.comparing { it.second.joinToString() })
        )
        .collect(Collectors.toList())

    val pDetail = step.map {
        it.key.joinToString() to it.value.mapValues { it.value.size }
    }.toMap()


    fun next(result: String, value: List<Int>): GuessNumberSolution {
        return GuessNumberSolution(
            "$name|$value|$result",
            step.getOrDefault(value, emptyMap()).getOrDefault(result, emptyList())
        )
    }

    fun getMaxProbability(): List<Int> {
        return p[0].second
    }

    fun printProbability() {
        p.stream().limit(10).forEach { println("v:${it.second} e:${it.first}") }
    }

}


fun main() {
    val scanner = Scanner(System.`in`)
    var solution = init()
    while (true) {
        solution.printProbability()
        println("input value")
        val value = scanner.next().toCharArray().map { it.digitToInt() }.toList()
        println("input result")
        val result = scanner.next()
        solution = solution.next(result, value)
    }

}