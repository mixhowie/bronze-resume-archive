package com.howie.bronze

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppTest {
    @Test fun appHasAGreeting() {
        val classUnderTest = App()
        assertNotNull(classUnderTest.greeting, "app should have a greeting")
        assertEquals(classUnderTest.greeting, "Hello World!xxx")
    }
}
