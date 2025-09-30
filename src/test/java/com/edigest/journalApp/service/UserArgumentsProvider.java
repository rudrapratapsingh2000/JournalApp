package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("Ramu").password("Ramu001").build()),
                Arguments.of(User.builder().userName("Raj").password("").build())//,
//                Arguments.of(User.builder().userName("Raj").password(null).build())
        );
    }
}
