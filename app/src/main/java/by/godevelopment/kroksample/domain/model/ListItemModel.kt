package by.godevelopment.kroksample.domain.model

data class ListItemModel(
    val pictures: String,
    val text: String,
    val onClickNav: (Int) -> Unit
)
