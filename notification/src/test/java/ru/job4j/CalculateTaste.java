package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculateTaste {

    @Test
    public void whenOnePlusOneThenTwo() {
        assertThat(
                new Calculate().add(1, 1),
                is(2)
        );
    }
}
