package by.godevelopment.kroksample.domain.model

sealed class Element<T>

class ItemElement<T>(
    val item: T
) : Element<T>()

class ErrorElement<T>(
    val error: Throwable
) : Element<T>()

class CompletedElement<T> : Element<T>()