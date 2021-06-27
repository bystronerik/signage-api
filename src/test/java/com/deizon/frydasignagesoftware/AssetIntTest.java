/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware;

import static org.assertj.core.api.Assertions.assertThat;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import java.io.IOException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@GraphQLTest
public class AssetIntTest {

    @Autowired private GraphQLTestTemplate graphQLTestTemplate;

    // @Test
    public void updateAsset() throws IOException {
        GraphQLResponse response =
                graphQLTestTemplate.postForResource("graphql/update-asset.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.updateAsset.id")).isNotNull();
        assertThat(response.get("$.data.updateAsset.name")).isEqualTo("gggtestovic");
    }
}
