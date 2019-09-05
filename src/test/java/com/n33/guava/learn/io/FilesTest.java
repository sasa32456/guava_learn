package com.n33.guava.learn.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FilesTest {
    private final String SOURCE_FILE = "F:\\IdeaProjectsLearn\\guava_learn\\src\\test\\resources\\io\\source.txt";
    private final String TARGET_FILE = "F:\\IdeaProjectsLearn\\guava_learn\\src\\test\\resources\\io\\target.txt";

    @Test
    public void testCopyFileWithGuava() throws IOException {
        File targetFile = new File(TARGET_FILE);
        File sourceFile = new File(SOURCE_FILE);
        Files.copy(sourceFile, targetFile);
        assertThat(targetFile.exists(), equalTo(true));
        HashCode sourceHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(sourceHashCode.toString(),equalTo(targetHashCode.toString()));
    }

    @Test
    public void testCopyFileWithJDKNio2() throws IOException {
        java.nio.file.Files.copy(
                Paths.get("F:\\IdeaProjectsLearn\\guava_learn\\src\\test\\resources","io","source.txt"),
                Paths.get("F:\\IdeaProjectsLearn\\guava_learn\\src\\test\\resources","io","target.txt"),
                StandardCopyOption.REPLACE_EXISTING);
        File targetFile = new File(TARGET_FILE);
        assertThat(targetFile.exists(), equalTo(true));
    }

    @Test
    public void testMoveFile() throws IOException {
        try{
            Files.move(new File(SOURCE_FILE),new File(TARGET_FILE));
            assertThat(new File(TARGET_FILE).exists(), equalTo(true));
            assertThat(new File(SOURCE_FILE).exists(), equalTo(false));
        }finally {
            Files.move(new File(TARGET_FILE),new File(SOURCE_FILE));
        }
    }

    @Test
    public void testToString() throws IOException {
        final String expectedString = "123456\n" +
                "123456";
        List<String> strings = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String result = Joiner.on("\n").join(strings);
        assertThat(result, equalTo(expectedString));
    }

    @Test
    public void testToProcessString() throws IOException {
        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<List<Integer>>() {
            private final List<Integer> lengthList = new ArrayList<>();
            @Override
            public boolean processLine(String line) throws IOException {
                if (line.length()==0) return false;//当遇到0不继续读
                lengthList.add(line.length());
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return lengthList;
            }
        };

        List<Integer> result = Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8).readLines(lineProcessor);
        System.out.println(result);
    }

    @Test
    public void testFileSha() throws IOException {
        File file = new File(SOURCE_FILE);
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode);

    }

    @Test
    public void testFileWrite() throws IOException {
        String testPath = "F:\\IdeaProjectsLearn\\guava_learn\\src\\test\\resources\\io\\testFileWrite.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        String content1 = "content 1";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content1);
        String actully = Files.asCharSource(testFile, Charsets.UTF_8).read();

        assertThat(actully, equalTo(content1));

        String content2 = "content 2";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content2);
        actully = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actully, equalTo(content2));
    }

    @Test
    public void testFileAppend() throws IOException {
        String testPath = "F:\\IdeaProjectsLearn\\guava_learn\\src\\test\\resources\\io\\testFileAppend.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        String content1 = "content1";
        Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND).write(content1);
        String actully = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actully, equalTo(content1));
        String content2 = "content2";
        Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND).write(content2);
        actully = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actully, equalTo(content1+content2));
    }


    @After
    public void tearDown() {
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }

}
