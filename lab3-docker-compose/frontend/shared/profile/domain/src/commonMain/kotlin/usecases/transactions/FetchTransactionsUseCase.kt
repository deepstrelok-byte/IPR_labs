package usecases.transactions

import repositories.TransactionsRepository


class FetchTransactionsUseCase(
    private val repository: TransactionsRepository,
) {
    operator fun invoke(userId: String) = repository.fetchTransactions(userId)
}