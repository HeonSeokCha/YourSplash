package util

data class SearchFilter(
    val orderBy: String = Constants.SORT_BY_LIST.first { it.first == "RELEVANCE" }.second,
    val color: String? = Constants.SEARCH_COLOR_LIST.first { it.first == "Any" }.second,
    val orientation: String? = Constants.SEARCH_ORI_LIST.first { it.first == "Any" }.second
)
