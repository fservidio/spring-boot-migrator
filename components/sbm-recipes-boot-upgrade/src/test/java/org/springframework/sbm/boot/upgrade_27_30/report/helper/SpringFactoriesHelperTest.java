/*
 * Copyright 2021 - 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.sbm.boot.upgrade_27_30.report.helper;

import org.junit.jupiter.api.Test;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.project.resource.TestProjectContext;

import static org.assertj.core.api.Assertions.assertThat;

class SpringFactoriesHelperTest {

    @Test
    public void detectsFileWithSpringFactories() {

        ProjectContext context = TestProjectContext.buildProjectContext()
                .withProjectResource(
                        "src/main/resources/META-INF/spring.factories",
                        """
                                hello.world=something
                                org.springframework.boot.autoconfigure.EnableAutoConfiguration=XYZ
                                """
                )
                .build();

        SpringFactoriesHelper sut = new SpringFactoriesHelper();
        boolean evaluate = sut.evaluate(context);

        assertThat(evaluate).isTrue();

        assertThat(sut.getData()).isNotNull();
        assertThat(sut.getData().get("files")).hasSize(1);
        assertThat(sut.getData().get("files").get(0)).contains("src/main/resources/META-INF/spring.factories");
    }
}
