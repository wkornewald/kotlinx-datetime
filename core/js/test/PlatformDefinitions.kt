/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

import kotlinx.datetime.internal.JSJoda.*
import kotlinx.datetime.internal.JSJoda.Instant as jtInstant
import kotlinx.datetime.internal.JSJoda.LocalDate as jtLocalDate
import kotlinx.datetime.internal.JSJoda.LocalDateTime as jtLocalDateTime

/**
 * The minimum [LocalDate] representable on the platform.
 */
actual val LocalDate.Companion.MIN: LocalDate
    get() = LocalDate(jtLocalDate.MIN)

/**
 * The maximum [LocalDate] representable on the platform.
 */
actual val LocalDate.Companion.MAX: LocalDate
    get() = LocalDate(jtLocalDate.MAX)

/**
 * The minimum [LocalDateTime] representable on the platform.
 */
actual val LocalDateTime.Companion.MIN: LocalDateTime
    get() = LocalDateTime(jtLocalDateTime.MIN)

/**
 * The maximum [LocalDateTime] representable on the platform.
 */
actual val LocalDateTime.Companion.MAX: LocalDateTime
    get() = LocalDateTime(jtLocalDateTime.MAX)

/**
 * The minimum [Instant] representable on the platform.
 */
actual val Instant.Companion.MIN: Instant
    get() = Instant(jtInstant.MIN)

/**
 * The maximum [Instant] representable on the platform.
 */
actual val Instant.Companion.MAX: Instant
    get() = Instant(jtInstant.MAX)

/**
 * Displays the given Instant in the given [offset].
 *
 * Be careful: this function may throw for some values of the [Instant].
 */
actual fun Instant.toStringWithOffset(offset: UtcOffset): String =
    OffsetDateTime.ofInstant(this.value, offset.zoneOffset).toString()
