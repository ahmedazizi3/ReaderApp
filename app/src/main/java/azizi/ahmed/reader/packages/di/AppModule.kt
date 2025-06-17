package azizi.ahmed.reader.packages.di

import azizi.ahmed.reader.packages.network.BooksAPI
import azizi.ahmed.reader.packages.repository.BooksRepository
import azizi.ahmed.reader.packages.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBooksRepository(booksAPI: BooksAPI) = BooksRepository(booksAPI)

    @Singleton
    @Provides
    fun provideBookAPI(): BooksAPI {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksAPI::class.java)
    }

}