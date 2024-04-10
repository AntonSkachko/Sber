package com.anton.sber.module

import com.anton.sber.data.repository.AccountRepository
import com.anton.sber.data.repository.LogRepository
import com.anton.sber.data.repository.TaskRepository
import com.anton.sber.data.repository.UserTaskRepository
import com.anton.sber.data.repository.imp.AccountRepositoryImp
import com.anton.sber.data.repository.imp.LogRepositoryImpl
import com.anton.sber.data.repository.imp.TaskRepositoryImp
import com.anton.sber.data.repository.imp.UserTaskRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAccountRepository(impl: AccountRepositoryImp): AccountRepository

    @Binds
    abstract fun provideLogRepository(impl: LogRepositoryImpl): LogRepository

    @Binds
    abstract fun provideTaskRepository(impl: TaskRepositoryImp): TaskRepository

    @Binds
    abstract fun provideUserTaskRepository(impl: UserTaskRepositoryImp): UserTaskRepository
}