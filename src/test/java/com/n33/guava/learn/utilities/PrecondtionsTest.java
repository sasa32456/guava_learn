package com.n33.guava.learn.utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Precondition
 * Objects
 * assert key word(statement)
 */
public class PrecondtionsTest {
    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        checkNotNull(null);
    }

    private void checkNotNull(final List<String> list) {
        Preconditions.checkNotNull(list);
    }

    @Test
    public void testCheckNotNullWithMessage() {
        try {
            checkNotNullWithMessage(null);
        } catch (Exception e) {
//            assertThat(e, is(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("The list should not be null"));
        }
    }

    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            checkNotNullWithFormatMessage(null);
        } catch (Exception e) {
//            assertThat(e, is(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("The list should not be null and the size must be 2"));
        }
    }

    @Test
    public void testCheckArguments() {
        final String type = "A";
        try {
            Preconditions.checkArgument(type.equals("B"));
        } catch (Exception e) {
//            assertThat(e,is(IllegalArgumentException.class));
        }
    }
    @Test
    public void testCheckState() {
        final String state = "A";
        try {
            Preconditions.checkState(state.equals("B"),"The state is illegal.");
        } catch (Exception e) {
//            assertThat(e,is(IllegalArgumentException.class));
        }
    }

    @Test
    public void testCheckIndex() {
        try {
            List<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10,list.size());
        } catch (Exception e) {
            assertThat(e.getClass(),equalTo(IndexOutOfBoundsException.class));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testByObject() {
        Objects.requireNonNull(null);
    }

    @Test(expected = AssertionError.class)
    public void testAssert() {
        List<String> list = null;
        assert list != null;
    }
    @Test
    public void testAssertWithMessage() {
        try {
            List<String> list = null;
            assert list != null:"The list should not be null.";
        } catch (Error e) {
            assertThat(e.getClass(),equalTo(AssertionError.class));
            assertThat(e.getMessage(), equalTo("The list should not be null."));
        }
    }



    private void checkNotNullWithMessage(final List<String> list) {
        Preconditions.checkNotNull(list,"The list should not be null");
    }
    private void checkNotNullWithFormatMessage(final List<String> list) {
        Preconditions.checkNotNull(list,"The list should not be null and the size must be %s",2);
    }
}
