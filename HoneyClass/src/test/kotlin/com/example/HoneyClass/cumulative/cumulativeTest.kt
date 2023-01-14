package com.example.HoneyClass.cumulative

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class cumulativeTest {

    @Test
    fun cuTest(){
        val cumulative = Cumulative()

        var zScore1 = 1.9235
        var isPos1 = 1
        if(zScore1 < 0) {
            isPos1 = 0
            zScore1 *= -1F
        }
        var favor1 = cumulative.cumul[isPos1][(zScore1 * 10.0F).toInt()][(zScore1 * 100.0F).toInt() % 10].toFloat()

        Assertions.assertThat(favor1).isEqualTo(0.97257F)

        var zScore2 = -1.9235
        var isPos2 = 1
        if(zScore2 < 0) {
            isPos2 = 0
            zScore2 *= -1F
        }
        var favor2 = cumulative.cumul[isPos2][(zScore2 * 10.0F).toInt()][(zScore2 * 100.0F).toInt() % 10].toFloat()

        Assertions.assertThat(favor2).isEqualTo(0.02743F)
    }
}