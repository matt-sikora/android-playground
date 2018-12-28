package io.msikora.starter.sample.di

import com.google.gson.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.msikora.starter.sample.network.HackerNewsAlgolia
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import org.joda.time.DateTime

@Module
object HackerNewsModule {

    @Provides
    @Singleton
    @JvmStatic
    fun hackerNewsAlgolia(baseUrl: String, scheduler: Scheduler): HackerNewsAlgolia = Retrofit.Builder()
        .client(
            OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(
                DateTime::class.java,
                JsonDeserializer<DateTime> { json, _, _ -> DateTime.parse(json.asString) }
            )
            .create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler))
        .build()
        .create(HackerNewsAlgolia::class.java)
}
