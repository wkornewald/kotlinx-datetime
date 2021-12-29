package kotlinx.datetime

public sealed interface AtLeastYear : Comparable<AtLeastYear> {

    public val year: Int

    override fun compareTo(other: AtLeastYear): Int {
        val result = toComparisonInstant().compareTo(other.toComparisonInstant())
        return if (result == 0) hierarchyLevel.compareTo(other.hierarchyLevel) else result
    }

    public fun toComparisonInstant(): Instant

    public companion object {

        public fun parse(value: String): AtLeastYear {
            TODO()
        }
    }
}

public val AtLeastYear.hierarchyLevel: Int get() = when (this) {
    is Year -> 0
    is YearMonth -> 1
//    is LocalDate -> 2
//    is ZonedDateTime -> 3
}
