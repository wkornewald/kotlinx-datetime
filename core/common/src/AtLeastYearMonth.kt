package kotlinx.datetime

public sealed interface AtLeastYearMonth : AtLeastYear {

    public val month: Month

    public companion object {

        public fun parse(value: String): AtLeastYearMonth {
            TODO()
        }
    }
}
