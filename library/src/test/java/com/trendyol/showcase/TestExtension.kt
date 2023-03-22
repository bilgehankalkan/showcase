package com.trendyol.showcase

import org.junit.Assert

infix fun Any.`should be`(expectedResult: Any) {
    Assert.assertEquals(expectedResult, this)
}