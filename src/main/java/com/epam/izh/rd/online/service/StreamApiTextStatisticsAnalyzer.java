package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.*;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text)
                .stream()
                .mapToInt(String::length)
                .sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return getWords(text)
                .size();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return getUniqueWords(text)
                .size();
    }

    @Override
    public List<String> getWords(String text) {
        return Stream.of(text.split("[^a-zA-z]+"))
                .collect(
                        Collectors.toList());
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return getWords(text)
                .stream()
                .collect(
                        HashSet::new,
                        HashSet::add,
                        HashSet::addAll);
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        Map<String, Integer> mapWords = new HashMap<>();
        getUniqueWords(text)
                .forEach(
                        (m) -> mapWords.put(
                                m, (int) getWords(text)
                                        .stream()
                                        .filter(s -> s.equals(m))
                                        .count()));
        return mapWords;
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        if (direction == Direction.ASC) {
            return getWords(text)
                    .stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .collect(Collectors.toList());
        } else {
            return getWords(text)
                    .stream()
                    .sorted((o1, o2) -> o2.length() - o1.length())
                    .collect(Collectors.toList());
        }
    }
}
