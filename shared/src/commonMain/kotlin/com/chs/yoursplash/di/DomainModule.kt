package com.chs.yoursplash.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataModule::class])
@ComponentScan("com.chs.yoursplash.domain")
class DomainModule