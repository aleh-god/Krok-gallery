package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.data.repositories.NetworkRepository
import by.godevelopment.kroksample.domain.model.DetailsModel
import by.godevelopment.kroksample.domain.model.Result
import kotlinx.coroutines.flow.flow
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




//        flow {
//        emit(networkRepository.getView(id))
//    }.map {
//        when(it) {
//            is Result.Success -> {
//                val data = it.data
//                val model = DetailsModel(
//                    header = it.data.name,
//                    headerText = it.data.logo,
//                    text = it.data.text,
//                    sound = it.data.sound,
//                    pictures = it.data.photo
//                )
//                Result.Success(model)
//            }
//            is Result.Error -> {
//                val message = it.message
//                Result.Error(message)
//            }
//        }
//    }
}