package com.n33.guava.learn.utilities;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class JoinerTest {
    private final List<String> stringList = Arrays.asList(
            "Google", "Guava", "Java", "Scala", "Kafka"
    );
    private final List<String> stringListWithNullValue = Arrays.asList(
            "Google", "Guava", "Java", "Scala", null
    );
    private final Map<String ,String> stringMap = of("hello","Guava", "Java", "Scala");

    private final String targetFileName = "F:\\IdeaProjectsLearn\\zzz\\guava-joiner.txt";
    private final String targetFileNameToMap = "F:\\IdeaProjectsLearn\\zzz\\guava-joiner-map.txt";

    @Test
    public void testJoinOnJoin(){
        String result = Joiner.on("#").join(stringList);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }
    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue(){
        String result = Joiner.on("#").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }
    @Test
    public void testJoinOnJoinWithNullValueButSkip(){
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }
    @Test
    public void testJoin_On_Join_WithNullValue_UseDefaultValue(){
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_StringBuilder() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder, stringListWithNullValue);
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(),equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_Writer() {
        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error");
        }
    }

    @Test
    public void testJoiningByStreamSkipNullValues() {
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty()).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }
    @Test
    public void testJoiningByStreamWithDefaultValues() {
//        String result = stringListWithNullValue.stream().map(item -> item == null || item.isEmpty()?"DEFAULT":item).collect(joining("#"));
        String result = stringListWithNullValue.stream().map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }
    public String defaultValue(final String item) {
       return item == null || item.isEmpty()?"DEFAULT":item;
    }

    @Test
    public void testJoinOnMap() {
       assertThat(Joiner.on('#').withKeyValueSeparator("=").join(stringMap),equalTo("hello=Guava#Java=Scala"));
    }

    @Test
    public void testJoinOnWithMapToAppendable() {
        try (FileWriter writer = new FileWriter(new File(targetFileNameToMap))) {
            Joiner.on("#").withKeyValueSeparator("=").appendTo(writer, stringMap);
            assertThat(Files.isFile().test(new File(targetFileNameToMap)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error");
        }
    }
}
