/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

actual val LocalDate.Companion.MIN: LocalDate get() = LocalDate(YEAR_MIN, 1, 1)

actual val LocalDate.Companion.MAX: LocalDate get() = LocalDate(YEAR_MAX, 12, 31)

actual val LocalDateTime.Companion.MIN: LocalDateTime get() = LocalDateTime(LocalDate.MIN, LocalTime.MIN)

actual val LocalDateTime.Companion.MAX: LocalDateTime get() = LocalDateTime(LocalDate.MAX, LocalTime.MAX)

actual val Instant.Companion.MIN: Instant get() = Instant(MIN_SECOND, 0)

actual val Instant.Companion.MAX: Instant get() = Instant(MAX_SECOND, 999_999_999)

actual fun Instant.toStringWithOffset(offset: UtcOffset): String = toString(offset)