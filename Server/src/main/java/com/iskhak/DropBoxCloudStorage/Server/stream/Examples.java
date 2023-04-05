package com.iskhak.DropBoxCloudStorage.Server.stream;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Examples {
        private static String serverDir;
    private static Integer[][] ARRAY = new Integer[][]{
            {1,1,2,3},
            {1,1,3},
            {1,3},
            {1,1,2,3,5,6,7}
            };

    public static void main(String[] args) throws IOException {
        serverDir = "Server/base";
        //filter odd
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(x -> x % 2 == 0)
                .forEach(System.out::println);
        // increment all values by 1 and collect to list
        List<Integer> list = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .map(x -> x + 1)
                .toList();
        System.out.println(list);

        // calculate sum
        Integer r = Stream.of(1, 2, 3, 4, 5, 6, 7, 9)
                .reduce(0, Integer::sum);
        System.out.println(r);

        //words counter
        Map<String, Integer> words = Files.lines(Path.of(serverDir, "1.txt"))
                .filter(StringUtils::isNoneBlank)
                .flatMap(s -> Stream.of(s.split(" +")))
                .filter(StringUtils::isNoneBlank)
                .map(String::toLowerCase)
                .map(s -> s.replaceAll("\\W|\\d", ""))
                .collect(Collectors.toMap(
                        Function.identity(),
                        value -> 1,
                        Integer::sum
                ));

        //counter rate
        words.entrySet().stream().
                sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));

        List<Integer> values = Arrays.stream(ARRAY)
                .flatMap(Stream::of)
                .toList();

        System.out.println(values);
    }

}
