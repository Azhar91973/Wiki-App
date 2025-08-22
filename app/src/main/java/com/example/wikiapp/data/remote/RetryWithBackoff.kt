package com.example.wikiapp.data.remote

suspend fun <T> retryWithBackoff(
    maxRetries: Int = 3,
    initialDelayMillis: Long = 1000,
    block: suspend () -> T
): T {
    var attempt = 0
    var delayMillis = initialDelayMillis

    while (true) {
        try {
            return block()
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 429 && attempt < maxRetries) {
                val retryAfterHeader = e.response()?.headers()?.get("Retry-After")
                val waitTime = retryAfterHeader?.toLongOrNull()?.times(1000) ?: delayMillis
                kotlinx.coroutines.delay(waitTime)

                delayMillis *= 2
                attempt++
            } else {
                throw e
            }
        }
    }
}
