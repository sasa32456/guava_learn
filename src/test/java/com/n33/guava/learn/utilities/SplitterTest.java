package com.n33.guava.learn.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SplitterTest {
    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("hello|world");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty() {
        List<String> result = Splitter.on("|").splitToList("hello|world|||||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(7));
        result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||||");
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult() {
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|||||");
        assertThat(result.get(0), equalTo("hello "));
        assertThat(result.get(1), equalTo(" world"));

        result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello | world|||||");
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }


    @Test
    public void testSplitFixLength() {
        List<String> result = Splitter.fixedLength(3).splitToList("aaabbbccc");
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("aaa"));
        assertThat(result.get(1), equalTo("bbb"));
        assertThat(result.get(2), equalTo("ccc"));
    }

    @Test
    public void TestSplitOnSplitLimit() {
        //指定长度后不拆分
        List<String> result = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
        assertThat(result.get(2), equalTo("java#google#scala"));
    }

    @Test
    public void TestSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }
    @Test
    public void TestSplitOnPattern() {
        List<String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().splitToList("hello | world|||||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void TestSplitOnSplitToMap() {
        Map<String, String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().withKeyValueSeparator("=").split("hello=HELLO | world=WORLD|||||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("hello"), equalTo("HELLO"));
        assertThat(result.get("world"), equalTo("WORLD"));
    }
}
