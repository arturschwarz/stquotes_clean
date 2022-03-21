package dev.arturschwarz.stquotes.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import dev.arturschwarz.stquotes.Result
import kotlinx.coroutines.delay

class QuotesRepository(private val httpClient: HttpClient) {

    suspend fun getQuotes() : Result<List<Quote>> {
        return try {
            var quotes: List<Quote>
            withContext(Dispatchers.IO) {
                quotes = httpClient.request<Array<Quote>>("https://strangerthings-quotes.vercel.app/api/quotes/10") {
                    method = HttpMethod.Get
                }.toList()
            }
            Result.Success(quotes)
        } catch (e: Exception) {
            Result.Error(exception = e)
        }
    }

}