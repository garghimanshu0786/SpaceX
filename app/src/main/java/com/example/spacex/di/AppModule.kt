package com.example.spacex.di

import com.apollographql.apollo.ApolloClient
import com.example.spacex.model.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideClient(): ApolloClient{
        return ApolloClient.builder()
            .serverUrl(Constants.BASEURL)
            .build()
    }
}