package com.cdpo.dwh.repository;

import com.cdpo.dwh.dto.BookingResponseDTO;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

@Repository
public class MetricRepository implements IMetricRepository {
    @Override
    public List<Long> save(List<BookingResponseDTO> bookings) {
        return generateRandomNumbers(bookings.size());
    }

    private static List<Long> generateRandomNumbers(int count) {
        if (count == 0) return Collections.emptyList();
        Random random = new Random();
        int realCount = random.nextInt(count == 1 ? 2 : count);
        if (realCount == 0) return Collections.emptyList();
        return LongStream.generate(random::nextLong).limit(realCount).boxed().toList();
    }

}
