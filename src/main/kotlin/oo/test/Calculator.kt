package oo.test

/**
 * @author hanlipeng
 * @date 2023/2/7
 */
class Calculator {

    private val firstFrame = Frame(1, null)
    private var lastFrame = firstFrame

    fun nextPoint(point: Int) {
        lastFrame = lastFrame.nextPoint(point)
    }

    fun totalPoints(): Int {
        return firstFrame.pointSum()
    }

    fun possibleMax(): Int {
        return 0
    }

    open class Frame(
        private val frameNum: Int,
        private val previousFrame: Frame?,
        private val frameMax: Int = 10,
        private val throwMax: Int = 2
    ) {
        private lateinit var firstThrow: Throw
        private lateinit var lastThrow: Throw
        private var throwCount = 0
        private var nextFrame: Frame? = null
        private var frameResult: FrameResult = FrameResult.INCOMPLETE

        open fun nextPoint(point: Int): Frame {
            if (frameNum > frameMax) {
                throw GameHasFinishedException()
            }
            if (frameResult.isComplete()) {
                nextFrame = newFrame().nextPoint(point)
                return nextFrame!!
            }
            if (throwCount == 0) {
                if (point > 10) throw InvalidPointException()
                firstThrow = previousFrame?.lastThrow?.nextPoint(point, frameNum) ?: Throw(point, frameNum)
                lastThrow = firstThrow
                throwCount++
            } else {
                if (currentFramePoint() + point > 10) throw InvalidPointException()
                lastThrow = lastThrow.nextPoint(point, frameNum)
                throwCount++
            }
            if (isFinished()) {
                markFrameResult()
            }
            return this
        }

        private fun newFrame() =
            Frame(frameNum + 1, this, frameMax, throwMax)


        fun point(): Int {
            if (!isFinished()) throw GameNotFinishedException()
            if (isLastFrame()) return currentFramePoint()
            return currentFramePoint() + lastThrow.bonusPoint(frameResult.bonusThrow)
        }

        private fun isLastFrame(): Boolean = frameMax == frameNum

        fun pointSum(): Int {
            return try {
                point() + (nextFrame?.pointSum() ?: 0)
            } catch (e: GameNotFinishedException) {
                0
            }
        }


        private fun markFrameResult() {
            frameResult =
                if (currentFramePoint() == 10) {
                    if (throwCount == 1) {
                        FrameResult.STRIKE
                    } else {
                        FrameResult.SPARE
                    }
                } else {
                    FrameResult.NORMAL
                }
        }

        private fun isFinished(): Boolean {
            if (isLastFrame()) {
                return throwCount > throwMax
            }
            return throwCount >= throwMax || currentFramePoint() == 10
        }

        private fun currentFramePoint(): Int {
            return firstThrow.framePoint()
        }


    }

    class Throw(
        val point: Int,
        val frameNum: Int
    ) {
        var nextThrow: Throw? = null

        fun nextPoint(point: Int, frameNum: Int): Throw {
            val t = Throw(point, frameNum)
            this.nextThrow = t
            return t
        }

        fun framePoint(): Int {
            return point + (nextThrow?.takeIf { it.frameNum == this.frameNum }?.framePoint() ?: 0)
        }

        fun bonusPoint(bonusThrow: Int): Int {
            if (bonusThrow == 0) {
                return 0
            }
            return (nextThrow?.point ?: 0) + (nextThrow?.bonusPoint(bonusThrow - 1) ?: 0)
        }
    }

    enum class FrameResult(val bonusThrow: Int) {
        STRIKE(2),
        SPARE(1),
        NORMAL(0),
        INCOMPLETE(0);

        fun isComplete(): Boolean {
            return this != INCOMPLETE
        }

    }


}

open class BowlingException(override val message: String) : Exception()

class GameNotFinishedException() : BowlingException("game not finished")
class GameHasFinishedException() : BowlingException("game finished")
class InvalidPointException() : BowlingException("point is invalid")