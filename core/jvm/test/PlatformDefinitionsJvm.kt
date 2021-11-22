/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

import java.time.*

/**
 * The minimum [LocalDate] representable on the platform.
 */
actual val LocalDate.Companion.MIN: LocalDate
    get() = LocalDate(java.time.LocalDate.MIN)

/**
 * The maximum [LocalDate] representable on the platform.
 */
actual val LocalDate.Companion.MAX: LocalDate
    get() = LocalDate(java.time.LocalDate.MAX)

/**
 * The minimum [LocalDateTime] representable on the platform.
 */
actual val LocalDateTime.Companion.MIN: LocalDateTime
    get() = LocalDateTime(java.time.LocalDateTime.MIN)

/**
 * The maximum [LocalDateTime] representable on the platform.
 */
actual val LocalDateTime.Companion.MAX: LocalDateTime
    get() = LocalDateTime(java.time.LocalDateTime.MAX)

/**
 * The minimum [Instant] representable on the platform.
 */
actual val Instant.Companion.MIN: Instant
    get() = Instant(java.time.Instant.MIN)

/**
 * The maximum [Instant] representable on the platform.
 */
actual val Instant.Companion.MAX: Instant
    get() = Instant(java.time.Instant.MAX)

/**
 * Displays the given Instant in the given [offset].
 *
 * Be careful: this function may throw for some values of the [Instant].
 */
actual fun Instant.toStringWithOffset(offset: UtcOffset): String =
    OffsetDateTime.ofInstant(this.value, offset.zoneOffset).toString()