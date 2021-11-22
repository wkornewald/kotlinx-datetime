/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

import java.time.*

actual val LocalDate.Companion.MIN: LocalDate get() = LocalDate(java.time.LocalDate.MIN)

actual val LocalDate.Companion.MAX: LocalDate get() = LocalDate(java.time.LocalDate.MAX)

actual val LocalDateTime.Companion.MIN: LocalDateTime get() = LocalDateTime(java.time.LocalDateTime.MIN)

actual val LocalDateTime.Companion.MAX: LocalDateTime get() = LocalDateTime(java.time.LocalDateTime.MAX)

actual val Instant.Companion.MIN: Instant get() = Instant(java.time.Instant.MIN)

actual val Instant.Companion.MAX: Instant get() = Instant(java.time.Instant.MAX)

actual fun Instant.toStringWithOffset(offset: UtcOffset): String =
    OffsetDateTime.ofInstant(this.value, offset.zoneOffset).toString()