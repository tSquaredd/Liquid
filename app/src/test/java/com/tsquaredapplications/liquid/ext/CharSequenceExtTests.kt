package com.tsquaredapplications.liquid.ext


import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CharSequenceExtTests {

    @Test
    fun testValidEmail() {
        assertTrue("test@aol.com".validateEmail())
    }

    @Test
    fun testDoubleDotDomain() {
        assertTrue("test@secon.first.org".validateEmail())
    }

    @Test
    fun testInvalidEmail() {
        assertFalse("notValid".validateEmail())
    }

    @Test
    fun testInvalidTwo() {
        assertFalse("almostValidgmail.com".validateEmail())
    }
}