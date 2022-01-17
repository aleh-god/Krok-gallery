package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.data.repositories.NetworkRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetViewConvertToModelUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(id: Int) = networkRepository.getAllViews().map { list ->
        list.first {
            it.id == id     // Throws: NoSuchElementException - if no such element is found
        }
    }
}
