package com.hyshchak.findcharacter.di

import android.content.Context
import androidx.room.Room
import com.hyshchak.findcharacter.data.ApiDataSource
import com.hyshchak.findcharacter.database.main.PeopleDatabase
import com.hyshchak.findcharacter.network.ApiService
import com.hyshchak.findcharacter.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PeopleDatabase::class.java,
        "people_database"
    ).allowMainThreadQueries()
        .build()

    @Provides
    fun providePeopleDao(db: PeopleDatabase) = db.personDao

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val logging = HttpLoggingInterceptor()

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()

    @Provides
    @Singleton
    fun provideApiClient(apiService: ApiService): ApiDataSource {
        return ApiDataSource(apiService)
    }
}
