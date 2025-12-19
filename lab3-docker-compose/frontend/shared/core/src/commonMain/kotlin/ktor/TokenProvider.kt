package ktor

interface TokenProvider {
    fun getToken(): String?
}