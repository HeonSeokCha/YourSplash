package com.chs.yoursplash.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DomainModule::class])
@ComponentScan("com.chs.yoursplash.presentation")
class ViewModelModule