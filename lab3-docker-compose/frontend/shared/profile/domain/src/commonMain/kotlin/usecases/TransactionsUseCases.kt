package usecases

import repositories.TransactionsRepository
import usecases.transactions.FetchTransactionsUseCase

class TransactionsUseCases(
    repository: TransactionsRepository
) {
    val fetchTransactions = FetchTransactionsUseCase(repository)
}