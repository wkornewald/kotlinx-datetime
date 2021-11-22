/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

/**
 * The minimum [LocalDate] representable on the platform.
 */
expect val LocalDate.Companion.MIN: LocalDate

/**
 * The maximum [LocalDate] representable on the platform.
 */
expect val LocalDate.Companion.MAX: LocalDate

/**
 * The minimum [LocalDateTime] representable on the platform.
 */
expect val LocalDateTime.Companion.MIN: LocalDateTime

/**
 * The maximum [LocalDateTime] representable on the platform.
 */
expect val LocalDateTime.Companion.MAX: LocalDateTime

/**
 * The minimum [Instant] representable on the platform.
 */
expect val Instant.Companion.MIN: Instant

/**
 * The maximum [Instant] representable on the platform.
 */
expect val Instant.Companion.MAX: Instant

/**
 * Displays the given Instant in the given [offset].
 *
 * Be careful: this function may throw for some values of the [Instant].
 */
internal expect fun Instant.toStringWithOffset(offset: UtcOffset): String
