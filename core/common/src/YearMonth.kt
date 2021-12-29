package kotlinx.datetime

public data class YearMonth(override val year: Int, override val month: Month) : AtLeastYearMonth {

    override fun toComparisonInstant(): Instant =
        "${year.formatLen(4)}-${month.number.formatLen(2)}-01T00:00:00Z".toInstant()
}
