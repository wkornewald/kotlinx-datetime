/*
 * Copyright 2019-2020 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 */
/* Based on the ThreeTenBp project.
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 */

package kotlinx.datetime.test
import kotlinx.datetime.*
import kotlin.test.*

class TimeZoneTest {

    @Test
    fun utc() {
        val utc: FixedOffsetTimeZone = TimeZone.UTC
        println(utc)
        assertEquals("Z", utc.id)
        assertEquals(0, utc.utcOffset.totalSeconds)
    }

    @Test
    fun system() {
        val tz = TimeZone.currentSystemDefault()
        println(tz)
        val offset = Clock.System.now().offsetIn(tz)
        assertTrue(offset.totalSeconds in -18 * 60 * 60 .. 18 * 60 * 60)
        // assertTrue(tz.id.contains('/')) // does not work on build agents, whose timezone is "UTC"
        // TODO: decide how to assert system tz properties
    }

    @Test
    fun available() {
        val allTzIds = TimeZone.availableZoneIds
        println("Available TZs:")
        allTzIds.forEach(::println)

        assertNotEquals(0, allTzIds.size)
        assertTrue(TimeZone.currentSystemDefault().id in allTzIds)
        assertTrue("UTC" in allTzIds)
    }

    @Test
    fun availableZonesAreAvailable() {
        for (zoneName in TimeZone.availableZoneIds) {
            TimeZone.of(zoneName)
        }
    }

    @Test
    fun of() {
        val tzm = TimeZone.of("Europe/Moscow")
        assertNotNull(tzm)
        assertEquals("Europe/Moscow", tzm.id)
        // TODO: Check known offsets from UTC for particular moments

        assertFailsWith<IllegalTimeZoneException> { TimeZone.of("Mars/Standard") }
        assertFailsWith<IllegalTimeZoneException> { TimeZone.of("UTC+X") }
    }

    @Test
    fun ofFailsOnInvalidOffset() {
        for (v in UtcOffsetTest.invalidUtcOffsetStrings) {
            assertFailsWith<IllegalTimeZoneException> { TimeZone.of(v) }
        }
    }

    // from 310bp
    @Test
    fun timeZoneEquals() {
        val test1 = TimeZone.of("Europe/London")
        val test2 = TimeZone.of("Europe/Paris")
        val test2b = TimeZone.of("Europe/Paris")
        assertEquals(false, test1 == test2)
        assertEquals(false, test2 == test1)

        assertEquals(true, test1 == test1)
        assertEquals(true, test2 == test2)
        assertEquals(true, test2 == test2b)

        assertEquals(test1.hashCode(), test1.hashCode())
        assertEquals(test2.hashCode(), test2.hashCode())
        assertEquals(test2.hashCode(), test2b.hashCode())
    }

    // from 310bp
    @Test
    fun timeZoneToString() {
        val idToString = arrayOf(
            Pair("Europe/London", "Europe/London"),
            Pair("Europe/Paris", "Europe/Paris"),
            Pair("Europe/Berlin", "Europe/Berlin"),
            Pair("Z", "Z"),
            Pair("UTC", "UTC"),
            Pair("UTC+01:00", "UTC+01:00"),
            Pair("GMT+01:00", "GMT+01:00"),
            Pair("UT+01:00", "UT+01:00"))
        for ((id, str) in idToString) {
            assertEquals(str, TimeZone.of(id).toString())
        }
    }

    @Test
    fun utcOffsetNormalization() {
        val sameOffsetTZs = listOf("+04", "+04:00", "UTC+4", "UT+04").map { TimeZone.of(it) }
        val instant = Instant.fromEpochSeconds(0)
        val offsets = sameOffsetTZs.map { it.offsetAt(instant) }

        assertTrue(offsets.distinct().size == 1, "Expected all offsets to be equal: $offsets")
        assertTrue(offsets.map { it.toString() }.distinct().size == 1, "Expected all offsets to have the same string representation: $offsets")
    }

    // from 310bp
    @Test
    fun newYorkOffset() {
        val test = TimeZone.of("America/New_York")
        val offset = UtcOffset.parse("-5")

        fun check(expectedOffset: String, dateTime: LocalDateTime) {
            assertEquals(UtcOffset.parse(expectedOffset), dateTime.toInstant(offset).offsetIn(test))
        }

        check("-5", LocalDateTime(2008, 1, 1))
        check("-5", LocalDateTime(2008, 2, 1))
        check("-5", LocalDateTime(2008, 3, 1))
        check("-4", LocalDateTime(2008, 4, 1))
        check("-4", LocalDateTime(2008, 5, 1))
        check("-4", LocalDateTime(2008, 6, 1))
        check("-4", LocalDateTime(2008, 7, 1))
        check("-4", LocalDateTime(2008, 8, 1))
        check("-4", LocalDateTime(2008, 9, 1))
        check("-4", LocalDateTime(2008, 10, 1))
        check("-4", LocalDateTime(2008, 11, 1))
        check("-5", LocalDateTime(2008, 12, 1))
        check("-5", LocalDateTime(2008, 1, 28))
        check("-5", LocalDateTime(2008, 2, 28))
        check("-4", LocalDateTime(2008, 3, 28))
        check("-4", LocalDateTime(2008, 4, 28))
        check("-4", LocalDateTime(2008, 5, 28))
        check("-4", LocalDateTime(2008, 6, 28))
        check("-4", LocalDateTime(2008, 7, 28))
        check("-4", LocalDateTime(2008, 8, 28))
        check("-4", LocalDateTime(2008, 9, 28))
        check("-4", LocalDateTime(2008, 10, 28))
        check("-5", LocalDateTime(2008, 11, 28))
        check("-5", LocalDateTime(2008, 12, 28))
    }

    // from 310bp
    @Test
    fun newYorkOffsetToDST() {
        val test = TimeZone.of("America/New_York")
        val offset = UtcOffset.parse("-5")

        fun check(expectedOffset: String, dateTime: LocalDateTime) {
            assertEquals(UtcOffset.parse(expectedOffset), dateTime.toInstant(offset).offsetIn(test))
        }

        check("-5", LocalDateTime(2008, 3, 8))
        check("-5", LocalDateTime(2008, 3, 9))
        check("-4", LocalDateTime(2008, 3, 10))
        check("-4", LocalDateTime(2008, 3, 11))
        check("-4", LocalDateTime(2008, 3, 12))
        check("-4", LocalDateTime(2008, 3, 13))
        check("-4", LocalDateTime(2008, 3, 14))
        // cutover at 02:00 local
        check("-5", LocalDateTime(2008, 3, 9, 1, 59, 59, 999999999))
        check("-4", LocalDateTime(2008, 3, 9, 2, 0, 0, 0))
    }

    // from 310bp
    @Test
    fun newYorkOffsetFromDST() {
        val test = TimeZone.of("America/New_York")
        val offset = UtcOffset.parse("-4")

        fun check(expectedOffset: String, dateTime: LocalDateTime) {
            assertEquals(UtcOffset.parse(expectedOffset), dateTime.toInstant(offset).offsetIn(test))
        }

        check("-4", LocalDateTime(2008, 11, 1))
        check("-4", LocalDateTime(2008, 11, 2))
        check("-5", LocalDateTime(2008, 11, 3))
        check("-5", LocalDateTime(2008, 11, 4))
        check("-5", LocalDateTime(2008, 11, 5))
        check("-5", LocalDateTime(2008, 11, 6))
        check("-5", LocalDateTime(2008, 11, 7))
        // cutover at 02:00 local
        check("-4", LocalDateTime(2008, 11, 2, 1, 59, 59, 999999999))
        check("-5", LocalDateTime(2008, 11, 2, 2, 0, 0, 0))
    }

    @Test
    fun localDateTimeNormalToInstant() {
        val zone = TimeZone.of("Europe/Berlin")
        val date = LocalDateTime(2021, Month.JULY, 28, 12, 25)
        for (resolver in listOf(
            LocalDateTimeAmbiguityResolver.Strict,
            LocalDateTimeAmbiguityResolver.Lenient,
            LocalDateTimeAmbiguityResolver.BeforeTransition,
            LocalDateTimeAmbiguityResolver.AfterTransition,
            LocalDateTimeAmbiguityResolver.Custom { fail("Should not be called") },
        )) {
            val instant = date.toInstant(zone, resolver)
            assertEquals(date, instant.toLocalDateTime(zone))
        }
    }

    @Test
    fun localDateTimeGapToInstant() {
        val zone = TimeZone.of("Europe/Berlin")
        val date = LocalDateTime(2021, Month.MARCH, 28, 2, 25) // non-existent local time

        val instantLenient = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Lenient)
        val instantLater = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.later() })
        val instantEarlier = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom {
            assertEquals(date, it.dateTime)
            assertEquals(0, it.results)
            assertEquals(UtcOffset.parse("+1"), it.offsetBefore)
            assertEquals(UtcOffset.parse("+2"), it.offsetAfter)

            it.earlier()
        })
        val instantBefore1 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.BeforeTransition)
        val instantBefore2 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.beforeTransition() })
        val instantAfter1 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.AfterTransition)
        val instantAfter2 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.afterTransition() })

        assertEquals(instantLater, instantLenient)
        assertTrue(instantEarlier < instantLater, "$instantEarlier < $instantLater")

        assertEquals(LocalDateTime(2021, Month.MARCH, 28, 3, 25), instantLater.toLocalDateTime(zone))
        assertEquals(LocalDateTime(2021, Month.MARCH, 28, 1, 25), instantEarlier.toLocalDateTime(zone))

        assertEquals(instantBefore1, instantBefore2)
        assertEquals(instantLater, instantBefore1)

        assertEquals(instantAfter1, instantAfter2)
        assertEquals(instantEarlier, instantAfter1)

        assertFails { date.toInstant(zone, LocalDateTimeAmbiguityResolver.Strict) }
        assertFails { date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.single() }) }
    }

    @Test
    fun localDateTimeOverlapToInstant() {
        val zone = TimeZone.of("Europe/Berlin")
        val date = LocalDateTime(2020, Month.OCTOBER, 25, 2, 25) // ambiguous local time

        val instantLenient = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Lenient)
        val instantLater = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.later() })
        val instantEarlier = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom {
            assertEquals(date, it.dateTime)
            assertEquals(2, it.results)
            assertEquals(UtcOffset.parse("+2"), it.offsetBefore)
            assertEquals(UtcOffset.parse("+1"), it.offsetAfter)

            it.earlier()
        })
        val instantBefore1 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.BeforeTransition)
        val instantBefore2 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.beforeTransition() })
        val instantAfter1 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.AfterTransition)
        val instantAfter2 = date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.afterTransition() })

        assertEquals(instantEarlier, instantLenient)
        assertTrue(instantEarlier < instantLater, "$instantEarlier < $instantLater")

        assertEquals(date, instantLater.toLocalDateTime(zone))
        assertEquals(date, instantEarlier.toLocalDateTime(zone))

        assertEquals(instantBefore1, instantBefore2)
        assertEquals(instantEarlier, instantBefore1)

        assertEquals(instantAfter1, instantAfter2)
        assertEquals(instantLater, instantAfter1)

        assertFails { date.toInstant(zone, LocalDateTimeAmbiguityResolver.Strict) }
        assertFails { date.toInstant(zone, LocalDateTimeAmbiguityResolver.Custom { it.single() }) }
    }





    private fun LocalDateTime(year: Int, monthNumber: Int, dayOfMonth: Int) = LocalDateTime(year, monthNumber, dayOfMonth, 0, 0)

}
