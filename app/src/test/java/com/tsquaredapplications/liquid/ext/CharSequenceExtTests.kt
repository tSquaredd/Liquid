package com.tsquaredapplications.liquid.ext

import com.tsquaredapplications.liquid.login.PasswordValidation
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CharSequenceExtTests {

    @Test
    fun testNoDomain() {
        assertFalse { "derp".isValidEmail() }
    }

    @Test
    fun testOnlyDomain() {
        assertFalse { "@gmail.com".isValidEmail() }
    }

    @Test
    fun testValidEmail() {
        assertTrue { "derptaties@bob.com".isValidEmail() }
    }

    @Test
    fun testEmptyPassword() {
        assertTrue { "".isValidPassword() == PasswordValidation.InvalidTooShort }
    }

    @Test
    fun testShortPassword() {
        assertTrue { "short".isValidPassword() == PasswordValidation.InvalidTooShort }
    }

    @Test
    fun testLongPassword() {
        assertTrue { "thispasswordiswaywaywaytooooooooolong".isValidPassword() == PasswordValidation.InvalidTooLong }
    }

    @Test
    fun testNoUpperCase() {
        assertTrue { "nouppercase!".isValidPassword() == PasswordValidation.InvalidNoUpperCase }
    }

    @Test
    fun testValidPassword() {
        assertTrue { "ValidPassw0rd!".isValidPassword() == PasswordValidation.Valid }
    }
}