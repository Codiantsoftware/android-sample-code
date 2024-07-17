package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.R
import com.example.myapplication.datalayer.api.ApiInterface
import com.example.myapplication.datalayer.repository_impl.AuthRepositoryImpl
import com.example.myapplication.datalayer.repository_impl.LoginRepositoryImpl
import com.example.myapplication.datalayer.repository_impl.LogoutRepositoryImpl
import com.example.myapplication.datalayer.repository_impl.SignUpRepositoryImpl
import com.example.myapplication.datalayer.repository_impl.UserDetailRepositoryImpl
import com.example.myapplication.datalayer.repository_impl.UserRepositoryImpl
import com.example.myapplication.domainlayer.repository.AuthRepository
import com.example.myapplication.domainlayer.repository.LoginRepository
import com.example.myapplication.domainlayer.repository.LogoutRepository
import com.example.myapplication.domainlayer.repository.SignUpRepository
import com.example.myapplication.domainlayer.repository.UserDetailRepository
import com.example.myapplication.domainlayer.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides a SignUpRepository implementation.
     */
    @Provides
    fun provideSignUpRepository(apiInterface: ApiInterface): SignUpRepository {
        return SignUpRepositoryImpl(apiInterface)
    }

    /**
     * Provides a LoginRepository implementation.
     */
    @Provides
    fun provideLoginRepository(apiInterface: ApiInterface): LoginRepository {
        return LoginRepositoryImpl(apiInterface)
    }

    /**
     * Provides a UserRepository implementation.
     */
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    /**
     * Provides a UserDetailRepository implementation.
     */
    @Provides
    fun provideUserDetailRepository(apiInterface: ApiInterface): UserDetailRepository {
        return UserDetailRepositoryImpl(apiInterface)
    }

    /**
     * Provides a LogoutRepository implementation.
     */
    @Provides
    fun provideLogoutRepository(apiInterface: ApiInterface): LogoutRepository {
        return LogoutRepositoryImpl(apiInterface)
    }

    /**
     * Provides a AuthRepository implementation.
     */
    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    /**
     * Provides FirebaseAuth for Google sign in.
     */
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    /**
     * Provides GoogleSignInOptions.
     */
    @Provides
    @Singleton
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    /**
     * Provides GoogleSignInClient.
     */
    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)


}