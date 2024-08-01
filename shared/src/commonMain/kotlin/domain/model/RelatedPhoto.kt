package domain.model

import domain.model.Photo

data class RelatedPhoto(
    val total: Int,
    val results: List<Photo>
)
