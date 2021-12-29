package kotlinx.datetime

public data class Year(override val year: Int) : AtLeastYear {

    override fun toComparisonInstant(): Instant =
        "${year.formatLen(4)}-01-01T00:00:00Z".toInstant()
}
