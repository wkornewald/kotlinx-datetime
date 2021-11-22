/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

/**
 * The minimum [LocalDate] representable on the platform.
 */
actual val LocalDate.Companion.MIN: LocalDate
    get() = LocalDate(YEAR_MIN, 1, 1)

/**
 * The maximum [LocalDate] representable on the platform.
 */
actual val LocalDate.Companion.MAX: LocalDate
    get() = LocalDate(YEAR_MAX, 12, 31)

/**
 * The minimum [LocalDateTime] representable on the platform.
 */
actual val LocalDateTime.Companion.MIN: LocalDateTime
    get() = LocalDateTime(LocalDate.MIN, LocalTime.MIN)

/**
 * The maximum [LocalDateTime] representable on the platform.
 */
actual val LocalDateTime.Companion.MAX: LocalDateTime
    get() = LocalDateTime(LocalDate.MAX, LocalTime.MAX)

/**
 * The minimum [Instant] representable on the platform.
 */
actual val Instant.Companion.MIN: Instant
    get() = Instant(MIN_SECOND, 0)

/**
 * The maximum [Instant] representable on the platform.
 */
actual val Instant.Companion.MAX: Instant
    get() = Instant(MAX_SECOND, 999_999_999)

/**
 * Displays the given Instant in the given [offset].
 *
 * Be careful: this function may throw for some values of the [Instant].
 */
actual fun Instant.toStringWithOffset(offset: UtcOffset): String = toString(offset)