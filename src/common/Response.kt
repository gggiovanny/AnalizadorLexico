package common

data class Response<T>(
        var sucess: Boolean,
        var message: String? = null,
        var atacched: T? = null
)