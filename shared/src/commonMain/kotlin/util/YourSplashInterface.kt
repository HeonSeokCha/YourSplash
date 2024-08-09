package util

expect interface Navigator {
    fun navigateToSecondActivity(type: String, id: String)
    fun finish()
}