package by.mchs.e112.analytics.dto;

import java.time.Instant;

/**
 * Точка временного ряда: начало бакета и число происшествий в нём.
 */
public record TimeBucketCount(Instant bucketStart, long count) {
}
