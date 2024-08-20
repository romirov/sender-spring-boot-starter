package com.mvp

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
abstract class AbstractSpringBootTest: AnnotationSpec(){
    init {
        extensions(SpringExtension)
    }
}