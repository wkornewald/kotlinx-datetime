/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */

package kotlinx.datetime

import kotlinx.datetime.internal.JSJoda.*
import kotlinx.datetime.internal.JSJoda.Instant as jtInstant
import kotlinx.datetime.internal.JSJoda.LocalDate as jtLocalDate
import kotlinx.datetime.internal.JSJoda.LocalDateTime as jtLocalDateTime

actual val LocalDate.Companion.MIN: LocalDate get() = LocalDate(jtLocalDate.MIN)

actual val LocalDate.Companion.MAX: LocalDate get() = LocalDate(jtLocalDate.MAX)

actual val LocalDateTime.Companion.MIN: LocalDateTime get() = LocalDateTime(jtLocalDateTime.MIN)

actual val LocalDateTime.Companion.MAX: LocalDateTime get() = LocalDateTime(jtLocalDateTime.MAX)

actual val Instant.Companion.MIN: Instant get() = Instant(jtInstant.MIN)

actual val Instant.Companion.MAX: Instant get() = Instant(jtInstant.MAX)

actual fun Instant.toStringWithOffset(offset: UtcOffset): String =
    OffsetDateTime.ofInstant(this.value, offset.zoneOffset).toString()
