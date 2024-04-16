package com.example.project_999.subscription.progress.domain

interface SubscriptionInteractor {

    suspend fun subscribe(): SubscriptionResult

    suspend fun subscribeInternal() : SubscriptionResult

    class Base(
        private val repository: SubscriptionRepository
    ) : SubscriptionInteractor {

        override suspend fun subscribe(): SubscriptionResult {

            return if (repository.isUserPremium())
                SubscriptionResult.Success
            else {
                repository.subscribe()
                SubscriptionResult.NoDataYet
            }
        }

        override suspend fun subscribeInternal(): SubscriptionResult {
            repository.subscribeInternal()
            return SubscriptionResult.Success
        }
    }
}

interface SubscriptionResult {

    fun map(mapper: Mapper, canGoBackCallback: (Boolean) -> Unit)

    object Success : SubscriptionResult {

        override fun map(mapper: Mapper, canGoBackCallback: (Boolean) -> Unit) =
            mapper.map(canGoBackCallback)
    }

    object NoDataYet : SubscriptionResult {

        override fun map(mapper: Mapper, canGoBackCallback: (Boolean) -> Unit) = Unit
    }
}

interface Mapper {

    fun map(canGoBackCallback: (Boolean) -> Unit)
}