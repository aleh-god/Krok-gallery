package by.godevelopment.kroksample.common

open class AppException : RuntimeException()

class InternetException : AppException()

class ApplicationException : AppException()